package eu.compassresearch.ide.theoremprover.isabellelaunch;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WorkbenchPreferencePage1 extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage
{

	@Override
	public void init(IWorkbench workbench)
	{

	}

	@Override
	protected void createFieldEditors()
	{
		addField(new DirectoryFieldEditor(IIsabelleConstants.ATTR_LOCATION, "Isabelle location", getFieldEditorParent()));
		addField(new BooleanFieldEditor(IIsabelleConstants.Z3_NON_COMMERCIAL, "Non Commercial", getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	protected void performDefaults()
	{
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(IIsabelleConstants.ATTR_LOCATION, "");
		store.setDefault(IIsabelleConstants.Z3_NON_COMMERCIAL, false);
		super.performDefaults();
	}

}
