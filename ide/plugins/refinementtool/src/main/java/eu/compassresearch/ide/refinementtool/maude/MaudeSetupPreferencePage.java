package eu.compassresearch.ide.refinementtool.maude;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.ide.refinementtool.CmlRefinePlugin;
import eu.compassresearch.ide.refinementtool.RefConstants;

public class MaudeSetupPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	
	public MaudeSetupPreferencePage()
	{
		super();
		setDescription("Setup values for using Maude for refinement");
		setTitle("Maude Setup");
	}

	@Override
	public void init(IWorkbench workbench)
	{
		setDescription("Setup values for using Maude for refinement");
		setTitle("Maude Setup");
	}

	@Override
	protected void createFieldEditors()
	{
		addField(new FileFieldEditor(RefConstants.MAUDE_LOC, "Maude binary location", getFieldEditorParent()));
		addField(new FileFieldEditor(RefConstants.MAUDE_THY, "Maude CML location", getFieldEditorParent()));
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore()
	{
		return CmlRefinePlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected void performDefaults()
	{
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(RefConstants.MAUDE_LOC, "");
		store.setDefault(RefConstants.MAUDE_THY, "");
		super.performDefaults();
	}

	
}
