package eu.compassresearch.ide.collaboration.menu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;
import org.eclipse.ecf.sync.SerializationException;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.management.CollaborationManager;
import eu.compassresearch.ide.collaboration.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.treeview.model.CollaborationGroup;
import eu.compassresearch.ide.collaboration.treeview.model.TreeRoot;
import eu.compassresearch.ide.collaboration.treeview.model.User;
import eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView;

public class AddCollaboratorRosterMenuHandler extends AbstractRosterMenuHandler
{

	public AddCollaboratorRosterMenuHandler(IRosterEntry entry)
	{
		super(entry);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
//		ISelection selection = HandlerUtil.getCurrentSelection(event);
//
//		IWorkbench wb = PlatformUI.getWorkbench();
//		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
//		ISelectionService ss = wbw.getSelectionService();
//		selection = ss.getSelection();
		
		IRosterEntry rosterEntry = getRosterEntry();
		if (rosterEntry != null) {
			
			IRoster roster = rosterEntry.getRoster();
			final IContainer container = (IContainer) roster.getPresenceContainerAdapter().getAdapter(IContainer.class);
			if (container.getConnectedID() == null)
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED);
			
			CollaborationManager collabMgm = Activator.getDefault().getCollaborationManager(container.getID()); 
			if (collabMgm == null)
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NO_COLLAB_CHANNEL);
		
			IUser self = roster.getUser();	
			IUser receiver = rosterEntry.getUser();
			
			CollaborationRequestDialog collabReqDia = new CollaborationRequestDialog(receiver.getName(), null); 
			collabReqDia.create();
			if(collabReqDia.open() == Window.OK){
				
				CollaborationRequest msg = new CollaborationRequest(self, receiver,collabReqDia.getTitle(),collabReqDia.getDescription());
				try
				{
					collabMgm.sendMessage(receiver.getID(), msg.serialize());
				} catch (SerializationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ECFException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				final IViewPart view = page.findView("eu.compassresearch.ide.collaboration.treeview.ui.CollaborationView");
				final CollaborationView collabview = (CollaborationView) view;
				
				TreeRoot root = collabview.getRoot();
				
				CollaborationGroup collabGrp = (CollaborationGroup) root.getCollaboratorGroups().get(0);
				User usr = new User(receiver.getName(), "(Request sent, pending response.)");
				collabGrp.addCollaborator(usr);
				
				collabMgm.setProject(collabReqDia.getProject());
			}
		}
		return null;
	}
}
