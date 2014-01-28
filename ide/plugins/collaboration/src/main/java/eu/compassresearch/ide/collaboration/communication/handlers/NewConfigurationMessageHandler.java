package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.NewConfigurationMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.Configurations;
import eu.compassresearch.ide.collaboration.files.FileHandler;
import eu.compassresearch.ide.collaboration.files.FileDTO;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class NewConfigurationMessageHandler extends
		BaseMessageHandler<NewConfigurationMessage>
{
	public NewConfigurationMessageHandler(MessageProcessor processor)
	{
		super(NewConfigurationMessage.class, processor);
	}

	@Override
	public void process(final NewConfigurationMessage msg)
	{
		final String senderName = msg.getSenderID().getName();

		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		// TODO move to collaboration manager
		final CollaborationProject collaborationProject = dataModelManager.getCollaborationProjectFromID(msg.getProjectID());
		if (collaborationProject == null)
		{
			Notification.logError(Notification.Collab_ERROR_NO_SUCH_COLLAB__PROJ_ID
					+ " " + senderName, null);
			// TODO reply with error msg of no such collaboration project id
			//TODO gentle notification of error
			
			return;
		}

		// TODO check that parent config is the same as msg.getParentConfigurationUniqueID()
		try
		{
			FileHandler.saveFilesToCollaborationDir(msg.getFileSets(), collaborationProject);

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
				Configuration receivedConfiguration = new Configuration(msg.getConfigurationUniqueID(), msg.getTimeStamp(), msg.getSignedBy(), newestConfiguration, configurations);
				configurations.addConfiguration(receivedConfiguration);
				for (FileDTO fs : msg.getFileSets())
				{
					receivedConfiguration.addNewFile(fs.getFileName(), fs.getFileHash(), fs.getTimestamp(), fs.getFilePath());
				}
				
				CollaborationDialogs.getInstance().displayNotificationPopup(senderName, Notification.Collab_Dialog_SEND_NEW_CONFIG);
			}
		});

		// TODO send received response
		// FileStatusMessage statusMsg = new FileStatusMessage(fileMsg.getReceiverID(), fileMsg.getSenderID(), filename,
		// NegotiationStatus.RECEIVED, time);

	}
}
