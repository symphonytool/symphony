package eu.compassresearch.core.interpreter.util;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
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
	
	public static boolean isAllChildrenFinishedOrWaitingForEvent(CmlBehaviourThread process)
	{
		boolean isAllFinishedOrWaitingForEvent = true;
		for(CmlBehaviourThread child : process.children())
		{
			isAllFinishedOrWaitingForEvent &= child.finished() || child.waitingForEvent();
		}
		return isAllFinishedOrWaitingForEvent;
	}
	
	public static boolean isAtLeastOneChildWaitingForEvent(CmlBehaviourThread process)
	{
		for(CmlBehaviourThread child : process.children())
		{
			if(child.waitingForEvent())
				return true;
		}
		
		return false;
	}
	
	public static boolean isAllChildrenWaitingForEvent(CmlBehaviourThread process)
	{
		boolean result = true;
		for(CmlBehaviourThread child : process.children())
		{
			result &= child.waitingForEvent();
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
}
