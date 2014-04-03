package eu.compassresearch.ide.interpreter.launch;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.json.simple.JSONObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.ide.debug.core.IDebugConstants;
import org.overture.ide.debug.core.IDebugPreferenceConstants;
import org.overture.ide.debug.core.VdmDebugPlugin;
import org.overture.ide.debug.core.dbgp.DbgpServer;
import org.overture.ide.debug.core.launching.VdmLaunchConfigurationDelegate;
import org.overture.ide.debug.utils.ClassPathCollector;
import org.overture.ide.debug.utils.VdmProjectClassPathCollector;

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterArguments;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public class CmlLaunchConfigurationDelegate extends LaunchConfigurationDelegate
{
	private static final int FROM_PORT = 10000;
	private static final int TO_PORT = 50000;

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

			Map<String, Object> configurationMap = createDebuggerArgumentMap(configuration, port);

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
			CmlDebugTarget target = new CmlDebugTarget(launch, launchExternalProcess(launch, configuration, JSONObject.toJSONString(configurationMap), "CML Debugger"), project, port, shouldAutoTerminate());
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

	protected boolean shouldAutoTerminate()
	{
		return true;
	}

	protected Map<String, Object> createDebuggerArgumentMap(
			ILaunchConfiguration configuration, int port) throws CoreException,
			IOException
	{
		// Write out the launch configuration to the interpreter runner
		Map<String, Object> configurationMap = new HashMap<String, Object>();
		configurationMap.put(CmlInterpreterArguments.PROCESS_NAME.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, ""));
		configurationMap.put(CmlInterpreterArguments.CML_SOURCES_PATH.key, getSources(configuration));
		configurationMap.put(CmlInterpreterArguments.CML_EXEC_MODE.key, configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true));

		configurationMap.put(CmlInterpreterArguments.HOST.key, "localhost");
		configurationMap.put(CmlInterpreterArguments.PORT.key, port);

		configurationMap.put(CmlInterpreterArguments.AUTO_FILTER_TOCK_EVENTS.key, CmlDebugPlugin.getDefault().getPreferenceStore().getString(ICmlDebugConstants.PREFERENCES_AUTO_FILTER_TOCK_EVENTS));

		if (configuration.hasAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS))
		{
			String remoteClass = configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS, "");
			if (!remoteClass.trim().isEmpty())
			{
				configurationMap.put(CmlInterpreterArguments.REMOTE_NAME.toString(), remoteClass);
			}
		}
		return configurationMap;
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
		List<String> additionalCpEntries = new Vector<String>();

		if (true/* logging disabled */)
		{
			String defaultLog4JProperties = getDefaultLog4JProperties();
			if (defaultLog4JProperties != null)
			{
				additionalCpEntries.add(defaultLog4JProperties);
			}
		}

		Collection<? extends String> classPath = removeDublicateLogAppenders(VdmProjectClassPathCollector.getClassPath(getProject(configuration), collectRequiredBundleIds(ICmlDebugConstants.ID_CML_PLUGIN_NAME), additionalCpEntries.toArray(new String[] {})));
		commandArray.addAll(Arrays.asList(new String[] { "-cp",
				VdmProjectClassPathCollector.toCpCliArgument(classPath) }));
		commandArray.add(ICmlDebugConstants.DEBUG_ENGINE_CLASS);
		commandArray.addAll(1, getVmArguments(configuration));
		commandArray.add(config);

		// Execute in a new JVM process
		ProcessBuilder pb = new ProcessBuilder(commandArray);

		pb.directory(getProject(configuration).getLocation().toFile());

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

	private Collection<? extends String> removeDublicateLogAppenders(
			List<String> list)
	{
		boolean found = false;
		List<String> res = new Vector<String>();
		for (String f : list)
		{
			if (f.indexOf("slf4j-log4j12.jar") > 0)
			{
				if (!found)
				{
					res.add(f);
					found = true;
				}

				continue;
			}
			res.add(f);
		}

		return res;
	}

	private String getDefaultLog4JProperties()
	{
		final Bundle bundle = Platform.getBundle(CmlDebugPlugin.PLUGIN_ID);
		if (bundle != null)
		{
			URL buildInfoUrl = FileLocator.find(bundle, new Path("log4j.properties"), null);

			try
			{
				if (buildInfoUrl != null)
				{
					URL buildInfofileUrl = FileLocator.toFileURL(buildInfoUrl);
					if (buildInfofileUrl != null)
					{
						File file = new File(buildInfofileUrl.getFile());

						return file.getParentFile().getAbsolutePath();
					}

				}
			} catch (IOException e)
			{
			}
		}
		return null;
	}

	private Collection<? extends String> getVmArguments(
			ILaunchConfiguration configuration) throws CoreException
	{
		List<String> options = new Vector<String>();
		String opt = configuration.getAttribute(IDebugConstants.VDM_LAUNCH_CONFIG_VM_MEMORY_OPTION, "");
		if (opt.trim().length() != 0)
		{
			String[] opts = opt.split(" ");
			for (String o : opts)
			{
				o = o.trim();
				if (o.startsWith("-"))
				{
					options.add(o);
				}
			}
		}

		// is logging of?
		if (configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_ENABLE_LOGGING, false))
		{
			options.add("-Dlog4j.configuration=log4j-on.properties");
		} else
		{
			options.add("-Dlog4j.configuration=log4j-off.properties");
		}

		if (VdmDebugPlugin.getDefault().getPreferenceStore().getBoolean(IDebugPreferenceConstants.PREF_DBGP_ENABLE_EXPERIMENTAL_MODELCHECKER))
		{
			final Bundle bundle = Platform.getBundle(VdmLaunchConfigurationDelegate.ORG_OVERTURE_IDE_PLUGINS_PROBRUNTIME);
			if (bundle != null)
			{
				URL buildInfoUrl = FileLocator.find(bundle, new Path("build_info.txt"), null);

				try
				{
					if (buildInfoUrl != null)
					{
						URL buildInfofileUrl = FileLocator.toFileURL(buildInfoUrl);
						if (buildInfofileUrl != null)
						{
							File file = new File(buildInfofileUrl.getFile());
							if (ClassPathCollector.isWindowsPlatform())
							{
								options.add("-Dprob.home=\""
										+ file.getParentFile().getPath() + "\"");
							} else
							{
								options.add("-Dprob.home="
										+ file.getParentFile().getPath());
							}
						}

					}
				} catch (IOException e)
				{
				}
			}

		}

		return options;
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

	public static String[] collectRequiredBundleIds(String bundleId)
	{
		List<String> bundleIds = new ArrayList<String>();
		bundleIds.add(bundleId);

		final Bundle bundle = Platform.getBundle(bundleId);
		if (bundle == null)
		{
			System.out.println("Bundle " + bundleId + " not found.");
			return null;
		}

		try
		{
			String requires = (String) bundle.getHeaders().get(Constants.REQUIRE_BUNDLE);
			ManifestElement[] elements = ManifestElement.parseHeader(Constants.REQUIRE_BUNDLE, requires);

			for (ManifestElement manifestElement : elements)
			{
				String value = manifestElement.getValue();
				if (value.startsWith("org.overture")
						|| value.startsWith("eu.compassresearch"))
				{
					bundleIds.add(value);
				}
			}

			if (VdmDebugPlugin.getDefault().getPreferenceStore().getBoolean(IDebugPreferenceConstants.PREF_DBGP_ENABLE_EXPERIMENTAL_MODELCHECKER))
			{
				bundleIds.add(VdmLaunchConfigurationDelegate.ORG_OVERTURE_IDE_PLUGINS_PROBRUNTIME);
			}

		} catch (BundleException e)
		{
			return null;
		}
		return bundleIds.toArray(new String[] {});
	}

}
