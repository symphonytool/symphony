package eu.compassresearch.ide.pog;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PogPrefPage1 extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage
{

	@Override
	public void init(IWorkbench workbench)
	{
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors()
	{
		addField(new RadioGroupFieldEditor(PogPluginPrefConstants.POG_VIEW_CHOICE, "Proof Obligation Detail Level", 1, new String[][] {
				{ "&Definedness Predicate Only", PogPluginPrefConstants.PRED_ONLY },
				{ "&Full Predicate with Context", PogPluginPrefConstants.CTXT_ETC } }, getFieldEditorParent()));

	}

}
