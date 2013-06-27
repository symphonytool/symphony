package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private RttMbtClient client;
	private Label valVersion;
	private Label valServerName;
	private Label valServerVersion;
	private Label valUptime;
	private Label valServerConnection;
	private Label valResetServerWorkspace;
	
	@Override
	protected Control createContents(Composite parent) {

		// suppress default and apply button for this page
		noDefaultAndApplyButton();

		// create layout
    	GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        parent.setLayout(layout);

        // create grid data
    	GridData gridData = new GridData();
    	gridData.horizontalAlignment = GridData.FILL;
    	gridData.grabExcessHorizontalSpace = true;
    	gridData.grabExcessVerticalSpace = true;
        
    	// version
		String version = Platform.getBundle("eu.compassresearch.ide.cml.rtt-plugin").getHeaders().get("Bundle-Version");
    	Label capVersion = new Label(parent, SWT.NONE);
    	capVersion.setText("RTT-MBT plugin version:");
    	valVersion = new Label(parent, SWT.NONE);
    	valVersion.setText(version);

    	// get clienbt information
    	client = Activator.getClient();
    	String serverName = "";
		String serverVersion = "";
		String uptime = "";
		if (client != null) {
			serverName = client.getRttMbtServer();
			serverVersion = client.getRttMbtServerVersion();
			uptime = client.getRttMbtServerUptime();
		}

    	// server name
    	Label capServerName = new Label(parent, SWT.NONE);
    	capServerName.setText("Server:");
    	valServerName = new Label(parent, SWT.NONE);
    	valServerName.setText(serverName);

    	// server version
    	Label capServerVersion = new Label(parent, SWT.NONE);
    	capServerVersion.setText("Server version:");
    	valServerVersion = new Label(parent, SWT.NONE);
    	valServerVersion.setText(serverVersion);
    	
    	// server uptime
    	Label capUptime = new Label(parent, SWT.NONE);
    	capUptime.setText("Uptime:");
    	valUptime = new Label(parent, SWT.NONE);
    	valUptime.setText(uptime);

		// add test connection button
	    Button testConnection = new Button(parent, SWT.PUSH);
	    testConnection.setText("Test server connection");
	    testConnection.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) { testServerConnection(); }
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { testServerConnection(); }
			});
    	valServerConnection = new Label(parent, SWT.NONE);
    	valServerConnection.setText("");

		// add test connection button
	    Button resetWorkspace = new Button(parent, SWT.PUSH);
	    resetWorkspace.setText("Reset server workspace");
	    resetWorkspace.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) { resetServerWorkspace(); }
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { resetServerWorkspace(); }
			});
    	valResetServerWorkspace = new Label(parent, SWT.NONE);
    	valResetServerWorkspace.setText("");

    	return super.createContents(parent);
	}

	private void testServerConnection() {
		if (client == null) return;
		valServerConnection.setText("in progress...");
		valServerConnection.pack();
		if (client.testConenction()) {
			valServerConnection.setText("PASS");
			valServerConnection.pack();
			valServerName.setText(client.getRttMbtServer());
			valServerName.pack();
	    	valServerVersion.setText(client.getRttMbtServerVersion());
	    	valServerVersion.pack();
	    	valUptime.setText(client.getRttMbtServerUptime());
	    	valUptime.pack();
		} else {
			valServerConnection.setText("FAIL");
		}
	}
	
	private void resetServerWorkspace() {
		if (client == null) return;
		valResetServerWorkspace.setText("in progress...");
		valResetServerWorkspace.pack();
		if (client.removeRttMbtSession()) {
			valResetServerWorkspace.setText("PASS");
			valResetServerWorkspace.pack();
		} else {
			valResetServerWorkspace.setText("FAIL");
			valResetServerWorkspace.pack();
		}
	}
	
	public RttMbtPreferencesPage() {
		super(GRID);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);

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