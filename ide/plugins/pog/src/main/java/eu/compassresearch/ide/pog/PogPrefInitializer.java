package eu.compassresearch.ide.pog;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class PogPrefInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PogPluginPrefConstants.POG_VIEW_CHOICE, PogPluginPrefConstants.PRED_ONLY);
	}

}
