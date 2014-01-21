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
import org.eclipse.ecf.presence.IPresence.Mode;
import org.eclipse.ecf.presence.IPresence.Type;
import org.eclipse.ecf.presence.IPresenceListener;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.roster.IRosterGroup;
import org.eclipse.ecf.presence.roster.IRosterItem;
import org.eclipse.ecf.sync.SerializationException;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationRequestHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.CollaborationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.ConfigurationStatusMessageHandler;
import eu.compassresearch.ide.collaboration.communication.handlers.NewConfigurationMessageHandler;
import eu.compassresearch.ide.collaboration.communication.messages.BaseMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Configuration;
import eu.compassresearch.ide.collaboration.datamodel.User;

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
		synchronized (connectedUser)
		{

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

	public void send(BaseMessage messageToSend, CollaborationProject project)
			throws SerializationException
	{
		synchronized (availableCollaboratorsLock)
		{
			Map<String, ID> receivers = getAvailableCollaborators();

			byte[] serializedData = messageToSend.serialize();

			CollaborationGroup collaboratorGroup = project.getCollaboratorGroup();
			List<User> collaborators = collaboratorGroup.getCollaborators();

			for (User collaborator : collaborators)
			{
				ID collaboratorId = receivers.get(collaborator.getName());
				sendTo(collaboratorId, serializedData);
			}
		}
	}

	public void sendTo(ID receiverID, BaseMessage messageToSend)
			throws SerializationException
	{
		if (messageToSend != null)
		{
			sendTo(receiverID, messageToSend.serialize());
		}
	}

	public void sendTo(ID receiverID, byte[] serializedData)
	{
		if (receiverID != null)
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
		if (collabMgm != null)
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

			for (ID id : usersFromRoster)
			{
				availableCollaborators.put(id.getName(), id);
			}
		}

		return availableCollaborators;
	}

	private List<ID> getUsersFromRoster()
	{

		if (collaboratorRoster == null)
			return null;

		// /get buddies
		Collection items = Collections.unmodifiableCollection(collaboratorRoster.getItems());

		List<ID> newUsers = new ArrayList<ID>();

		synchronized (items)
		{
			for (Iterator it = items.iterator(); it.hasNext();)
			{
				IRosterItem item = (IRosterItem) it.next();
				addRosterEntry(item, newUsers);
			}
		}

		return newUsers;
	}

	// cycle through all roster items recursively
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
