package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationRequestedDialog;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

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
		final String senderName = msg.getSenderID().getName();
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				try
				{
					CollaborationRequestedDialog collabRequestedDialog = new CollaborationRequestedDialog(senderName, msg.getTitle(), msg.getMessage(), null);
					collabRequestedDialog.create();
					boolean join = collabRequestedDialog.open() == Window.OK; 
					
					if(join) {
						CollaborationView collabview = CollaborationDialogs.getCollaborationView();
						
						CollaborationDataModelRoot root = collabview.getDataModel();
						
						CollaborationGroup collabGrp = (CollaborationGroup) root.getCollaborationProjects().get(0).getCollaboratorGroup();
						User usr = new User(senderName);
						collabGrp.addCollaborator(usr);
						
						//project = collabRequestedDialog.getProject();	
					}
					
					CollaborationStatusMessage statusMsg = new CollaborationStatusMessage(msg.getReceiverID(), msg.getSenderID(), join);
					messageProcessor.sendMessage(statusMsg.getReceiverID(), statusMsg.serialize());
					
				} catch (ECFException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});	
	}
}
