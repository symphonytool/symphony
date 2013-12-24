package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.user.IUser;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.presence.roster.IRoster;
import org.eclipse.ecf.presence.roster.IRosterEntry;
import org.eclipse.ecf.presence.ui.menu.AbstractRosterMenuHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.NewFileMessage;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class CollabMenuRosterMenuHandler extends AbstractRosterMenuHandler
{

	public CollabMenuRosterMenuHandler(IRosterEntry entry)
	{
		super(entry);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = wb.getActiveWorkbenchWindow();
		ISelectionService ss = wbw.getSelectionService();
		selection = ss.getSelection();
		
		IRosterEntry rosterEntry = getRosterEntry();
		if (rosterEntry != null) {
			
			IRoster roster = rosterEntry.getRoster();
			final IContainer container = (IContainer) roster.getPresenceContainerAdapter().getAdapter(IContainer.class);
			if (container.getConnectedID() == null)
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NOT_CONNECTED);
			
			MessageProcessor collabMgm = Activator.getDefault().getCollaborationManager(container.getID()); 
			if (collabMgm == null)
				Notification.showErrorMessage(Notification.CollabMenuRosterMenuHandler_ERROR_NO_COLLAB_CHANNEL);
			
			//find file
			String filename = null;
			String fileContents = null; 
			if (selection instanceof IStructuredSelection) {
			    IStructuredSelection ssel = (IStructuredSelection) selection;
			    Object obj = ssel.getFirstElement();
			    IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
	
			    if (file == null) {
			        if (obj instanceof IAdaptable) {
			            file = (IFile) ((IAdaptable) obj).getAdapter(IFile.class);
			        }
			    }
			    
			    if (file != null) {
						try
						{
							fileContents = CollaborationPluginUtils.convertStreamToString(file.getContents());
						} catch (CoreException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						filename = file.getName();
			    }
			}
			
			IUser self = roster.getUser();	
			IUser receiver = rosterEntry.getUser();
			NewFileMessage msg = new NewFileMessage(self, receiver, filename, fileContents);
			try
			{
				collabMgm.sendMessage(receiver.getID(), msg.serialize());
	
			
//			try
//			{
//			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(collabMgm.getProject());
//			IFolder folder = project.getFolder("Collaboration");
//			if (!folder.exists())
//			{
//					folder.create(IResource.NONE, true, null);
//					collabMgm.setProjectFolder(folder);	
//			}
//	
//			byte[] bytes = fileContents.getBytes();
//			InputStream source = new ByteArrayInputStream(bytes);
//			
//			IFile file = folder.getFile(filename);
//			
//			if (!file.exists()){
//				file.create(source, IResource.NONE, null);
//			} else {
//				file = collabMgm.nextFilename(filename);
//				file.create(source, IResource.NONE, null);
//			}
//			
//			} catch (CoreException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			} catch (SerializationException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (ECFException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
}
