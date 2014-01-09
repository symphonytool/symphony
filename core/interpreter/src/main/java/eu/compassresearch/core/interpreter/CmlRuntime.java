package eu.compassresearch.core.interpreter;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CmlRuntime
{

	protected static Logger logger = null;
	protected static Handler logHandler = null;
	private static boolean showHiddenEvents = true;

	public static Logger logger()
	{
		if (null == logger)
		{
			logger = Logger.getLogger("CMLInterpreterLogger");
			logger.setLevel(Level.FINE);

			logHandler = new Handler()
			{

				@Override
				public void publish(LogRecord record)
				{
					Console.out.println(record.getMessage());
				}

				@Override
				public void flush()
				{

				}

				@Override
				public void close() throws SecurityException
				{

				}
			};
			// logHandler.setFilter(new Filter()
			// {
			//
			// @Override
			// public boolean isLoggable(LogRecord record) {
			// record.getLevel()
			// return false;
			// }
			//
			// })
			logger.addHandler(logHandler);
		}

		return logger;

	}

	public static boolean expandShowHiddenEvents()
	{
		return showHiddenEvents;
	}

	public static void expandHiddenEvents(boolean showHiddenEvents)
	{
		CmlRuntime.showHiddenEvents = showHiddenEvents;
	}

}
