package eu.compassresearch.ide.interpreter;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class CmlDebugPlugin extends AbstractUIPlugin
{
	private static CmlDebugPlugin plugin;
	public static final int INTERNAL_ERROR = 120;

	public static final String PLUGIN_ID = ICmlDebugConstants.PLUGIN_ID;
	public static final boolean DEBUG = false;

	public static IWorkbenchPage getActivePage()
	{
		IWorkbenchWindow w = getActiveWorkbenchWindow();
		if (w != null)
		{
			return w.getActivePage();
		}
		return null;
	}

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

	public static CmlDebugPlugin getDefault()
	{
		return plugin;
	}

	/**
	 * Returns the active workbench window
	 * 
	 * @return the active workbench window
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow()
	{
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Returns the active workbench shell or <code>null</code> if none
	 * 
	 * @return the active workbench shell or <code>null</code> if none
	 */
	public static Shell getActiveWorkbenchShell()
	{
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null)
		{
			return window.getShell();
		}
		return null;
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

	/**
	 * Initializes a preference store with default preference values for this plug-in.
	 */
	@Override
	protected void initializeDefaultPreferences(IPreferenceStore store)
	{
		PreferenceConverter.setDefault(store, ICmlDebugConstants.PREFERENCES_DEBUG_HIGHLIGHT_COLOR, ICmlDebugConstants.DEFAULT_HIGHLIGHT_COLOR);
		store.setDefault(ICmlDebugConstants.PREFERENCES_AUTO_FILTER_TOCK_EVENTS, ICmlDebugConstants.DEFAULT_AUTO_FILTER_TOCK_EVENTS);
	}
}
