package eu.compassresearch.ide.collaboration.notifications;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.osgi.util.NLS;

import eu.compassresearch.ide.collaboration.Activator;

public class Notification extends NLS {
	private static final String BUNDLE_NAME = "eu.compassresearch.ide.collaboration.notifications.messages";

	
	public static String CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED;
	public static String CollabRosterMenuContributionItem_MAIN_MENU;
	public static String CollabRosterMenuContributionItem_NO_OTHER_COLLABORATORS;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Notification.class);
	}
	
	public static void showErrorMessage(String errorMessage) {
		ErrorDialog.openError(null, "Error", errorMessage, new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMessage, null));
	}
	
}
