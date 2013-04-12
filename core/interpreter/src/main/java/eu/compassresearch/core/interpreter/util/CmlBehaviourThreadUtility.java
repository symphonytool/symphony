package eu.compassresearch.core.interpreter.util;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

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
	
	public static boolean finishedChildExists(CmlBehaviourThread process)
	{
		for(CmlBehaviourThread child : process.children())
		{
			if(child.finished())
				return true;
		}
		
		return false;
	}
}
