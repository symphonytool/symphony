package eu.compassresearch.core.interpreter.runtime;

import org.overture.typechecker.Environment;

import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.DefaultSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.RandomSelectionStrategy;


public class CmlRuntime {

	//protected static CmlScheduler cmlScheduler = new CmlScheduler();
	protected static Environment globalEnvironment = null;
	protected static CMLSupervisorEnvironment supervisorEnvironment = null;
	
	
//	public static CmlScheduler getCmlScheduler()
//	{
//		return cmlScheduler;
//	}
	
	public static CMLSupervisorEnvironment getSupervisorEnvironment()
	{
		if(null == supervisorEnvironment)
			supervisorEnvironment = new DefaultSupervisorEnvironment(new RandomSelectionStrategy());
		
		return supervisorEnvironment;
		
	}

	public static Environment getGlobalEnvironment() {
		return globalEnvironment;
	}

	public static void setGlobalEnvironment(Environment globalEnvironment) {
		CmlRuntime.globalEnvironment = globalEnvironment;
	}
			
}
