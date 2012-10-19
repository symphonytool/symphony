package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;

public class ComponentWizard extends BasicNewFolderResourceWizard
  {
    
    @Override
    public boolean performFinish()
      {
        Object selectedObject = selection.getFirstElement();
        if (selectedObject instanceof IProject)
          {
            File currentDir = new File(".");
            IProject p = (IProject) selectedObject;
            System.out.println(p.getName());
            System.out.println(p.getFullPath());
            WizardNewFolderMainPage newFolderPage = (WizardNewFolderMainPage) getPages()[0];
            IFolder newFolder = newFolderPage.createNewFolder();
            File t = new File(newFolder.getFullPath().toString());
            System.out.println(t.getAbsolutePath());
            System.out.println(currentDir.getAbsolutePath() + File.separator
                + t.getAbsolutePath());
          }
        return true;
      }
  }
