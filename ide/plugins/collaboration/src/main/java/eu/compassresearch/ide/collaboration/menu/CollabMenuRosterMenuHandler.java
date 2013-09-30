package eu.compassresearch.ide.collaboration.menu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;

import eu.compassresearch.ide.collaboration.notifications.Notification;

public class CollabMenuRosterMenuHandler extends AbstractRosterMenuHandler
{

	public CollabMenuRosterMenuHandler(IRosterEntry entry)
	{
		super(entry);
	}

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		IRosterEntry rosterEntry = getRosterEntry();
		if (rosterEntry != null) {
			IRoster roster = rosterEntry.getRoster();
			final IContainer container = (IContainer) roster.getPresenceContainerAdapter().getAdapter(IContainer.class);
			if (container.getConnectedID() == null)
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED);
			
//			final DocShare sender = Activator.getDefault().getDocShare(container.getID());
//			if (sender == null)
//				showErrorMessage(Messages.DocShareRosterMenuHandler_ERROR_NO_SENDER);
//			if (sender.isSharing())
//				showErrorMessage(Messages.DocShareRosterMenuHandler_ERROR_EDITOR_ALREADY_SHARING);
//			final ITextEditor textEditor = getTextEditor();
//			if (textEditor == null)
//				showErrorMessage(Messages.DocShareRosterMenuHandler_EXCEPTION_EDITOR_NOT_TEXT);
//			final String inputName = getInputName(textEditor);
//			if (inputName == null)
//				showErrorMessage(Messages.DocShareRosterMenuHandler_NO_FILENAME_WITH_CONTENT);
//			final IUser user = roster.getUser();
//			sender.startShare(user.getID(), user.getName(), rosterEntry.getUser().getID(), inputName, textEditor);
		}

		Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED);
		
		return null;
	}

}
