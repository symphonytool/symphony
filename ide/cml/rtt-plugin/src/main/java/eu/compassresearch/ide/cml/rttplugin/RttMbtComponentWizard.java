package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
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
            IProject project = (IProject) selectedObject;
            WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
            IFolder newFolder = newFolderPage.createNewFolder();
            String projectName = project.getName() + File.separator + newFolder.getName();
            RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");
            client.createProject(projectName);
          }
        return true;
      }
  }
