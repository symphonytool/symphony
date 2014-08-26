package eu.compassresearch.ide.collaboration.communication.handlers;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.RelayMessage;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class RelayMessageHandler extends BaseMessageHandler<RelayMessage>
{
	
	public RelayMessageHandler()
	{
		super(RelayMessage.class);
	}

	@Override
	public void process(RelayMessage msg)
	{
		DistributedSimulationManager distributedSimulationManager = Activator.getDefault().getDistributedSimulationManager();
		
		
		switch (msg.getRelayFrom())
		{
			case Server:
				distributedSimulationManager.passRelayMessageToClient(msg.getInputData());
				break;

			case Client:
				distributedSimulationManager.passRelayMessageToCoordinatorServer(msg.getSenderID(), msg.getInputData());
				break;
				
			default:
				break;
		}
	}
}
