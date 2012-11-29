package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


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
			// set launch encoding to UTF-8. Mainly used to set console encoding.
			launch.setAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING, "UTF-8");
			
			IProject project = getProject(configuration);
			//final IDebugTarget target = launch.getDebugTarget();
			
			IVMInstall vm = JavaRuntime.computeVMInstall(configuration); 
			IVMRunner runner = vm.getVMRunner(mode);
			// Create VM config
			//TODO: this should be added from the config with absolut paths
			VMRunnerConfiguration runConfig = new VMRunnerConfiguration("eu.compassresearch.ide.cml.interpreter_plugin.CmlInterpreterRunner", 
											new String[]{"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/target/*",
														"/home/akm/sandbox/overture_cml/ide/cml/interpreter-plugin/lib/*",
														"/home/akm/sandbox/overture_cml/ide/cml/core/lib/*"});
			JSONObject obj = new JSONObject();
			obj.put(CmlLaunchConfigurationConstants.ATTR_CML_SOURCES_PATH.toString(), 
					configuration.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_PATH.toString(), ""));

			runConfig.setProgramArguments(new String[]{JSONValue.toJSONString(obj)});
			//runConfig.setWorkingDirectory(path);
			
			runner.run(runConfig, launch, monitor);
		
		} 
//		catch (CoreException e)
//		{
//			launch.terminate();
//			throw e;
//		} 
		finally
		{
			monitor.done();
		}
		
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
