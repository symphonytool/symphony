package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtProjectWizard extends BasicNewProjectResourceWizard {
	@Override
	public boolean performFinish() {

		// create RTT-MBT TMS client
		RttMbtClient client = Activator.getClient();

		// get workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();

		// pass workspace information to client
		client.setWorkspacePath(workspaceDirectory.getAbsolutePath());

		// Project are NOT embedded in other projects, so the selection is not evaluated
		client.setWorkspaceProjectPrefix(null);

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
		WizardNewProjectCreationPage newFolderPage = (WizardNewProjectCreationPage) getPages()[0];
		IProject newProject = newFolderPage.getProjectHandle();
		String projectName = newFolderPage.getProjectName();
		IProjectDescription description = workspace.newProjectDescription(projectName);
		File projectdir = new File(RttMbtClient.getAbsolutePathFromFileURI(newFolderPage.getLocationURI()));
		if (!projectdir.getAbsolutePath().startsWith(workspaceDirectory.getAbsolutePath())) {
			description.setLocation(Path.fromOSString(projectdir.getAbsolutePath()));
			description.setName(projectName);
		} else {
			description = null;
		}
		IStatus status = workspace.validateProjectLocation(newProject, Path.fromOSString(projectdir.getAbsolutePath()));
		if (!status.isOK()) {
			System.err.println("invalid project location " + projectdir.getAbsolutePath() + ": " + status.toString());
		}
		try {
			client.addLogMessage("creating Eclipse project resource '" + Path.fromOSString(projectdir.getAbsolutePath()) + "'...");
			newProject.create(description,null);
			newProject.open(null);
		} catch (CoreException e) {
			client.addErrorMessage("[FAIL]: creating project resource '" + Path.fromOSString(projectdir.getAbsolutePath()) + "' failed!");
			client.addErrorMessage(e.toString());
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return false;
		}

		// initialize client
		client.setRttProjectName(projectName);
		client.setRttProjectPath(RttMbtClient.getAbsolutePathFromFileURI(newFolderPage.getLocationURI()));
		client.setWorkspaceProjectName(projectName);
		client.setWorkspaceProjectPrefix(null);
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
		String backupWorkspaceProjectPrefix = client.getWorkspaceProjectPrefix();
		String backupProjectPath = client.getRttProjectPath();
		client.setWorkspaceProjectPrefix(null); // remove project prefix for templates download
		client.setRttProjectPath(client.getWorkspacePath() + File.separator + projectName); // set fake project path for templates download
		if (client.downloadDirectory(client.getWorkspacePath() + File.separator + "templates")) {
			client.addLogMessage("[PASS]: downloading templates");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 75);
			client.setWorkspaceProjectPrefix(backupWorkspaceProjectPrefix); // restore project prefix again
			client.setRttProjectPath(backupProjectPath); // restore file system project path
		} else {
			client.addErrorMessage("[FAIL]: downloading templates");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.setWorkspaceProjectPrefix(backupWorkspaceProjectPrefix); // restore project prefix again
			client.setRttProjectPath(backupProjectPath); // restore file system project path
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
		return true;
	}
}
