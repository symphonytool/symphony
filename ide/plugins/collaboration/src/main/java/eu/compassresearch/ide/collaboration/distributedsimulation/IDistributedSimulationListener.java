package eu.compassresearch.ide.collaboration.distributedsimulation;

public interface IDistributedSimulationListener
{
	public void onRequestUpdated(String process, boolean accepted);
	public void onSimluationStarting();
}
