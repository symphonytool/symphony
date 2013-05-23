package eu.compassresearch.ide.cml.ui.wizard;

import org.overture.ide.ui.wizard.VdmNewFileWizard;

public class NewCmlProcessWizard extends VdmNewFileWizard {

	@Override
	protected String getPageDescription() {		
		return "Choose new CML Process name and location";
	}

	@Override
	protected String getPageName() {		
		return "CML New Process Wizard";
	}

	@Override
	protected String getPageTitle() {		
		return "CML New Process Wizard";
	}

	@Override
	protected String getFileExtension()
	{
		return "cml";
	}
	
	@Override
	protected String getFileTemplate(String fileName)
	{
		return "process " + fileName + " = \nbegin\n\n@Skip\n\nend";
	}
}
