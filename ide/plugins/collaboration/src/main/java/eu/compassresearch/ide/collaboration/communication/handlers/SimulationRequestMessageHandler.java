package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationRequestMessage;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class SimulationRequestMessageHandler extends BaseMessageHandler<SimulationRequestMessage>
{
	public SimulationRequestMessageHandler()
	{
		super(SimulationRequestMessage.class);
	}

	@Override
	public void process(final SimulationRequestMessage msg)
	{
		final CollaborationDataModelManager modelMgm = Activator.getDefault().getDataModelManager();
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				String collabProjectId = msg.getProjectID();
				CollaborationProject collaborationProject = modelMgm.getCollaborationProjectFromID(collabProjectId);
				
				String sender = msg.getSenderID().getName();
				String remoteHost = msg.getHost();
				String process = msg.getProcess();
				DistributedSimulationManager distributedSimulationManager = Activator.getDefault().getDistributedSimulationManager();
				distributedSimulationManager.newSimulationRequest(sender, collaborationProject, process, remoteHost);
			}
		});
		
	}
}
