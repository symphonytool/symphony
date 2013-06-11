package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public RttMbtPreferencesPage() {
		super(GRID);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);

		// get RTT-MBT status information
		String version = Platform.getBundle("eu.compassresearch.ide.cml.rtt-plugin").getHeaders().get("Bundle-Version");
		String description = "RTT-MBT plugin version " + version + "\n";
		RttMbtClient client = Activator.getClient();
		if (client != null) {
			description += "\nserver:" + client.getRttMbtServer() +
					       "\n  version: " + client.getRttMbtServerVersion() +
					       "\n  uptime: " + client.getRttMbtServerUptime();
		}
		setDescription(description);

		IPropertyChangeListener listener =
		new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Activator.updatePreferences();
			}
		};
		store.addPropertyChangeListener(listener);
	}
	
	public void createFieldEditors() {
	}

	public void init(IWorkbench workbench) {
	}
	
}