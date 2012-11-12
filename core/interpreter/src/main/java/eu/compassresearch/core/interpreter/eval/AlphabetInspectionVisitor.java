package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLCommunication;
import eu.compassresearch.core.interpreter.cml.CmlEvent;
import eu.compassresearch.core.interpreter.cml.CMLTauEvent;
import eu.compassresearch.core.interpreter.cml.SpecialEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class AlphabetInspectionVisitor
		extends
		QuestionAnswerCMLAdaptor<Context, eu.compassresearch.core.interpreter.cml.CMLAlphabet> {

	@Override
	public CMLAlphabet defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CMLTauEvent.newTauEvent(node));
		return new CMLAlphabet(specialEvents);
	}
	
	@Override
	public CMLAlphabet defaultPAction(PAction node, Context question)
			throws AnalysisException {

		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CMLTauEvent.newTauEvent(node));
		return new CMLAlphabet(specialEvents);
	}
	
	@Override
	public CMLAlphabet caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("Default",node.getIdentifier());
		
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		CMLCommunication com = new CMLCommunication(chanValue);
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		comset.add(com);
		return new CMLAlphabet(comset);
	}
	
	
}
