package eu.compassresearch.ide.modelchecker;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ModelcheckerSetupPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public ModelcheckerSetupPreferencePage(){
		super();
		setDescription("Setup values for using the Symphony model checking support");
		setTitle("Model Checker Setup");
		IPreferenceStore store = CmlMCPlugin.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}

	@Override
	public void init(IWorkbench workbench){
		setDescription("Setup values for using the Symphony model checking support");
		setTitle("Model Checker Setup");
		final IPreferenceStore store = CmlMCPlugin.getDefault().getPreferenceStore();
		final NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		 
		
		//setPreferenceStore(store);
		store.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == MCConstants.INSTANCES_NUMBER) {
					int newNumberOfInstances = Integer.valueOf(event.getNewValue().toString());
					if(newNumberOfInstances > 0){
		              store.setValue(MCConstants.INSTANCES_NUMBER, newNumberOfInstances);
		              context.setNumberOfInstances(newNumberOfInstances);
					}
		        }
				
			}
		});
	}

	@Override
	protected void createFieldEditors() {
		
		if (MCPluginUtility.isWindowsPlatform()){
			IntegerFieldEditor instances = new IntegerFieldEditor(MCConstants.INSTANCES_NUMBER, 
					"Number of instances handled by FORMULA", getFieldEditorParent());
				addField(instances);
		} 
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore(){
		return CmlMCPlugin.getDefault().getPreferenceStore();
	}
	
	@Override
	protected void performDefaults(){
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(MCConstants.INSTANCES_NUMBER, 1);
	}

	
}
