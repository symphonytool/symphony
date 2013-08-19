package eu.compassresearch.ide.interpreter.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.ide.interpreter.CmlDebugPlugin;

public class WorkbenchPreferencePageInterpreter extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	@Override
	public void init(IWorkbench workbench)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors()
	{
		// addField(new BooleanFieldEditor(ICmlDebugConstants.PREFERENCES_REMOTE_DEBUG, "Enable remote debug",
		// getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return CmlDebugPlugin.getDefault().getPreferenceStore();
	}

}
