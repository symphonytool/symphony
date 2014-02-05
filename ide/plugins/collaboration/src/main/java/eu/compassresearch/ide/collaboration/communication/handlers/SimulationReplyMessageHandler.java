package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationReplyMessage;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class SimulationReplyMessageHandler extends
		BaseMessageHandler<SimulationReplyMessage>
{
	public SimulationReplyMessageHandler()
	{
		super(SimulationReplyMessage.class);
	}

	@Override
	public void process(final SimulationReplyMessage msg)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				DistributedSimulationManager distributedSimulationManager = Activator.getDefault().getDistributedSimulationManager();
				distributedSimulationManager.receivedReply(msg.getSenderID().getName(), msg.getProjectID(), msg.getProcess(), msg.isAccepted());
			}
		});
	}
}
