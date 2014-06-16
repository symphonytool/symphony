package eu.compassresearch.ide.collaboration.communication.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.sync.SerializationException;

import eu.compassresearch.ide.collaboration.notifications.Notification;

public class BaseMessage implements Serializable {

	private static final long serialVersionUID = -70529594981572196L;

	private final ID senderID;
	private final String projectID;
	
	public BaseMessage(ID sender, String collabProjectID)
	{
		senderID = sender;
		projectID = collabProjectID;
	}
	
	public static BaseMessage deserialize(byte[] bytes) throws SerializationException {
		try {
			final ByteArrayInputStream bins = new ByteArrayInputStream(bytes);
			final ObjectInputStream oins = new ObjectInputStream(bins);
			return (BaseMessage) oins.readObject();
		} catch (final Exception e) {
			throw new SerializationException(Notification.Collaboration_ERROR_SERIALIZATION_FAILED, e);
		}
	}

	public byte[] serialize() throws SerializationException {
		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			return bos.toByteArray();
		} catch (final Exception e) {
			throw new SerializationException(Notification.Collaboration_ERROR_DESERIALIZATION_FAILED, e);
		}
	}

	public ID getSenderID()
	{
		return senderID;
	}
	
	public String getProjectID()
	{
		return projectID;
	}
}
