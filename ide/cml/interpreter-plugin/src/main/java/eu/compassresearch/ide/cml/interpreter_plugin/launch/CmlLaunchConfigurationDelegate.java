package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.osgi.framework.Bundle;

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlDebugConstants;
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
		} catch(URISyntaxException e)
		{
			launch.terminate();
			e.printStackTrace();
			//throw new CoreException()e;
		}
		finally
		{
			monitor.done();
		}
		
	}
	
	private void WriteFile(InputStream inStream,File outfile) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(outfile);

		byte[] buffer = new byte[4096];  
		int bytesRead;  
		while ((bytesRead = inStream.read(buffer)) != -1) {  
		  fos.write(buffer, 0, bytesRead);  
		}  
		inStream.close();  
		fos.close();  
		
	}
	
	private void unpackInterpreterFromPlugin(URI path) throws IOException, URISyntaxException
	{
		//File tempFile = File.createTempFile("interpreter-with-dependencies", ".jar");

		InputStream jarStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar");
		InputStream jarHashStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar.sha");

		File jarFile = new File(path.getSchemeSpecificPart(),"interpreter-with-dependencies.jar");
		File jarHashFile = new File(path.getSchemeSpecificPart(),"interpreter-with-dependencies.jar.sha");
				
		WriteFile(jarStream,jarFile);
		WriteFile(jarHashStream,jarHashFile);
		
	}
	
	/**
	 * Determines whether the interpreter jar is extracted at the specified path
	 * @param uri The folder where the jar might be located
	 * @return true if it exist else false
	 * @throws IOException
	 */
	private boolean isInterpreterAlreadyExtracted(URI uri) throws IOException
	{
		File jarHashFile = new File(uri.getSchemeSpecificPart(),"interpreter-with-dependencies.jar.sha");
		
		//First check if the jar hash file exists, if not then we re-extract
		if(!jarHashFile.exists())
			return false;
		else
			//if it exists we need to check if the sha1 hash matches
		{
			InputStream jarHashStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar.sha");
			byte[] pluginJarHashBytes = new byte[2*40];
			jarHashStream.read(pluginJarHashBytes);
			
			FileInputStream existingJarHashFS = new FileInputStream(jarHashFile);
			byte[] existingJarHashBytes = new byte[2*40];
			existingJarHashFS.read(existingJarHashBytes);
			
			return Arrays.equals(pluginJarHashBytes, existingJarHashBytes);
		}
		
		
	}
	
	/**
	 * Returns the path of the interpreter-with-dependencies jar. 
	 * If the jar cannot be found or the hash is different from the one
	 * inside the plugin jar, then the interpreter-with-dependencies jar
	 * is extracted at the returned path.
	 * @return Path to the interpreter jar
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private String locateInterpreterFromPlugin() throws IOException, URISyntaxException
	{
		URI bundleURI = getBundleURI().resolve("./");
		
		File jarFile = new File(bundleURI.getSchemeSpecificPart(),"interpreter-with-dependencies.jar");

		//check whether the interpreter jar from the plugin is extracted
		//If not then we extract it else do nothing
		if(!isInterpreterAlreadyExtracted(bundleURI))
		{
			unpackInterpreterFromPlugin(bundleURI);
		}
		
		return jarFile.getAbsolutePath();
	}
	
	private URI getBundleURI()
	{
		Bundle bundle = Platform.getBundle(CmlDebugConstants.ID_CML_PLUGIN_NAME.toString());
		URI uri = URI.create(bundle.getLocation());
		uri = URI.create(uri.getSchemeSpecificPart());
		
		return uri;
	}
	
	private String locateInterpreterJarPath() throws IOException, URISyntaxException
	{
		URI uri = getBundleURI();
		File file = new File(uri.getSchemeSpecificPart());

		if(!file.exists())
			throw new FileNotFoundException("Can't determine the bundle path");
		
		//plugin is a folder and we can access it through the lib folder
		if(file.isDirectory())
		{
			return uri.resolve("./lib").getPath() + "/interpreter-with-dependencies.jar";
		}
		//were in a plugin jar, so we need to extract the interpreter jar from the plugin har
		else
		{
			//extract the interpreter with dependencies for launch
			return locateInterpreterFromPlugin();
		}
		
	}
	
	
	private IProcess launchExternalProcess(ILaunch launch, String config, String name) throws IOException, URISyntaxException
	{
		String interpreterJarPath = locateInterpreterJarPath();
		
		String[] commandArray = new String[]{
				"java",
				"-jar",
				interpreterJarPath,
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
