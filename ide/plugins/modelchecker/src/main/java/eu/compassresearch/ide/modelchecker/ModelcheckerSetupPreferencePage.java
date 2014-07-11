package eu.compassresearch.ide.modelchecker;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ModelcheckerSetupPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public ModelcheckerSetupPreferencePage(){
		super();
		//setDescription("Setup values for using the Symphony model checking support");
		//setTitle("Model Checker Setup");
		//IPreferenceStore store = CmlMCPlugin.getDefault().getPreferenceStore();
		//setPreferenceStore(store);
	}

	@Override
	public void init(IWorkbench workbench){
		setDescription("Setup values for using the Symphony model checking support");
		setTitle("Model Checker Setup");
	}

	@Override
	protected void createFieldEditors() {
		
		//if (MCPluginUtility.isWindowsPlatform()){
			IntegerFieldEditor instances = new IntegerFieldEditor(MCConstants.INSTANCES_NUMBER, 
					"Number of instances handled by FORMULA", getFieldEditorParent());
				addField(instances);
		//} 
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore(){
		return CmlMCPlugin.getDefault().getPreferenceStore();
	}
	
	//@Override
	//protected void performDefaults(){
	//	IPreferenceStore store = getPreferenceStore();
	//	store.setDefault(MCConstants.INSTANCES_NUMBER, 1);
	//}

	
}
