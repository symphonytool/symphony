package eu.compassresearch.ide.collaboration.communication.handlers;

import org.eclipse.swt.widgets.Display;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.communication.messages.SimulationStartMessage;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class SimulationStartMessageHandler extends BaseMessageHandler<SimulationStartMessage>
{
	public SimulationStartMessageHandler()
	{
		super(SimulationStartMessage.class);
	}

	@Override
	public void process(final SimulationStartMessage msg)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				DistributedSimulationManager distributedSimulationManager = Activator.getDefault().getDistributedSimulationManager();
				distributedSimulationManager.simulationStarting(msg.getSenderID().getName(), msg.getProjectID(), msg.getProcess());
			}
		});
	}
}
