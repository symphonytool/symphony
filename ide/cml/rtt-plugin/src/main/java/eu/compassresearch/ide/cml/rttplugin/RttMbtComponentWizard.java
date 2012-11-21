package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;

import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtComponentWizard extends BasicNewFolderResourceWizard
  {
    @Override
    public boolean performFinish()
      {
        Object selectedObject = selection.getFirstElement();
        if (selectedObject instanceof IProject)
          {
        	// get selected object
            IProject project = (IProject) selectedObject;

            // create RTT-MBT TMS client
            
        	RttMbtClient client = Activator.getClient();

        	// get workspace
        	IWorkspace workspace = ResourcesPlugin.getWorkspace();
    		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
    		
    		// pass workspace and cml project information to client
    		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());
    		client.setCmlProject(project.getFullPath().toString());
			System.out.println("projectWorkingDir: '" + client.getCmlWorkspace() + client.getCmlProject() + "'");

    		// test connection to rtt-mbt-tms server
    		if (client.testConenction()) {
    			System.out.println("[PASS]: test RTT-MBT server connection");
    		} else {
    			System.err.println("[FAIL]: test RTT-MBT server connection");
    			return false;
    		}

    		// create folder
            WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
            IFolder newFolder = newFolderPage.createNewFolder();

            // get folder name
            String projectName = newFolder.getName();

    		// start RTT-MBT-TMS session
    		if (client.beginRttMbtSession()) {
    			System.out.println("[PASS]: begin RTT-MBT session");
    		} else {
    			System.err.println("[FAIL]: begin RTT-MBT session");
    			return false;
    		}
    		
    		// download templates
    		if (client.downloadDirectory("templates")) {
    			System.out.println("[PASS]: downloading templates");
    		} else {
    			System.err.println("[FAIL]: downloading templates");
    			return false;
    		}

    		// create/select a new project
    		if (client.createProject(projectName)) {
    			System.out.println("[PASS]: create initial project structure");
    		} else {
    			System.err.println("[FAIL]: create initial project structure");
    			return false;
    		}
          }
        return true;
      }
  }
