package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class NotificationMessage extends BaseMessage
{

	private static final long serialVersionUID = 8137770064963147457L;
	private NotificationType notification;
	private String message;

	public enum NotificationType {INFORMATION,ERROR} 
	
	public NotificationMessage(ID sender, String collabProjectID, NotificationType notification, String message)
	{
		super(sender, collabProjectID);
		this.notification = notification;
		this.message = message;
	}

	public NotificationType getNotificationType()
	{
		return notification;
	}
	public String getMessage()
	{
		return message;
	}
}
