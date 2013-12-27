package eu.compassresearch.ide.interpreter.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class WorkbenchPreferencePageInterpreter extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	@Override
	public void init(IWorkbench workbench)
	{

	}

	@Override
	protected void createFieldEditors()
	{
		 addField(new BooleanFieldEditor(ICmlDebugConstants.PREFERENCES_AUTO_FILTER_TOCK_EVENTS, "Automatically filter tock events for none timed specifications",
		 getFieldEditorParent()));

		addField(new ColorFieldEditor(ICmlDebugConstants.PREFERENCES_DEBUG_HIGHLIGHT_COLOR, "Highlight color", getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return CmlDebugPlugin.getDefault().getPreferenceStore();
	}
	
	@Override
	protected void performDefaults()
	{
		PreferenceConverter.setDefault(doGetPreferenceStore(), ICmlDebugConstants.PREFERENCES_DEBUG_HIGHLIGHT_COLOR, ICmlDebugConstants.DEFAULT_HIGHLIGHT_COLOR);
		super.performDefaults();
	}

}
