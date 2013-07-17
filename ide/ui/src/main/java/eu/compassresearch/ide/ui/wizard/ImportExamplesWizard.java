package eu.compassresearch.ide.ui.wizard;

import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.overture.ide.ui.wizard.pages.WizardProjectsImportPageProxy;

import eu.compassresearch.ide.ui.CmlUIPlugin;
import eu.compassresearch.ide.ui.ICmlUiConstants;

public class ImportExamplesWizard extends Wizard implements IImportWizard
{
	private WizardProjectsImportPageProxy importPageProxy = new WizardProjectsImportPageProxy();

	public ImportExamplesWizard()
	{

	}


	@Override
	public void createPageControls(Composite pageContainer)
	{
		super.createPageControls(pageContainer);
		try
		{
			this.importPageProxy.setBundleRelativeInputPath(ICmlUiConstants.PLUGIN_ID, "examples/cml-examples.zip");
		} catch (IOException e)
		{
			CmlUIPlugin.logErrorMessage("Failed to get path for embedded exmaples.zip");
		}
		this.importPageProxy.createPageControlsPostconfig();

	}

	@Override
	public void addPages()
	{
		super.addPages();
		addPage(this.importPageProxy.getPage());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{

	}

	@Override
	public boolean performFinish()
	{
		this.importPageProxy.performFinish();
		return true;
	}

	@Override
	public boolean performCancel()
	{
		this.importPageProxy.performCancel();
		return true;
	}

}
