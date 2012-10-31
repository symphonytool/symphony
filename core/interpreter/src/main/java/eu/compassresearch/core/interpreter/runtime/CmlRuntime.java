package eu.compassresearch.core.interpreter.runtime;

import org.overture.typechecker.Environment;


public class CmlRuntime {

	//protected static CmlScheduler cmlScheduler = new CmlScheduler();
	protected static Environment globalEnvironment = null;
	
//	public static CmlScheduler getCmlScheduler()
//	{
//		return cmlScheduler;
//	}

	public static Environment getGlobalEnvironment() {
		return globalEnvironment;
	}

	public static void setGlobalEnvironment(Environment globalEnvironment) {
		CmlRuntime.globalEnvironment = globalEnvironment;
	}
			
}
