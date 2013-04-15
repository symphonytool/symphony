package eu.compassresearch.core.interpreter.util;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class CmlBehaviourThreadUtility {

	public static boolean isAllChildrenFinished(CmlBehaviour process)
	{
		boolean isAllFinished = true;
		for(CmlBehaviour child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}
	
	public static boolean finishedChildExists(CmlBehaviour process)
	{
		for(CmlBehaviour child : process.children())
		{
			if(child.finished())
				return true;
		}
		
		return false;
	}
}
