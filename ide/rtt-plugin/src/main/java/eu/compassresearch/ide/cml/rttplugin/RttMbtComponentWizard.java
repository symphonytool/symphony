package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;

import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtComponentWizard extends BasicNewFolderResourceWizard {
	@Override
	public boolean performFinish() {
		Object selectedObject = selection.getFirstElement();
		if (selectedObject instanceof IProject) {
			// get selected object
			IProject project = (IProject) selectedObject;

			// create RTT-MBT TMS client
			RttMbtClient client = Activator.getClient();

			// get workspace
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			File workspaceDirectory = workspace.getRoot().getLocation()
					.toFile();

			// pass workspace and cml project information to client
			client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());
			client.setCmlProject(project.getFullPath().toString());
			client.addLogMessage("projectWorkingDir: '"
					+ client.getCmlWorkspace() + client.getCmlProject() + "'\n");

			// test connection to rtt-mbt-tms server
			if (client.testConenction()) {
				client.addLogMessage("[PASS]: test RTT-MBT server connection\n");
			} else {
				client.addErrorMessage("[FAIL]: test RTT-MBT server connection\n");
				return false;
			}

			// create folder
			WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
			IFolder newFolder = newFolderPage.createNewFolder();

			// get folder name
			String projectName = newFolder.getName();

			// start RTT-MBT-TMS session
			if (client.beginRttMbtSession()) {
				client.addLogMessage("[PASS]: begin RTT-MBT session\n");
			} else {
				client.addErrorMessage("[FAIL]: begin RTT-MBT session\n");
				return false;
			}

			// download templates
			if (client.downloadDirectory("templates")) {
				client.addLogMessage("[PASS]: downloading templates\n");
			} else {
				client.addErrorMessage("[FAIL]: downloading templates\n");
				return false;
			}

			// create/select a new project
			if (client.createProject(projectName)) {
				client.addLogMessage("[PASS]: create initial project structure\n");
			} else {
				client.addErrorMessage("[FAIL]: create initial project structure\n");
				return false;
			}
		}
		return true;
	}
}
