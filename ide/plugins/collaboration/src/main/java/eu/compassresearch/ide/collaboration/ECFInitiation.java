package eu.compassresearch.ide.collaboration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.IContainerListener;
import org.eclipse.ecf.core.IContainerManager;
import org.eclipse.ecf.core.IContainerManagerListener;
import org.eclipse.ecf.core.events.IContainerConnectedEvent;
import org.eclipse.ecf.core.events.IContainerDisconnectedEvent;
import org.eclipse.ecf.core.events.IContainerDisposeEvent;
import org.eclipse.ecf.core.events.IContainerEjectedEvent;
import org.eclipse.ecf.core.events.IContainerEvent;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.start.IECFStart;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.datashare.IChannelContainerAdapter;
import org.eclipse.ecf.presence.IPresenceContainerAdapter;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterManager;

import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class ECFInitiation implements IECFStart
{
	@Override
	public IStatus run(IProgressMonitor arg0)
	{
		final IContainerManager containerManager = Activator.getDefault().getContainerManager();

		if (containerManager == null)
			return new Status(IStatus.WARNING, Activator.PLUGIN_ID, IStatus.WARNING, Notification.ECFInit_ERROR_NO_CONTAINER_MANAGER_AVAILABLE, null);
		containerManager.addListener(containerManagerListener);
		return Status.OK_STATUS;
	}

	IContainerManagerListener containerManagerListener = new IContainerManagerListener()
	{

		public void containerAdded(IContainer container)
		{
			IChannelContainerAdapter chanContAdpt = (IChannelContainerAdapter) container.getAdapter(IChannelContainerAdapter.class);
			if (chanContAdpt == null)
				return;
			container.addListener(containerListener);
		}

		public void containerRemoved(IContainer container)
		{
			container.removeListener(containerListener);
		}
	};

	IContainerListener containerListener = new IContainerListener()
	{

		@Override
		public void handleEvent(IContainerEvent event)
		{
			final IContainerManager containerManager = Activator.getDefault().getContainerManager();
			if (containerManager == null)
				return;

			IContainer container = containerManager.getContainer(event.getLocalContainerID());
			if (container == null)
				return;

			if (event instanceof IContainerConnectedEvent
					|| event instanceof IContainerDisconnectedEvent)
			{
				// connected
				IChannelContainerAdapter cca = (IChannelContainerAdapter) container.getAdapter(IChannelContainerAdapter.class);
				if (cca == null)
					return;

				ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
				ID containerID = container.getID();
				if (event instanceof IContainerConnectedEvent)
				{
					try
					{

						connectionManager.addMessageProcessor(containerID, cca);

						IPresenceContainerAdapter containerAdapter = (IPresenceContainerAdapter) container.getAdapter(IPresenceContainerAdapter.class);
						if (containerAdapter != null)
						{
							IRosterManager rosterManager = containerAdapter.getRosterManager();

							// get connected user
							IRoster roster = rosterManager.getRoster();
							connectionManager.setConnectedUser(roster.getUser().getID());
							connectionManager.setRoster(roster);

							// get notified about future changes in connections
							rosterManager.addPresenceListener(connectionManager);
						}
					} catch (ECFException e)
					{
						Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, IStatus.WARNING, Notification.ECFInit_ERROR_COLLABORATION_NOT_CREATED
								+ container.getID(), null));
					}
				} else if (event instanceof IContainerDisconnectedEvent
						|| event instanceof IContainerEjectedEvent)
				{
					connectionManager.removeMessageProcessor(containerID);
				}
			} else if (event instanceof IContainerDisposeEvent)
			{
				containerManager.removeListener(containerManagerListener);
				container.removeListener(containerListener);
			}
		}
	};
}
