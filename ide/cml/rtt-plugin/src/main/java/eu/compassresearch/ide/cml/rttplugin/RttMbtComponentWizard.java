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
        	// create RTT-MBT TMS client
        	RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");
    		IWorkspace workspace = ResourcesPlugin.getWorkspace();
    		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
    		client.setWorkspace(workspaceDirectory.getAbsolutePath());

    		// test connection to rtt-mbt-tms server
    		if (client.testConenction()) {
    			System.out.println("[PASS]: test RTT-MBT server connection");
    		} else {
    			System.err.println("[FAIL]: test RTT-MBT server connection");
    			return false;
    		}

            IProject project = (IProject) selectedObject;
            WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
            IFolder newFolder = newFolderPage.createNewFolder();
            String projectName = project.getName() + File.separator + newFolder.getName();

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
    		client.createProject(projectName);
          }
        return true;
      }
  }
