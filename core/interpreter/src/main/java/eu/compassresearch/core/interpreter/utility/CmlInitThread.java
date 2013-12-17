package eu.compassresearch.core.interpreter.utility;

import org.overture.interpreter.scheduler.InitThread;

public class CmlInitThread extends InitThread
{

	public CmlInitThread(Thread t)
	{
		super(t);
	}

	
	@Override
	public void suspendOthers()
	{
	}
}
