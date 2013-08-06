package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class RttMbtPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("RttMbtServer", "localhost");
		store.setDefault("RttMbtServerPort", 9116);
		store.setDefault("RttMbtUserName", "John Doe");
		store.setDefault("RttMbtUserId", "john.doe@nodomain");
		store.setDefault("RttMbtRttTprocPrefix", "RTT_TestProcedures");
		store.setDefault("RttMbtTProcGenCtx", "TestProcedures");
		store.setDefault("ClientMode", "COMPASS");
	}
}
