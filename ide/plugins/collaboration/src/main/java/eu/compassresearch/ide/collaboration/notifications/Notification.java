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
	public static String CollabRosterMenuContributionItem_ADD_COLLABORATOR_MENU;
	public static String CollabRosterMenuContributionItem_NO_OTHER_COLLABORATORS;
	public static String Collaboration_ERROR_HANDLE_MSG_EXCEPTION_CAUGHT;
	public static String Collaboration_ERROR_DESERIALIZATION_FAILED;
	public static String Collaboration_ERROR_SERIALIZATION_FAILED;
	public static String Collaboration_ERROR_UNKNOWN_MESSAGE_TYPE;
	public static String CollabMenuRosterMenuHandler_ERROR_NO_COLLAB_CHANNEL;
	public static String ECFInit_ERROR_NO_CONTAINER_MANAGER_AVAILABLE;
	public static String ECFInit_ERROR_COLLABORATION_NOT_CREATED;
	public static String ECF_ERROR_COULD_NOT_SEND;
	public static String Collab_Dialog_NEW_COLLAB_TITLE;
	public static String Collab_Dialog_NEW_COLLAB_MSG;
	public static String Collab_Dialog_NEW_COLLAB_ATTACH;
	public static String Collab_Dialog_NEW_COLLAB_ERROR_TITLE_EXISTS;
	public static String Collab_Dialog_NEW_COLLAB_ERROR_COLLABORATION_ALREADY_ATTACHED;
	public static String Collab_Dialog_BTN_OK;
	public static String Collab_Dialog_BTN_Send;
	public static String Collab_Dialog_BTN_CANCEL;
	public static String Collab_File_ERROR_NO_SUCH_PROJECT;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Notification.class);
	}
	
	public static void showErrorMessage(String errorMessage) {
		ErrorDialog.openError(null, "Error", errorMessage, new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMessage, null));
	}	
	
	public static void logError(String exceptionString, Throwable e){
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, exceptionString, e));
	}
}
