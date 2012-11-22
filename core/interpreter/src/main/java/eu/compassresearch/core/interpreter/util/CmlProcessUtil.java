package eu.compassresearch.core.interpreter.util;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlProcessUtil {

	public static boolean isAllChildrenFinished(CmlProcess process)
	{
		boolean isAllFinished = true;
		for(CmlProcess child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}
	
	public static boolean isAtLeastOneChildWaitingForEvent(CmlProcess process)
	{
		for(CmlProcess child : process.children())
		{
			if(child.waiting())
				return true;
		}
		
		return false;
	}
	
	/**
	 * FIXME:This is just a temp solution, chansets can be other than this
	 * @return
	 */
	public static CmlAlphabet convertChansetExpToAlphabet(PExp chansetExp, Context question)
	{
		AEnumChansetSetExp chanset = (AEnumChansetSetExp)chansetExp;

		Set<CmlEvent> coms = new HashSet<CmlEvent>();
		
		for(LexIdentifierToken id : chanset.getIdentifiers())
		{
			//FIXME: This should be a name so the conversion is avoided
			LexNameToken channelName = new LexNameToken("Default",id);
			CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
			CmlCommunicationEvent com = new CmlCommunicationEvent(chanValue);
			coms.add(com);
		}
		
		CmlAlphabet alpha = new CmlAlphabet(coms);
		
		return alpha;
	}
}
