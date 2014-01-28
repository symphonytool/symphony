package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationRequestedDialog;

public class CollaborationRequestHandler extends BaseMessageHandler<CollaborationRequest>
{		
	public CollaborationRequestHandler(
			MessageProcessor processor)
	{
		super(CollaborationRequest.class, processor);
	}

	@Override
	public void process(final CollaborationRequest msg)
	{
		final ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		final String collabProjectId = msg.getProjectID();
		final ID sender = connectionManager.getConnectedUser();
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				try
				{
					CollaborationRequestedDialog collabRequestedDialog = new CollaborationRequestedDialog(sender.getName(), msg.getTitle(), msg.getMessage(), null);
					collabRequestedDialog.create();
					boolean join = collabRequestedDialog.open() == Window.OK; 
					
					if(join) {
						CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
						modelMgm.addReceivedCollaborationProject(collabRequestedDialog.getProject(), msg.getTitle(), msg.getMessage(), collabProjectId);
					}
					
					//send reply
					CollaborationStatusMessage statusMsg = new CollaborationStatusMessage(sender, msg.getProjectID(), join);
					connectionManager.sendTo(msg.getSenderID(), statusMsg);
					
				} catch (ECFException e)
				{
					ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
				}
			}	
		});	
	}
}
