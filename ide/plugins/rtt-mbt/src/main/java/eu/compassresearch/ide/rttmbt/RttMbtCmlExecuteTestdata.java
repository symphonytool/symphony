package eu.compassresearch.ide.rttmbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;

import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtCmlExecuteTestdata extends RttMbtAbstractTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Execute Test Data on CML Model";
	}

	public RttMbtCmlExecuteTestdata() {
		isServerConnectionNeeded = false;
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}

		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!");
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.addLogMessage("execute test data on CML model " + selectedObjectName + "... please wait for the task to be finished.");

		// create a new lauch configuration
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType ctype = launchManager.getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);
		ILaunchConfigurationWorkingCopy lcwc = null;
		try {
			lcwc = ctype.newInstance(null, launchManager.generateLaunchConfigurationName(project.getName()));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String projectname = RttMbtProjectPropertiesPage.getPropertyValue(project,"RttMbtCmlProjectName");
		String processname = RttMbtProjectPropertiesPage.getPropertyValue(project,"RttMbtCmlProcessName");
		if ((projectname == null) || (processname == null)) {
			client.addErrorMessage("no CML project and process association defined in project properties!");
			return Status.CANCEL_STATUS;
		}
		if (lcwc != null)  {
			// <launchConfiguration type="eu.compassresearch.ide.interpreter.launchConfigurationType">
			lcwc.setAttribute("cml_launch_config_enable_logging", false);
			lcwc.setAttribute("cml_launch_config_process_name", processname);
			lcwc.setAttribute("cml_launch_config_project", projectname);
			lcwc.setAttribute("cml_launch_config_remote_debug", false);
			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_USE_REMOTE_CONTROL, true);
			lcwc.setAttribute("cml_launch_config_remote_interpreter_class", "eu.compassresearch.core.interpreter.remote.rttmbt.testdriver.CmlTestDriver");
			lcwc.setAttribute("eu.compassresearch.ide.interpreter.is_animation", false);
			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, processname);
			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, projectname);
			try {
				lcwc.doSave();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Find project in workspace
		String projectroot = "";
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int idx = 0; idx < projects.length; idx++) {
			if (projects[idx].getName().compareTo(projectname) == 0) {
				projectroot = RttMbtClient.getAbsolutePathFromFileURI(projects[idx].getLocationURI());
			}
		}
		File projectdir = new File(projectroot);
		if (projectdir.isDirectory()) {
			
			File libdir = new File(projectroot, "lib");
			if (libdir.isDirectory()) {
				// check for signals file
				File inputSignals = new File(selectedObjectFilesystemPath + File.separator + "model" + File.separator + "signals.json");
				if (inputSignals.isFile()) {
					// copy test data (signals.json) to projectname/lib folder
					// TODO!
					try {
						File outputSignals = new File(libdir, "signals.json");
						outputSignals.createNewFile();
						FileInputStream fromSignals = new FileInputStream(inputSignals);
						FileOutputStream toSignals = new FileOutputStream(outputSignals);
						byte[] buffer = new byte[1024];
						int bytes_read = fromSignals.read(buffer);
						while (bytes_read != -1) {
							toSignals.write(buffer, 0, bytes_read);
							bytes_read = fromSignals.read(buffer);
						}
						fromSignals.close();
						toSignals.close();
					}
					catch (IOException e) {
						client.addErrorMessage("unable to copy testdata in file '" + inputSignals.getAbsolutePath() + "' to CML project '" + projectname + "'!");
					}
					
					// execute test data
					DebugUITools.launch(lcwc, "debug");
				} else {
					client.addErrorMessage("unable to find test data in '" + selectedObjectFilesystemPath + "'! Please generate test procedure.");
				}
			} else {
				client.addErrorMessage("unable to find directory lib/ in CML-project '" + projectname + "'!");
			}
		} else {
			client.addErrorMessage("unable to retrieve valid location for CML-project '" + projectname + "'!");
		}

		// cleanup
		client.addLogMessage("[STARTED]: execute test data on CML model " + selectedObjectName + " with CML process " + processname + " in project " + projectname);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
