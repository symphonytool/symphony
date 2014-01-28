package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.CollaborationGroupUpdateMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;

public class CollaborationGroupUpdateMessageHandler extends
		BaseMessageHandler<CollaborationGroupUpdateMessage>
{

	public CollaborationGroupUpdateMessageHandler()
	{
		super(CollaborationGroupUpdateMessage.class);
	}

	@Override
	public void process(final CollaborationGroupUpdateMessage msg)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
				modelMgm.updateCollaborationGroup(msg.getCollaborationGroupMembers(), msg.getProjectID());
			}
		});
	}
}
