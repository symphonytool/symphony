package eu.compassresearch.ide.interpreter.launch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.json.simple.JSONObject;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.ide.debug.core.dbgp.DbgpServer;
import org.overture.ide.debug.utils.VdmProjectClassPathCollector;

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterArguments;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public class CmlLaunchConfigurationDelegate extends LaunchConfigurationDelegate
{
	private static final int FROM_PORT = 10000;
	private static final int TO_PORT = 50000;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException
	{

		if (monitor == null)
		{
			monitor = new NullProgressMonitor();
		}

		monitor.beginTask("Debugger launching", 4);
		if (monitor.isCanceled())
		{
			return;
		}
		try
		{

			int port = CmlDebugDefaultValues.PORT;
			
			if (!configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_DEBUG, false))
			{
				port = DbgpServer.findAvailablePort(FROM_PORT, TO_PORT);
			}
			
			ICmlProject project = (ICmlProject) getProject(configuration).getAdapter(ICmlProject.class);
			// set launch encoding to UTF-8. Mainly used to set console encoding.
			launch.setAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING, "UTF-8");

			// Write out the launch configuration to the interpreter runner
			Map configurationMap = new HashMap();
			configurationMap.put(CmlInterpreterArguments.PROCESS_NAME.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, ""));
			configurationMap.put(CmlInterpreterArguments.CML_SOURCES_PATH.key, getSources(configuration));
			configurationMap.put(CmlInterpreterArguments.CML_EXEC_MODE.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true));

			configurationMap.put(CmlInterpreterArguments.HOST.key, "localhost");
			configurationMap.put(CmlInterpreterArguments.PORT.key, port);

			if (configuration.hasAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS))
			{
				String remoteClass = configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS, "");
				if (!remoteClass.trim().isEmpty())
				{
					configurationMap.put(CmlInterpreterArguments.REMOTE_NAME.toString(), remoteClass);
				}
			}

			if (mode.equals(ILaunchManager.DEBUG_MODE))
			{
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(true);
				// switch to the debugging perspective
				SwitchToDebugPerspective();
			}
			// Run mode
			else if (mode.equals(ILaunchManager.RUN_MODE))
			{
				// In run mode the debugger should not be enabled
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(false);
				// switch to the debugging perspective even though we are in run mode
				SwitchToDebugPerspective();
			}

			// Execute in a new JVM process
			CmlDebugTarget target = new CmlDebugTarget(launch, launchExternalProcess(launch, configuration, JSONObject.toJSONString(configurationMap), "CML Debugger"), project, port);
			launch.addDebugTarget(target);

		} catch (CoreException e)
		{
			launch.terminate();
			throw e;
		} catch (IOException | URISyntaxException e)
		{
			CmlDebugPlugin.logError("Failed to connect to debugger", e);
			launch.terminate();
			monitor.setCanceled(true);
		} finally
		{
			monitor.done();
		}

	}

	private List<String> getSources(ILaunchConfiguration configuration)
			throws CoreException, IOException
	{
		IVdmProject vdmproject = (IVdmProject) getProject(configuration).getAdapter(IVdmProject.class);
		List<String> sources = new LinkedList<String>();
		for (IVdmSourceUnit su : vdmproject.getSpecFiles())
		{
			sources.add(su.getSystemFile().getCanonicalPath());
		}
		return sources;
	}

	static private IProject getProject(ILaunchConfiguration configuration)
			throws CoreException
	{
		return ResourcesPlugin.getWorkspace().getRoot().getProject(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, ""));
	}

	private void SwitchToDebugPerspective()
	{

		// this can potentially be executed in a different thread so we need to do this
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{

				try
				{
					IWorkbench workbench = PlatformUI.getWorkbench();
					workbench.showPerspective("org.eclipse.debug.ui.DebugPerspective", workbench.getActiveWorkbenchWindow());
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_OPTION_VIEW);
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_HISTORY_VIEW);
				} catch (WorkbenchException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private IProcess launchExternalProcess(ILaunch launch,
			ILaunchConfiguration configuration, String config, String name)
			throws IOException, URISyntaxException, CoreException
	{
		if (isWindowsPlatform())
		{
			// escape quotes or else they disappear
			config = config.replace("\"", "\\\"");
		}

		List<String> commandArray = new LinkedList<String>();

		commandArray.add("java");
		// commandArray.addAll(getClassPath());
		commandArray.addAll(VdmProjectClassPathCollector.getClassPath(getProject(configuration), CmlUtil.collectRequiredBundleIds(ICmlDebugConstants.ID_CML_PLUGIN_NAME), new String[] {}));
		commandArray.add(ICmlDebugConstants.DEBUG_ENGINE_CLASS);
		commandArray.add(config);

		// Execute in a new JVM process
		ProcessBuilder pb = new ProcessBuilder(commandArray);
		Process process = null;
		if (!configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_DEBUG, false))
		{
			process = pb.start();
		} else
		{
			System.out.println("Debugger Arguments:\n"
					+ getArgumentString(commandArray.subList(4, commandArray.size())));
			process = Runtime.getRuntime().exec("java -version");
			return null;
		}
		IProcess iprocess = DebugPlugin.newProcess(launch, process, name);

		if (!configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_DEBUG, false))
		{
			launch.addProcess(iprocess);
		}

		return iprocess;
	}

	private String getArgumentString(List<String> args)
	{
		StringBuffer executeString = new StringBuffer();
		for (String string : args)
		{
			executeString.append(string);
			executeString.append(" ");
		}
		return executeString.toString().trim();

	}

	public static boolean isWindowsPlatform()
	{
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

}
