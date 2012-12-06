package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import eu.compassresearch.ide.cml.interpreter_plugin.model.CmlDebugTarget;


public class CmlLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

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
//			IWorkbench workbench = PlatformUI.getWorkbench();
//			   workbench.showPerspective("org.eclipse.debug.ui.DebugPerspective", 
//			      workbench.getActiveWorkbenchWindow());
			
			// set launch encoding to UTF-8. Mainly used to set console encoding.
			launch.setAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING, "UTF-8");
			
//			IProject project = getProject(configuration);
			//final IDebugTarget target = launch.getDebugTarget();
			
			//Write out the launch configuration to the interpreter runner
			JSONObject obj = serializeLaunchConfigurationToJSON(configuration);
			obj.put("mode", mode);
			
			String mainJavaClass = "eu.compassresearch.ide.cml.interpreter_plugin.CmlInterpreterRunner";
			
			if (mode.equals(ILaunchManager.DEBUG_MODE))
			{
				
				//IDbgpService service = DebugPlugin.getDefault().getDbgpService();

//				if (!service.available())
//				{
//					abort("Could not create DBGP Service", null);
//				}

				DebugPlugin.getDefault().getBreakpointManager().setEnabled(true);
				
				String[] commandArray = new String[]{
						"java",
						"-cp",
						"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/target/*:" +
						"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/lib/*:" +
						"/home/akm/sandbox/overture_cml/ide/cml/core/lib/*",
						mainJavaClass,
						JSONValue.toJSONString(obj)
				};
				File dir = new File("/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/target/");
				
				//Execute in a new JVM process
				Process process = Runtime.getRuntime().exec(commandArray, null, dir);

				CmlDebugTarget target = new CmlDebugTarget(launch,DebugPlugin.newProcess(launch, process, "CML Debugger"),
						CmlDebugDefaultValues.PORT);
				//				target.setVdmProject(vdmProject);
				launch.addDebugTarget(target);
				
			}
			// Run mode
			else if (mode.equals(ILaunchManager.RUN_MODE))
			{
				//In run mode the debugger should not be enabled
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(false);
				
				IVMInstall vm = JavaRuntime.getDefaultVMInstall(); 
				IVMRunner runner = vm.getVMRunner(mode);
				// Create VM config
				//TODO: this should be added from the config with absolut paths
				VMRunnerConfiguration runConfig = new VMRunnerConfiguration(mainJavaClass, 
												new String[]{"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/target/*",
															"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/lib/*",
															"/home/akm/sandbox/overture_cml/ide/cml/core/lib/*"});
				
								
				runConfig.setProgramArguments(new String[]{JSONValue.toJSONString(obj)});
				//runConfig.setWorkingDirectory(path);
				
				runner.run(runConfig, launch, monitor);
			}
		
		} 
		catch (CoreException e)
		{
			launch.terminate();
			throw e;
		} catch (IOException e) {
			launch.terminate();
			e.printStackTrace();
			//throw new CoreException()e;
		}
		finally
		{
			monitor.done();
		}
		
	}
	
	/**
	 * Converts the launch configuration into a JSON string
	 * @param configuration
	 * @return
	 * @throws CoreException
	 */
	private JSONObject serializeLaunchConfigurationToJSON(ILaunchConfiguration configuration) throws CoreException
	{
		JSONObject obj = new JSONObject();
		for(Object entryObj : configuration.getAttributes().entrySet())
		{
			Entry<String,String> entry = (Entry<String,String>)entryObj;
			obj.put(entry.getKey(), entry.getValue());
		}
		
		return obj;
	}

//	static public IVdmProject getVdmProject(ILaunchConfiguration configuration)
//			throws CoreException
//	{
//
//		IProject project = getProject(configuration);
//
//		if (project != null)
//		{
//			IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);
//			return vdmProject;
//		}
//		return null;
//	}

	static private IProject getProject(ILaunchConfiguration configuration)
			throws CoreException
	{
		return ResourcesPlugin.getWorkspace().getRoot().getProject(configuration.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), ""));
	}
	
	

}
