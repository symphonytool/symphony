package eu.compassresearch.ide.modelchecker;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class ModelcheckerPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = CmlMCPlugin.getDefault().getPreferenceStore();
		store.setDefault(MCConstants.INSTANCES_NUMBER, 1);
	}

}
