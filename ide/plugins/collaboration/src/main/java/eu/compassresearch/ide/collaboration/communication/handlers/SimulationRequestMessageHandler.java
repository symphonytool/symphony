package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.ConnectionManager;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationRequestMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.menu.DistributedSimulationRequestDialog;

public class SimulationRequestMessageHandler extends BaseMessageHandler<SimulationRequestMessage>
{
	public SimulationRequestMessageHandler()
	{
		super(SimulationRequestMessage.class);
	}

	@Override
	public void process(final SimulationRequestMessage msg)
	{
		final ConnectionManager connectionManager = Activator.getDefault().getConnectionManager();
		final String collabProjectId = msg.getProjectID();
		final ID currentUser = connectionManager.getConnectedUser();
		final CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
		final String process = msg.getProcess();
		final String host = msg.getHost();
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				CollaborationProject collaborationProject = modelMgm.getCollaborationProjectFromID(collabProjectId);
				
				String sender = msg.getSenderID().getName();
				
				DistributedSimulationManager distributedSimulationManager = Activator.getDefault().getDistributedSimulationManager();
				distributedSimulationManager.newSimulationRequest(sender, collaborationProject, process);
			}
		});
		
	}
}
