package eu.compassresearch.ide.interpreter.launch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterLaunchConfigurationConstants;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public class CmlLaunchConfigurationDelegate extends LaunchConfigurationDelegate
{

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
			ICmlProject project = (ICmlProject) getProject(configuration).getAdapter(ICmlProject.class);
			// set launch encoding to UTF-8. Mainly used to set console encoding.
			launch.setAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING, "UTF-8");

			// Write out the launch configuration to the interpreter runner

			Map configurationMap = new HashMap();
			configurationMap.put(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(), configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, ""));
			configurationMap.put(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString(), getSources(configuration));
			configurationMap.put("mode", mode);
			// Along with the current mode "debug" or "run"

			if (mode.equals(ILaunchManager.DEBUG_MODE))
			{
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(true);
				// Execute in a new JVM process
				CmlDebugTarget target = new CmlDebugTarget(launch, launchExternalProcess(launch, configuration, JSONObject.toJSONString(configurationMap), "CML Debugger"), project, CmlDebugDefaultValues.PORT);
				// target.setVdmProject(vdmProject);
				launch.addDebugTarget(target);

				// switch to the debugging perspective
				SwitchToDebugPerspective();
			}
			// Run mode
			else if (mode.equals(ILaunchManager.RUN_MODE))
			{
				// In run mode the debugger should not be enabled
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(false);

				// Execute in a new JVM process
				// launchExternalProcess(launch,JSONObject.toJSONString(configurationMap),"CML Runner");
				// //Execute in a new JVM process
				CmlDebugTarget target = new CmlDebugTarget(launch, launchExternalProcess(launch, configuration, JSONObject.toJSONString(configurationMap), "CML Runner"), project, CmlDebugDefaultValues.PORT);
				launch.addDebugTarget(target);
			}

		} catch (CoreException e)
		{
			launch.terminate();
			throw e;
		} catch (IOException e)
		{
			launch.terminate();
			e.printStackTrace();
			// throw new CoreException()e;
		} catch (URISyntaxException e)
		{
			launch.terminate();
			e.printStackTrace();
			// throw new CoreException()e;
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
		commandArray.addAll(getClassPath());
		commandArray.add("eu.compassresearch.core.interpreter.debug.DebugMain");
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
		}
		IProcess iprocess = DebugPlugin.newProcess(launch, process, name);

		launch.addProcess(iprocess);

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

	private List<String> getClassPath() throws CoreException
	{
		List<String> commandList = new Vector<String>();

		// get the bundled class path of the debugger
		List<String> entries = CmlUtil.collectJars(ICmlDebugConstants.ID_CML_PLUGIN_NAME);

		if (entries.size() > 0)
		{
			commandList.add("-cp");
			StringBuilder classPath = new StringBuilder();
			for (String cp : entries)
			{
				if (cp.toLowerCase().replace("\"", "").trim().endsWith(".jar"))
				{
					classPath.append(toPlatformPath(cp));
					classPath.append(getCpSeperator());
				}
			}
			if (classPath.length() > 0)
				commandList.add(classPath.toString());
			else
				commandList.add(" ");
		}
		return commandList;
	}

	public static boolean isWindowsPlatform()
	{
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

	private String getCpSeperator()
	{
		if (isWindowsPlatform())
			return ";";
		else
			return ":";
	}

	protected static String toPlatformPath(String path)
	{
		if (isWindowsPlatform())
		{
			return "\"" + path + "\"";
		} else
		{
			return path.replace(" ", "\\ ");
		}
	}

}
