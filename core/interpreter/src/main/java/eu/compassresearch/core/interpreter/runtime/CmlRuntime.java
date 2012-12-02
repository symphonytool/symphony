package eu.compassresearch.core.interpreter.runtime;

import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.overture.typechecker.Environment;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;


public class CmlRuntime {

	//protected static CmlScheduler cmlScheduler = new CmlScheduler();
	protected static Environment globalEnvironment = null;
	//protected static CmlSupervisorEnvironment supervisorEnvironment = null;
	protected static Logger logger = null;
	protected static Handler logHandler = null;
	private static boolean showHiddenEvents = false;
	
//	public static CmlScheduler getCmlScheduler()
//	{
//		return cmlScheduler;
//	}
	public static Logger logger()
	{
		if(null == logger)
		{
			logger = Logger.getLogger("CMLInterpreterLogger");
			logger.setLevel(Level.FINER);
			
			logHandler = new Handler() {

				@Override
				public void publish(LogRecord record) {
					System.out.println(record.getMessage());
				}

				@Override
				public void flush() {
					
				}

				@Override
				public void close() throws SecurityException {
					
				}
			};
//			logHandler.setFilter(new Filter()
//			{
//
//				@Override
//				public boolean isLoggable(LogRecord record) {
//					record.getLevel()
//					return false;
//				}
//				
//			})
			logger.addHandler(logHandler);
		}
		
		return logger;

	}
	
//	public static CmlSupervisorEnvironment createSupervisorEnvironment()
//	{
//		//if(null == supervisorEnvironment)
//			//supervisorEnvironment = new DefaultSupervisorEnvironment(new RandomSelectionStrategy());
//		
//		return new DefaultSupervisorEnvironment(new RandomSelectionStrategy());
//		
//	}
	
	public static CmlSupervisorEnvironment createSupervisorEnvironment(CmlCommunicationSelectionStrategy cmlCommunicationSelectionStrategy)
	{
		//if(null == supervisorEnvironment)
			//supervisorEnvironment = new DefaultSupervisorEnvironment(new RandomSelectionStrategy());
		
		return new DefaultSupervisorEnvironment(cmlCommunicationSelectionStrategy);
		
	}

	public static Environment getGlobalEnvironment() {
		return globalEnvironment;
	}

	public static void setGlobalEnvironment(Environment globalEnvironment) {
		CmlRuntime.globalEnvironment = globalEnvironment;
	}

	public static boolean isShowHiddenEvents() {
		return showHiddenEvents;
	}

	protected static void setShowHiddenEvents(boolean showHiddenEvents) {
		CmlRuntime.showHiddenEvents = showHiddenEvents;
	}
			
}
