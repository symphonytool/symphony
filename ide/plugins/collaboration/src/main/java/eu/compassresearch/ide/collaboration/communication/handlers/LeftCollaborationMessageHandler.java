package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.LeftCollaborationMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class LeftCollaborationMessageHandler extends BaseMessageHandler<LeftCollaborationMessage>
{

	public LeftCollaborationMessageHandler()
	{
		super(LeftCollaborationMessage.class);
	}

	@Override
	public void process(final LeftCollaborationMessage msg)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
				String senderName = msg.getSenderID().getName();
				
				modelMgm.collaboratorLeft(senderName, msg.getProjectID());
				
				CollaborationDialogs.getInstance().displayNotificationPopup(senderName, "Decided to leave collaboration");
			}
		});
	}
}
