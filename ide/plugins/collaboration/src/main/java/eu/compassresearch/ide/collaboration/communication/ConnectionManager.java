package eu.compassresearch.ide.collaboration.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ecf.presence.IPresence;
import org.eclipse.ecf.presence.IPresence.Type;
import org.eclipse.ecf.presence.IPresenceListener;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.roster.IRosterGroup;
import org.eclipse.ecf.presence.roster.IRosterItem;
import org.eclipse.ecf.sync.SerializationException;

import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationGroupUpdateMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationRequestHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.ConfigurationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.NewConfigurationMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.NotificationMessageHandler;
import eu.compassresearch.ide.collaboration.communication.messages.BaseMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class ConnectionManager implements IPresenceListener
{
	private final Object availableCollaboratorsLock = new Object();
	private Map<String, ID> availableCollaborators;
	private static final Hashtable<ID, MessageProcessor> messageProcessors = new Hashtable<ID, MessageProcessor>();
	private MessageProcessor messageProcessor;
	private ID connectedUser;
	private IRoster collaboratorRoster;

	public ConnectionManager()
	{
	}

	@Override
	public void handlePresence(ID fromID, IPresence presence)
	{
		Type type = presence.getType();

		if (type == Type.AVAILABLE)
		{
			addUser(fromID);
		} else
		{
			removeUser(fromID);
		}
	}

	private void removeUser(ID user)
	{
		synchronized (availableCollaboratorsLock)
		{
			if (availableCollaborators != null
					|| availableCollaborators.containsKey(user))
			{
				availableCollaborators.remove(user);
			}
		}
	}

	private boolean addUser(ID user)
	{
		synchronized (availableCollaboratorsLock)
		{
			if (availableCollaborators == null
					|| availableCollaborators.containsKey(user))
			{
				return false;
			} else
			{
				availableCollaborators.put(user.getName(), user);
				return true;
			}
		}
	}

	// Send to all users, that have accepted to be part of the collaboration.
	public void send(BaseMessage messageToSend, CollaborationProject project)
	{

		CollaborationGroup collaboratorGroup = project.getCollaboratorGroup();
		List<User> collaborators = collaboratorGroup.getCollaborators();

		for (User collaborator : collaborators)
		{
			sendTo(collaborator, messageToSend);
		}
	}

	public void sendTo(User user, BaseMessage messageToSend)
	{
		if (user.hasJoinedGroup())
		{
			Map<String, ID> receivers = getAvailableCollaborators();
			ID collaboratorId;
			synchronized (availableCollaboratorsLock)
			{
				collaboratorId = receivers.get(user.getName());
			}

			sendTo(collaboratorId, messageToSend);
		}
	}

	// Send to a specific user.
	public void sendTo(ID receiverID, BaseMessage messageToSend)
	{
		if (messageToSend != null)
		{
			try
			{
				sendTo(receiverID, messageToSend.serialize());
			} catch (SerializationException e)
			{
				Notification.logError(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	// Send to a specific ID
	private void sendTo(ID receiverID, byte[] serializedData)
	{
		// there needs to be a receiver, and don't send to self.
		if (receiverID != null && receiverID != connectedUser)
		{
			messageProcessor.sendMessage(receiverID, serializedData);
		}
	}

	// currently only support XMPP, so only one messageprocessor can exist.
	public MessageProcessor getMessageProcessor()
	{
		return messageProcessor;
	}

	// currently only support XMPP, for future use to support more protocols.
	public MessageProcessor getMessageProcessor(ID containerID)
	{
		return messageProcessors.get(containerID);
	}

	public MessageProcessor addMessageProcessor(ID containerID,
			IChannelContainerAdapter channelAdapter) throws ECFException
	{
		messageProcessor = messageProcessors.get(containerID);
		if (messageProcessor == null)
		{
			messageProcessor = new MessageProcessor(channelAdapter);
			messageProcessors.put(containerID, messageProcessor);

			addMessageHandlers();
		}

		return messageProcessor;
	}

	public void removeMessageProcessor(ID containerID)
	{
		MessageProcessor collabMgm = messageProcessors.remove(containerID);
		if (collabMgm != null && !collabMgm.isDisposed())
		{
			collabMgm.dispose();
		}
	}

	private void addMessageHandlers()
	{
		messageProcessor.addMessageHandler(new CollaborationRequestHandler(messageProcessor));
		messageProcessor.addMessageHandler(new NewConfigurationMessageHandler(messageProcessor));
		messageProcessor.addMessageHandler(new ConfigurationStatusMessageHandler(messageProcessor));
		messageProcessor.addMessageHandler(new CollaborationStatusMessageHandler(messageProcessor));
		messageProcessor.addMessageHandler(new CollaborationGroupUpdateMessageHandler(messageProcessor));
		messageProcessor.addMessageHandler(new NotificationMessageHandler(messageProcessor));
	}

	public boolean isConnectionInitialized()
	{
		return messageProcessor != null;
	}

	public ID getConnectedUser()
	{
		return connectedUser;
	}

	public void setConnectedUser(ID connectedUser)
	{
		this.connectedUser = connectedUser;
	}

	public void setRoster(IRoster roster)
	{
		this.collaboratorRoster = roster;

	}

	Map<String, ID> getAvailableCollaborators()
	{
		// lazy load
		if (availableCollaborators == null || availableCollaborators.isEmpty())
		{
			List<ID> usersFromRoster = getUsersFromRoster();
			availableCollaborators = new HashMap<String, ID>();

			if (usersFromRoster != null)
			{
				for (ID id : usersFromRoster)
				{
					availableCollaborators.put(id.getName(), id);
				}
			}
		}

		return availableCollaborators;
	}

	private List<ID> getUsersFromRoster()
	{
		if (collaboratorRoster == null)
			return null;

		// /get buddies
		Collection<IRosterItem> items = Collections.unmodifiableCollection(getRoosterItems());

		List<ID> newUsers = new ArrayList<ID>();

		synchronized (items)
		{
			for (IRosterItem item : items)
			{
				addRosterEntry(item, newUsers);
			}
		}

		return newUsers;
	}

	@SuppressWarnings("unchecked")
	private Collection<IRosterItem> getRoosterItems()
	{
		return collaboratorRoster.getItems();
	}

	// cycle through all roster items recursively
	@SuppressWarnings("rawtypes")
	private void addRosterEntry(IRosterItem item, List<ID> newUsers)
	{
		if (item instanceof IRosterGroup)
		{
			IRosterGroup group = (IRosterGroup) item;
			for (Iterator it = group.getEntries().iterator(); it.hasNext();)
			{
				addRosterEntry((IRosterItem) it.next(), newUsers);
			}

		} else if (item instanceof IRosterEntry)
		{
			IRosterEntry entry = (IRosterEntry) item;
			IPresence presence = entry.getPresence();
			if (presence != null && presence.getType() == Type.AVAILABLE)
			{
				ID user = entry.getUser().getID();
				newUsers.add(user);
			}
		}
	}
}
