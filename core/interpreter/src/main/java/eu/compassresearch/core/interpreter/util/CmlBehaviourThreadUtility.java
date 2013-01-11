package eu.compassresearch.core.interpreter.util;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlBehaviourThreadUtility {

	public static boolean isAllChildrenFinished(CmlBehaviourThread process)
	{
		boolean isAllFinished = true;
		for(CmlBehaviourThread child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}
	
	public static boolean isAtLeastOneChildWaitingForEvent(CmlBehaviourThread process)
	{
		for(CmlBehaviourThread child : process.children())
		{
			if(child.waiting())
				return true;
		}
		
		return false;
	}
	
	public static boolean isAllChildrenWaitingForEvent(CmlBehaviourThread process)
	{
		boolean result = true;
		for(CmlBehaviourThread child : process.children())
		{
			result &= child.waiting();
		}
		
		return result;
	}
	
	public static boolean existsAFinishedChild(CmlBehaviourThread process)
	{
		for(CmlBehaviourThread child : process.children())
		{
			if(child.finished())
				return true;
		}
		
		return false;
	}
	
	/**
	 * FIXME:This is just a temp solution, chansets can be other than this
	 * @return
	 */
	public static CmlAlphabet convertChansetExpToAlphabet(CmlBehaviourThread sourceProcess, PVarsetExpression chansetExp, Context question)
	{
		AFatEnumVarsetExpression chanset = (AFatEnumVarsetExpression)chansetExp;

		Set<CmlEvent> coms = new HashSet<CmlEvent>();
		
		for(LexIdentifierToken id : chanset.getIdentifiers())
		{
			//FIXME: This should be a name so the conversion is avoided
			LexNameToken channelName = new LexNameToken("|CHANNELS|",id);
			CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
			ObservableEvent com = new PrefixEvent(sourceProcess,chanValue);
			coms.add(com);
		}
		
		CmlAlphabet alpha = new CmlAlphabet(coms);
		
		return alpha;
	}
}
