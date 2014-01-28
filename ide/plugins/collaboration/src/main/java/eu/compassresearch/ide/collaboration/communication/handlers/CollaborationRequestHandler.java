package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationRequestedDialog;

public class CollaborationRequestHandler extends BaseMessageHandler<CollaborationRequest>
{		
	public CollaborationRequestHandler()
	{
		super(CollaborationRequest.class);
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
			}	
		});	
	}
}
