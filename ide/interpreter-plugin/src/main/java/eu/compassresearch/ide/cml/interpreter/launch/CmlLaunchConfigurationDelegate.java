package eu.compassresearch.ide.cml.interpreter.launch;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

import eu.compassresearch.core.interpreter.debug.CmlDebugDefaultValues;
import eu.compassresearch.ide.cml.interpreter.CmlUtil;
import eu.compassresearch.ide.cml.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.cml.interpreter.model.CmlDebugTarget;


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
			
			//Write out the launch configuration to the interpreter runner
			Map configurationMap = configuration.getAttributes();
			configurationMap.put("mode", mode);
			//Along with the current mode "debug" or "run"
			
			if (mode.equals(ILaunchManager.DEBUG_MODE))
			{
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(true);
				//Execute in a new JVM process
				CmlDebugTarget target = new CmlDebugTarget(launch,
						launchExternalProcess(launch,JSONObject.toJSONString(configurationMap),"CML Debugger"),
						CmlDebugDefaultValues.PORT);
				//				target.setVdmProject(vdmProject);
				launch.addDebugTarget(target);
				
				//switch to the debugging perspective
				SwitchToDebugPerspective();
			}
			// Run mode
			else if (mode.equals(ILaunchManager.RUN_MODE))
			{
				//In run mode the debugger should not be enabled
				DebugPlugin.getDefault().getBreakpointManager().setEnabled(false);
				
				//Execute in a new JVM process
				//launchExternalProcess(launch,JSONObject.toJSONString(configurationMap),"CML Runner");
//				//Execute in a new JVM process
				CmlDebugTarget target = new CmlDebugTarget(launch,
						launchExternalProcess(launch,JSONObject.toJSONString(configurationMap),"CML Runner"),
						CmlDebugDefaultValues.PORT);
				//				target.setVdmProject(vdmProject);
				launch.addDebugTarget(target);
				
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
	
	private void SwitchToDebugPerspective()
	{

		//this can potentially be executed in a different thread so we need to do this
		Display.getDefault().asyncExec(new Runnable() {
            public void run() {
        		
        		try {
        			IWorkbench workbench = PlatformUI.getWorkbench();
					workbench.showPerspective("org.eclipse.debug.ui.DebugPerspective", workbench.getActiveWorkbenchWindow());
				} catch (WorkbenchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
         });
	}
	
//	/**
//	 * Write the inStream to the specified outfile
//	 * @param inStream
//	 * @param outfile
//	 * @throws IOException
//	 */
//	private void WriteFile(InputStream inStream,File outfile) throws IOException
//	{
//		FileOutputStream fos = new FileOutputStream(outfile);
//
//		byte[] buffer = new byte[4096];  
//		int bytesRead;  
//		while ((bytesRead = inStream.read(buffer)) != -1) {  
//		  fos.write(buffer, 0, bytesRead);  
//		}  
//		inStream.close();  
//		fos.close();  
//		
//	}
	
//	/**
//	 * Unpack the interpreter-with-dependencies.jar from the bundle and places it
//	 * at the basePath
//	 * @param basePath path to where the interpreter-with-dependencies.jar is placed
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 */
//	private void unpackInterpreterFromPlugin(String basePath) throws IOException, URISyntaxException
//	{
//		InputStream jarStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar");
//		InputStream jarHashStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar.sha");
//
//		File jarFile = new File(basePath,"interpreter-with-dependencies.jar");
//		File jarHashFile = new File(basePath,"interpreter-with-dependencies.jar.sha");
//				
//		WriteFile(jarStream,jarFile);
//		WriteFile(jarHashStream,jarHashFile);
//	}
	
//	/**
//	 * Determines whether the interpreter jar is extracted at the specified path
//	 * @param uri The folder where the jar might be located
//	 * @return true if it exist else false
//	 * @throws IOException
//	 */
//	private boolean isInterpreterAlreadyExtracted(String basePath) throws IOException
//	{
//		File jarHashFile = new File(basePath,"interpreter-with-dependencies.jar.sha");
//		
//		//First check if the jar hash file exists, if not then we re-extract
//		if(!jarHashFile.exists())
//			return false;
//		else
//			//if it exists we need to check if the sha1 hash matches
//		{
//			InputStream jarHashStream = getClass().getResourceAsStream("/lib/interpreter-with-dependencies.jar.sha");
//			byte[] pluginJarHashBytes = new byte[2*40];
//			jarHashStream.read(pluginJarHashBytes);
//			
//			FileInputStream existingJarHashFS = new FileInputStream(jarHashFile);
//			byte[] existingJarHashBytes = new byte[2*40];
//			existingJarHashFS.read(existingJarHashBytes);
//			existingJarHashFS.close();
//			
//			return Arrays.equals(pluginJarHashBytes, existingJarHashBytes);
//		}
//	}
	
//	/**
//	 * Returns the path of the interpreter-with-dependencies jar. 
//	 * If the jar cannot be found or the hash is different from the one
//	 * inside the plugin jar, then the interpreter-with-dependencies jar
//	 * is extracted at the returned path.
//	 * @return Path to the interpreter jar
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 */
//	private String locateInterpreterFromPlugin() throws IOException, URISyntaxException
//	{
//		File bundleFile = getBundleFile();
//		File jarFile = new File(bundleFile.getParent(),"interpreter-with-dependencies.jar");
//
//		//check whether the interpreter jar from the plugin is extracted
//		//If not then we extract it else do nothing
//		if(!isInterpreterAlreadyExtracted(bundleFile.getParent()))
//		{
//			unpackInterpreterFromPlugin(bundleFile.getParent());
//		}
//		
//		return jarFile.getAbsolutePath();
//	}
//	
//	private File getBundleFile() throws IOException
//	{
//		return FileLocator.getBundleFile(Platform.getBundle(ICmlDebugConstants.ID_CML_PLUGIN_NAME.toString()));
//	}
	
//	/**
//	 * Determines the path String of where the interpreter jar is located.
//	 * This depends on the eclipse mode 
//	 * @return
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 */
//	private String locateInterpreterJarPath() throws IOException, URISyntaxException
//	{
//		File file = getBundleFile();
//		if(!file.exists())
//			throw new FileNotFoundException("Can't determine the bundle path");
//		
//		//plugin is a folder and we can access it through the lib folder
//		if(file.isDirectory())
//		{
//			return file.getCanonicalPath() + "/lib/interpreter-with-dependencies.jar";
//		}
//		//were in a plugin jar, so we need to extract the interpreter jar from the plugin har
//		else
//		{
//			//extract the interpreter with dependencies for launch
//			return locateInterpreterFromPlugin();
//		}
//		
//	}
	
	
	private IProcess launchExternalProcess(ILaunch launch, String config, String name) throws IOException, URISyntaxException, CoreException
	{
		//String interpreterJarPath = locateInterpreterJarPath();
		
		if(isWindowsPlatform())
			//escape quotes or else they disappear
			config = config.replace("\"", "\\\"");
			
			
		List<String> commandArray = new LinkedList<String>();
		
		commandArray.add("java");
		commandArray.addAll(getClassPath());
		commandArray.add("eu.compassresearch.core.interpreter.debug.DebugMain");
		commandArray.add(config);
		
		//Execute in a new JVM process
		ProcessBuilder pb = new ProcessBuilder(commandArray);
		Process process = pb.start();
		IProcess iprocess = DebugPlugin.newProcess(launch, process, name);
		
		launch.addProcess(iprocess);
		
		return iprocess;
	}
	
	private List<String> getClassPath() throws CoreException
	{
		List<String> commandList = new Vector<String>();

		// get the bundled class path of the debugger
		//List<String> entries = ClasspathUtils.collectJars(ICmlDebugConstants.ID_CML_PLUGIN_NAME);
		List<String> entries = CmlUtil.collectJars(ICmlDebugConstants.ID_CML_PLUGIN_NAME);

//		// get the class path for all jars in the project lib folder
//		File lib = new File(project.getLocation().toFile(), "lib");
//		if (lib.exists() && lib.isDirectory())
//		{
//			for (File f : getAllFiles(lib))
//			{
//				if (f.getName().toLowerCase().endsWith(".jar"))
//				{
//					entries.add(toPlatformPath(f.getAbsolutePath()));
//				}
//			}
//		}

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
			if (classPath.length()>0)
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
	
	private static List<File> getAllFiles(File file)
	{
		List<File> files = new Vector<File>();
		if (file.isDirectory())
		{
			for (File f : file.listFiles())
			{
				files.addAll(getAllFiles(f));
			}

		} else
		{
			files.add(file);
		}
		return files;
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
