package eu.compassresearch.ide.cml.ui.wizard;

import org.overture.ide.ui.wizard.VdmNewFileWizard;

public class NewCmlClassWizard extends VdmNewFileWizard
  {
	
    
    @Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		boolean parentok = super.performFinish();
		
		
		
		return parentok;
	}

	@Override
    protected String getPageDescription()
      {
        return "Choose new CML Class name and location";
      }
    
    @Override
    protected String getPageName()
      {
        return "CML New Class Wizard";
      }
    
    @Override
    protected String getPageTitle()
      {
        return "CML New Class Wizard";
      }
    
    @Override
    protected String getFileExtension()
      {
        return "cml";
      }
    
    @Override
    protected String getFileTemplate(String fileName)
      {
        return "class " + fileName + " = \nbegin\n\nend";
      }
  }
