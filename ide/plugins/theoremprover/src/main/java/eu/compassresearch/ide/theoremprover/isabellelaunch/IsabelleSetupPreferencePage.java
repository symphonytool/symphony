package eu.compassresearch.ide.theoremprover.isabellelaunch;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.ide.theoremprover.CmlTPPlugin;
import eu.compassresearch.ide.theoremprover.commands.LaunchIsabelleHandler;

public class IsabelleSetupPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage
{

	public IsabelleSetupPreferencePage()
	{
		super();
		setDescription("Setup values for using the Symphony theorem proving support");
		setTitle("Theorem Prover Setup");
	}

	@Override
	public void init(IWorkbench workbench)
	{
		setDescription("Setup values for using the Symphony theorem proving support");
		setTitle("Theorem Prover Setup");
	}

	@Override
	protected void createFieldEditors()
	{
		if (LaunchIsabelleHandler.isMacPlatform())
		{
			addField(new StringFieldEditor(IIsabelleConstants.ATTR_LOCATION, "Isabelle application location", getFieldEditorParent()));
		} else
		{
			addField(new DirectoryFieldEditor(IIsabelleConstants.ATTR_LOCATION, "Isabelle application location", getFieldEditorParent()));
		}
		if (!LaunchIsabelleHandler.isWindowsPlatform())
		{
			addField(new DirectoryFieldEditor(IIsabelleConstants.ATTR_SESSION_DIRS, "CML theory location", getFieldEditorParent()));
		}
		addField(new BooleanFieldEditor(IIsabelleConstants.Z3_NON_COMMERCIAL, "Check box if theorem prover is for non commercial use", getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return CmlTPPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected void performDefaults()
	{
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(IIsabelleConstants.ATTR_LOCATION, "");
		if (!LaunchIsabelleHandler.isWindowsPlatform())
		{
			store.setDefault(IIsabelleConstants.ATTR_SESSION_DIRS, "");
		}
		store.setDefault(IIsabelleConstants.Z3_NON_COMMERCIAL, false);
		super.performDefaults();
	}

}
