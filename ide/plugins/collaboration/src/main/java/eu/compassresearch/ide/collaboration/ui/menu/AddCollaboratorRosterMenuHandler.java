package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.window.Window;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.Model;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class AddCollaboratorRosterMenuHandler extends AbstractRosterMenuHandler
{

	public AddCollaboratorRosterMenuHandler(IRosterEntry entry)
	{
		super(entry);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IRosterEntry rosterEntry = getRosterEntry();

		// build dynamic menu
		if (rosterEntry != null)
		{

			IRoster roster = rosterEntry.getRoster();

			final IContainer container = (IContainer) roster.getPresenceContainerAdapter().getAdapter(IContainer.class);
			if (container.getConnectedID() == null)
			{
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED);
				return null;
			}

			// TODO change error handling
			MessageProcessor messageProcessor = Activator.getDefault().getConnectionManager().getMessageProcessor(container.getID());
			if (messageProcessor == null)
			{
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NO_COLLAB_CHANNEL);
				return null;
			}
			// get users
			IUser receiver = rosterEntry.getUser();
			String userName = receiver.getID().getName();

			// get selected project
			Model selected = CollaborationDialogs.getCollaborationView().getSelectedEntry();
			CollaborationGroup group = (CollaborationGroup) selected;
			CollaborationProject project = (CollaborationProject) group.getParent();

			if (group.hasCollaborator(userName))
			{
				CollaborationDialogs.displayErrorDialog("Error adding collaborator.", Notification.Collab_Dialog_COLLAB_REQUEST_ALREADY_ADDED);
				return null;
			}

			// show dialog with message being send to collaborator
			CollaborationRequestDialog collabReqDia = CollaborationDialogs.getInstance().getCollaborationRequestDialog(project.getTitle(), userName);

			if (collabReqDia.open() == Window.OK)
			{

				CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();
				try
				{
					dataModelManager.addNewCollaborator(receiver, group, collabReqDia.getDescription());
				} catch (SerializationException e)
				{
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
				}
			}
		}
		return null;
	}
}
