package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationRequest;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationRequestedDialog;

public class CollaborationRequestHandler extends BaseMessageHandler<CollaborationRequest>
{		
	public CollaborationRequestHandler(
			MessageProcessor processor)
	{
		super(CollaborationRequest.class, processor);
	}

	@SuppressWarnings("unused")
	@Override
	public void process(final CollaborationRequest msg)
	{
		final ID sender = Activator.getDefault().getConnectionManager().getConnectedUser();
		final String collabProjectId = msg.getProjectID();
		
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
						//TODO move to model manager
						CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
						CollaborationDataModelRoot root = modelMgm.getDataModel();
						
						root.addCollaborationProject(collabRequestedDialog.getProject(), msg.getTitle(), msg.getMessage(), collabProjectId);
						
						CollaborationGroup collabGrp = (CollaborationGroup) root.getCollaborationProjects().get(0).getCollaboratorGroup();
						collabGrp.addCollaborator(sender.getName(), true);	
					}
					
					//send reply
					CollaborationStatusMessage statusMsg = new CollaborationStatusMessage(sender, msg.getProjectID(), join);
					//TODO send via connection manager
					messageProcessor.sendMessage(msg.getSenderID(), statusMsg.serialize());
					
				} catch (ECFException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});	
	}
}
