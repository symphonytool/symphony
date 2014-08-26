package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.NewConfigurationMessage;
import eu.compassresearch.ide.collaboration.communication.messages.NotificationMessage;
import eu.compassresearch.ide.collaboration.communication.messages.NotificationMessage.NotificationType;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.Configurations;
import eu.compassresearch.ide.collaboration.files.FileDTO;
import eu.compassresearch.ide.collaboration.files.FileHandler;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class NewConfigurationMessageHandler extends
		BaseMessageHandler<NewConfigurationMessage>
{
	public NewConfigurationMessageHandler()
	{
		super(NewConfigurationMessage.class);
	}

	@Override
	public void process(final NewConfigurationMessage msg)
	{
		final String senderName = msg.getSenderID().getName();

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();
		ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		
		final CollaborationProject collaborationProject = dataModelManager.getCollaborationProjectFromID(msg.getProjectID());
		//if collaboration project not found, error handle
		if (collaborationProject == null)
		{
			String message = Notification.Collab_ERROR_NO_SUCH_COLLAB__PROJ_ID  + " " + senderName;
			Notification.logError(message, null);

			//send reply
			NotificationMessage notificationMsg = new NotificationMessage(connectionManager.getConnectedUser(), msg.getProjectID(), NotificationType.ERROR, message);
			connectionManager.sendTo(msg.getSenderID(), notificationMsg);
			
			return;
		}

		// TODO check that parent config is the same as msg.getParentConfigurationUniqueID()
		try
		{
			FileHandler.saveFilesToCollaborationDir(msg.getFileDTOs(), collaborationProject);

		} catch (CoreException e)
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
			e.printStackTrace();
		}

		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				Configurations configurations = collaborationProject.getConfigurations();
			
				Configuration newestConfiguration = configurations.getNewestConfiguration();
				Configuration receivedConfiguration = new Configuration(msg.getConfigurationUniqueID(), msg.getTimeStamp(), msg.getSignedBy(), msg.getReceiverList(), newestConfiguration, configurations);
				configurations.addConfiguration(receivedConfiguration);
				for (FileDTO fs : msg.getFileDTOs())
				{
					receivedConfiguration.addNewFile(fs.getFileName(), fs.getFileHash(), fs.getTimestamp(), fs.getFilePath(), fs.getVisibilityList());
				}
				
				CollaborationDialogs.getInstance().displayNotificationPopup(senderName, Notification.Collab_Dialog_SEND_NEW_CONFIG);
			}
		});

		// TODO send received response
		// FileStatusMessage statusMsg = new FileStatusMessage(fileMsg.getReceiverID(), fileMsg.getSenderID(), filename,
		// NegotiationStatus.RECEIVED, time);
	}
}
