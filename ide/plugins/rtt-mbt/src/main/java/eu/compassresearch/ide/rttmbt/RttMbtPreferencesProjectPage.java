package eu.compassresearch.ide.rttmbt;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

public class RttMbtPreferencesProjectPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private StringFieldEditor rtttprocprefix;
	private StringFieldEditor tprocgenctx;

	@Override
	protected void performDefaults() {
		super.performDefaults();
		rtttprocprefix.setStringValue("TestExecution");
		tprocgenctx.setStringValue("TestGeneration");
	}

	public RttMbtPreferencesProjectPage() {
		super(GRID);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);
		setDescription("RTT-MBT settings");

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
		rtttprocprefix = new StringFieldEditor("RttMbtRttTprocPrefix",
                                               "Test Execution Context:",
                                               getFieldEditorParent());
		tooltip = "The name of the directory inside the project that contains the generated test procedures.";
		rtttprocprefix.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		rtttprocprefix.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		rtttprocprefix.load();
		if (rtttprocprefix.getStringValue().compareTo("") == 0) {
			rtttprocprefix.loadDefault();
		}
		addField(rtttprocprefix);
		tprocgenctx = new StringFieldEditor("RttMbtTProcGenCtx",
                                            "Test Generation Context:",
                                            getFieldEditorParent());
		tooltip = "The name of the directory inside the project that contains the different test procedure generation context directories.";
		tprocgenctx.getTextControl(getFieldEditorParent()).setToolTipText(tooltip);
		tprocgenctx.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		tprocgenctx.load();
		if (tprocgenctx.getStringValue().compareTo("") == 0) {
			tprocgenctx.loadDefault();
		}
		addField(tprocgenctx);

        String[][] modes = {{"Symphony", "RTT_MBT_VSI_MODE_COMPASS"},
                            {"Verified", "RTT_MBT_VSI_MODE"},
                            {"Papyrus", "RTT_MBT_VSI_MODE_PAPYRUS"},
                            {"POVEtech:TA", "RTT_MBT_DAG_MODE"},
        	                {"SCADE", "RTT_MBT_SCADE_MODE"}};
		ComboFieldEditor ClientMode = new ComboFieldEditor("ClientMode", "Client Mode: ", modes, getFieldEditorParent());
		ClientMode.loadDefault();
		tooltip = "The mode that is used by the plugin when communication with the RTT-MBT server. Note: currently only Symphony is supported.";
		ClientMode.getLabelControl(getFieldEditorParent()).setToolTipText(tooltip);
		addField(ClientMode);
		BooleanFieldEditor verboseLogging = new BooleanFieldEditor("RttMbtLogVerbose",
                                                                   "Verbose Console Logging",
                                                                   getFieldEditorParent());
		addField(verboseLogging);
		BooleanFieldEditor extraFiles = new BooleanFieldEditor("RttMbtExtraFiles",
                                                               "Retrieve Extra Files",
                                                                getFieldEditorParent());
        addField(extraFiles);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}
