package eu.compassresearch.ide.ui.wizard;

import org.overture.ide.ui.wizard.VdmNewFileWizard;

public class NewCmlFileWizard extends VdmNewFileWizard
{

	@Override
	protected String getPageDescription()
	{
		return "Choose new CML file name and location";
	}

	@Override
	protected String getPageName()
	{
		return "CML New File Wizard";
	}

	@Override
	protected String getPageTitle()
	{
		return "CML New File Wizard";
	}

	@Override
	protected String getFileExtension()
	{
		return "cml";
	}

}
