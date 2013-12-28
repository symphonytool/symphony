package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.MessageProcessor;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationStatusMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelRoot;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.User;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

public class CollaborationStatusMessageHandler extends BaseMessageHandler<CollaborationStatusMessage>
{

	public CollaborationStatusMessageHandler(MessageProcessor processor)
	{
		super(CollaborationStatusMessage.class, processor);
	}

	@Override
	public void process(final CollaborationStatusMessage msg)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				final CollaborationStatusMessage collabRequest = (CollaborationStatusMessage) msg;
				final CollaborationView collabview = CollaborationDialogs.getCollaborationView();
				
				CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
				CollaborationDataModelRoot root = modelMgm.getDataModel();
				collabview.getSelectedEntry();
				
				CollaborationGroup collabGrp = (CollaborationGroup) root.getCollaborationProjects().get(0).getCollaboratorGroup();
				
				String userName = collabRequest.getSenderID().getName();	
				User usr = collabGrp.getUser(userName);
				
				String notification;
				
				if(collabRequest.isJoining()){
					usr.setPostfix("");		
					notification = "Joined Collaboration";
				} else{
					usr.setPostfix("(Declined collaboration)");		
					notification = "Declined collaboration"; 
				}
				
				CollaborationDialogs.getInstance().displayNotificationPopup(usr.getName(), notification);
			}
		});		
	}
}
