package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
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
			
			if (mode.equals(ILaunchManager.DEBUG_MODE))
			{
				try {
					System.out.println(CmlLaunchConfigurationDelegate.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} 
				
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(true);
				
				//Execute in a new JVM process
				CmlDebugTarget target = new CmlDebugTarget(launch,
						launchExternalProcess(launch,JSONValue.toJSONString(obj),"CML Debugger"),
						CmlDebugDefaultValues.PORT);
				//				target.setVdmProject(vdmProject);
				launch.addDebugTarget(target);
				
			}
			// Run mode
			else if (mode.equals(ILaunchManager.RUN_MODE))
			{
				//In run mode the debugger should not be enabled
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(false);
				
				//Execute in a new JVM process
				launchExternalProcess(launch,JSONValue.toJSONString(obj),"CML Runner");
//				String develCP = "";
//				try {
//					develCP = CmlLaunchConfigurationDelegate.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString();
//				} catch (URISyntaxException e) {
//					e.printStackTrace();
//				} 
//
//				
//				IVMInstall vm = JavaRuntime.getDefaultVMInstall(); 
//				
//				IVMRunner runner = vm.getVMRunner(mode);
//				// Create VM config
//				//TODO: this should be added from the config with absolut paths
//				String mainJavaClass = "eu.compassresearch.core.interpreter.debug.CmlInterpreterRunner";
//				VMRunnerConfiguration runConfig = new VMRunnerConfiguration(mainJavaClass,new String[]{".",develCP  + "lib/*"});
//
//				runConfig.setProgramArguments(new String[]{JSONValue.toJSONString(obj)});
//				//runConfig.setWorkingDirectory(path);
//				
//				System.out.println("lib locs: " + vm.getLibraryLocations());
//				System.out.println("vm args: " + vm.getVMArguments());
//				
//				runner.run(runConfig, launch, monitor);
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
	
	private IProcess launchExternalProcess(ILaunch launch, String config, String name) throws IOException
	{
		
		URI develCP = null;
		 
		try {
			develCP = CmlLaunchConfigurationDelegate.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		
		URI workingdirUri = develCP.resolve("./lib");
		File workingdir = new File(workingdirUri);
		
		String mainJavaClass = "eu.compassresearch.core.interpreter.debug.CmlInterpreterRunner";
		
		System.out.println(workingdirUri.getPath());
		
		String[] commandArray = new String[]{
				"java",
				"-cp",
				workingdirUri.getPath() + "/*:lib/*",
				mainJavaClass,
				config
		};
		
		//Execute in a new JVM process
		//Process process = Runtime.getRuntime().exec(commandArray, null, workingdir);
		Process process = Runtime.getRuntime().exec(commandArray);
		IProcess iprocess = DebugPlugin.newProcess(launch, process, name);
		
		launch.addProcess(iprocess);
		
		return iprocess;
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


}
