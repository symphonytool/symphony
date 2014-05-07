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
	private StringFieldEditor username;
	private StringFieldEditor password;

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
		setDescription("Server settings\n" +
					   "note: Username and Password are used for HTTP(s) authentication if the server name is an URL.");

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
		String tooltip;
		server = new StringFieldEditor("RttMbtServer", "Server:", getFieldEditorParent());
		tooltip = "The server name or ip address of the RTT-MBT server. For HTTP(S) access mode, please specify the complete URL here.";
		server.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		server.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		server.load();
		if (server.getStringValue().compareTo("") == 0) {
			server.loadDefault();
		}
		addField(server);
		port = new IntegerFieldEditor("RttMbtServerPort", "Port:", getFieldEditorParent());
		tooltip = "The port of the RTT-MBT server. Defaults are 9116 for direct RTT-MBT server access, 80 for HTTP access mode and 443 for HTTPS access mode";
		port.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		port.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		addField(port);
		user = new StringFieldEditor("RttMbtUserName", "Name:", getFieldEditorParent());
		tooltip = "Your real name for documentation purposes";
		user.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		user.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		user.load();
		if (user.getStringValue().compareTo("") == 0) {
			user.loadDefault();
		}
		addField(user);
		id = new StringFieldEditor("RttMbtUserId", "User-ID:", getFieldEditorParent());
		tooltip = "A unique user id. Default is your email address.";
		id.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		id.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		id.load();
		if (id.getStringValue().compareTo("") == 0) {
			id.loadDefault();
        }
		addField(id);

		username = new StringFieldEditor("RttMbtHttpUsername", "Username:", getFieldEditorParent());
		tooltip = "The username for HTTP(S) authentication";
		username.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		username.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		username.load();
		if (username.getStringValue().compareTo("") == 0) {
			username.loadDefault();
		}
		addField(username);

		password = new StringFieldEditor("RttMbtHttpPassword", "Password:", getFieldEditorParent());
		tooltip = "The password for HTTP(S) authentication";
		password.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		password.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		password.getTextControl(getFieldEditorParent()).setEchoChar('*');
		password.load();
		if (password.getStringValue().compareTo("") == 0) {
			password.loadDefault();
		}
		addField(password);

	}

	public void init(IWorkbench workbench) {
	}
}
