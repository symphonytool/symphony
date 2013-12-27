package eu.compassresearch.ide.rttmbt;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtComponentWizard extends BasicNewFolderResourceWizard {
	@Override
	public boolean performFinish() {
		// local variables
		Object selectedObject = selection.getFirstElement();
		String cmlproject = null;
		URI parentLocation = null;

		// get/create RTT-MBT TMS client
		RttMbtClient client = Activator.getClient();

		if (selectedObject instanceof IProject) {
			// get selected object
			IProject project = (IProject) selectedObject;
			cmlproject = project.getFullPath().toString().substring(1);
			parentLocation = project.getLocationURI();
		}
		if (selectedObject instanceof IFolder) {
			IFolder folder = (IFolder) selectedObject;
			cmlproject = folder.getFullPath().toString().substring(1);
			parentLocation = folder.getLocationURI();
		}
		if ((cmlproject != null) && (parentLocation != null)) {
			// get workspace
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			File workspaceDirectory = workspace.getRoot().getLocation()
					.toFile();

			// pass workspace and cml project information to client
			client.setWorkspacePath(workspaceDirectory.getAbsolutePath());
			client.setWorkspaceProjectPrefix(cmlproject);

			// test connection to rtt-mbt-tms server
			if (client.testConenction()) {
				if (client.getVerboseLogging()) {
					client.addLogMessage("[PASS]: test RTT-MBT server connection");
				}
			} else {
				client.addErrorMessage("[FAIL]: test RTT-MBT server connection");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return false;
			}
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 10);

			// create folder
			WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
			IFolder newFolder = newFolderPage.createNewFolder();

			// get folder name
			String projectName = newFolder.getName();
			client.setWorkspaceProjectName(projectName);
			client.setRttProjectName(projectName);
			String fileSystemPath = RttMbtClient.getAbsolutePathFromFileURI(parentLocation);
			if (fileSystemPath == null) {
				client.addErrorMessage("[FAIL]: unable to retrieve parent location.");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return false;
			}
			client.setRttProjectPath(fileSystemPath + File.separator + projectName);
			client.addLogMessage("creating RTT-MBT project " + projectName + "... please wait for the task to be finished.");

			// start RTT-MBT-TMS session
			if (client.beginRttMbtSession()) {
				if (client.getVerboseLogging()) {
					client.addLogMessage("[PASS]: begin RTT-MBT session");
				}
			} else {
				client.addErrorMessage("[FAIL]: begin RTT-MBT session");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return false;
			}
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 15);

			// download templates
			if (client.downloadDirectory("templates")) {
				client.addLogMessage("[PASS]: downloading templates");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 75);
			} else {
				client.addErrorMessage("[FAIL]: downloading templates");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return false;
			}

			// create/select a new project
			if (client.createProject(projectName)) {
				client.addLogMessage("[PASS]: create initial project structure");
			} else {
				client.addErrorMessage("[FAIL]: create initial project structure");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return false;
			}
			
			// update progress bar
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: create RTT-MBT folder: no parent project or folder selected!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return false;
		}
		return true;
	}
}
