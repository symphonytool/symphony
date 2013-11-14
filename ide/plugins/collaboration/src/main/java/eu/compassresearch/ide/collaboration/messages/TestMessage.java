package eu.compassresearch.ide.collaboration.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.sync.IModelChangeMessage;
import org.eclipse.ecf.sync.SerializationException;

import eu.compassresearch.ide.collaboration.notifications.Notification;

public class TestMessage extends BaseMessage
{
	private static final long serialVersionUID = 4816943224781454232L;
	private final String senderUsrname;
	
	public TestMessage(IUser sender, IUser receiver, String senderUsername)
	{
		super(sender,receiver);
		senderUsrname = senderUsername;
	}
	
	public String getData() {
		return "Data Data";
	}
	
	public String getFromUsername()
	{
		return senderUsrname;
	}
	
	public static IModelChangeMessage deserialize(byte[] bytes) throws SerializationException {
		try {
			final ByteArrayInputStream bins = new ByteArrayInputStream(bytes);
			final ObjectInputStream oins = new ObjectInputStream(bins);
			return (IModelChangeMessage) oins.readObject();
		} catch (final Exception e) {
			throw new SerializationException(Notification.Collaboration_ERROR_DESERIALIZATION_FAILED, e);
		}
	}
	

	@Override
	public byte[] serialize() throws SerializationException
	{
		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			return bos.toByteArray();
		} catch (final Exception e) {
			throw new SerializationException(Notification.Collaboration_ERROR_SERIALIZATION_FAILED, e);
		}
	}
	
	@Override
	public Object getAdapter(Class adapter)
	{
		return null;
	}
}
