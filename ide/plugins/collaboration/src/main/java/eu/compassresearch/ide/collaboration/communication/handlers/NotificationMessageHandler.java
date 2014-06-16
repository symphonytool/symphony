package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.communication.messages.NotificationMessage;
import eu.compassresearch.ide.collaboration.communication.messages.NotificationMessage.NotificationType;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class NotificationMessageHandler extends
		BaseMessageHandler<NotificationMessage>
{

	public NotificationMessageHandler()
	{
		super(NotificationMessage.class);
	}

	@Override
	public void process(final NotificationMessage msg)
	{
		final String senderName = msg.getSenderID().getName();
		final String message = "Message received from: " + senderName + ". \n Message: " +  msg.getMessage();
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				if(msg.getNotificationType() == NotificationType.ERROR) {
					Notification.logError(message, null);
				}
				
				CollaborationDialogs.getInstance().displayNotificationPopup(senderName, message);
			}
		});
	}
}
