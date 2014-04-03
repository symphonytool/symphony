package eu.compassresearch.ide.rttmbt;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class RttMbtPreferencesServerPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private StringFieldEditor server;
	private IntegerFieldEditor port;
	private StringFieldEditor user;
	private StringFieldEditor id;

	@Override
	protected void performDefaults() {
		super.performDefaults();
		server.setStringValue("<RTT-MBT server name>");
		port.setStringValue("9116");
		user.setStringValue("<your name>");
		id.setStringValue("anonymous@domain");
	}

	public RttMbtPreferencesServerPage() {
		super(GRID);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);
		setDescription("Server settings");

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
		server = new StringFieldEditor("RttMbtServer",
									   "Server:",
									   getFieldEditorParent());
		server.load();
		if (server.getStringValue().compareTo("") == 0) {
			server.loadDefault();
		}
		addField(server);
		port = new IntegerFieldEditor("RttMbtServerPort",
		                              "Port:",
		                              getFieldEditorParent());
		addField(port);
		user = new StringFieldEditor("RttMbtUserName",
									 "Name:",
									 getFieldEditorParent());
		user.load();
		if (user.getStringValue().compareTo("") == 0) {
			user.loadDefault();
		}
		addField(user);
		id = new StringFieldEditor("RttMbtUserId",
		                           "User-ID:",
		                           getFieldEditorParent());
		id.load();
		if (id.getStringValue().compareTo("") == 0) {
			id.loadDefault();
        }
		addField(id);
	}

	public void init(IWorkbench workbench) {
	}
}
