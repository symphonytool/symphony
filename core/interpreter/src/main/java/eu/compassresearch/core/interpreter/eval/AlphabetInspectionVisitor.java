package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlCommunication;
import eu.compassresearch.core.interpreter.cml.CmlEvent;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlTauEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class AlphabetInspectionVisitor
		extends
		QuestionAnswerCMLAdaptor<Context, eu.compassresearch.core.interpreter.cml.CmlAlphabet> {

	private final CmlProcess ownerProcess;
	
	public AlphabetInspectionVisitor(CmlProcess ownerProcess)
	{
		this.ownerProcess = ownerProcess;
	}
	
	@Override
	public CmlAlphabet defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CmlTauEvent.newTauEvent(node));
		return new CmlAlphabet(specialEvents);
	}
	
	@Override
	public CmlAlphabet defaultPAction(PAction node, Context question)
			throws AnalysisException {

		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CmlTauEvent.newTauEvent(node));
		return new CmlAlphabet(specialEvents);
	}
	
	@Override
	public CmlAlphabet caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("Default",node.getIdentifier());
		
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		CmlCommunication com = new CmlCommunication(chanValue);
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		comset.add(com);
		return new CmlAlphabet(comset);
	}
	
	/**
	 * This returns the alphabet of a interleaved action. This has three parts:
	 * Parallel Begin:
	 * 	At this step the interleaving action are not yet created. So this will be a silent (tau) transition
	 * 	where they will be created and started. So the alphabet returned here is {tau}
	 * 
	 * Parallel Non-sync:
	 * 	At this step the actions are each executed separately. Since no sync shall stake place this Action just wait
	 * 	for the child actions to be in the FINISHED state. So the alphabet returned here is {alpha(left) union alpha(right)}
	 * 
	 * Parallel End:
	 *  At this step both child actions are in the FINISHED state and they will be removed from the running process network
	 *  and this will make a silent transition into Skip. So the alphabet returned here is {tau}
	 */
	@Override
	public CmlAlphabet caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Context question)
			throws AnalysisException {
		
		CmlAlphabet alpha = null;
		
		//Parallel Begin:
		if(ownerProcess.hasChildren())
		{
			for(CmlProcess child : ownerProcess.children())
			{
				if(!child.finished())
				{
					if(alpha == null)
						alpha = child.inspect();
					else
						alpha = alpha.union(child.inspect());
				}
			}
		}
		//If there are no children, then either the interleaving is beginning or ending
		if(null == alpha)
		{
			alpha = defaultPAction(node,question);
		}
		
		return alpha;
	}
	
}
