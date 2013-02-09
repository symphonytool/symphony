package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import eu.compassresearch.ide.cml.rttplugin.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class RttMbtPreferencesPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	@SuppressWarnings("deprecation")
	public RttMbtPreferencesPage() {
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
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		StringFieldEditor server = new StringFieldEditor("RttMbtServer",
														 "Server:",
														 getFieldEditorParent());
		addField(server);
		IntegerFieldEditor port = new IntegerFieldEditor("RttMbtServerPort",
												    	 "Port:",
												    	 getFieldEditorParent());
		addField(port);
		StringFieldEditor user = new StringFieldEditor("RttMbtUserName",
													   "Name:",
													   getFieldEditorParent());
		addField(user);
		StringFieldEditor id = new StringFieldEditor("RttMbtUserId",
													 "User-ID:",
													 getFieldEditorParent());
		addField(id);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}