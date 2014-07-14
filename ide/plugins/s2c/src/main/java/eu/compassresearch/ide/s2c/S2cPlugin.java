package eu.compassresearch.ide.s2c;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class S2cPlugin extends AbstractUIPlugin
{
	private static S2cPlugin plugin;
	public static final int INTERNAL_ERROR = 120;

	public static final String PLUGIN_ID = ICmlS2cConstants.PLUGIN_ID;
	public static final boolean DEBUG = false;

	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;

	}

	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		// HotCodeReplaceManager.getDefault().shutdown();
		super.stop(context);

	}

	public static S2cPlugin getDefault()
	{
		return plugin;
	}

	public static void logWarning(Exception e)
	{
		log(e);

	}

	// Logging

	public static void log(Throwable t)
	{
		Throwable top = t;
		if (t instanceof DebugException)
		{
			Throwable throwable = ((DebugException) t).getStatus().getException();
			if (throwable != null)
			{
				top = throwable;
			}
		}
		log(new Status(IStatus.ERROR, PLUGIN_ID, INTERNAL_ERROR, "internalErrorLoggedFromVdmDebugPlugin"
				+ top.getMessage(), top));
	}

	public static void log(IStatus status)
	{
		getDefault().getLog().log(status);
	}

	public static void logWarning(String message)
	{
		logWarning(message, null);
	}

	public static void logWarning(String message, Throwable t)
	{
		log(new Status(IStatus.WARNING, PLUGIN_ID, INTERNAL_ERROR, message, t));
	}

	public static void logError(String message)
	{
		logError(message, null);
	}

	public static void logError(String message, Throwable t)
	{
		Throwable top = t;
		if (t instanceof DebugException)
		{
			Throwable throwable = ((DebugException) t).getStatus().getException();
			if (throwable != null)
			{
				top = throwable;
			}
		}
		log(new Status(IStatus.ERROR, PLUGIN_ID, INTERNAL_ERROR, message, top));
	}

}
