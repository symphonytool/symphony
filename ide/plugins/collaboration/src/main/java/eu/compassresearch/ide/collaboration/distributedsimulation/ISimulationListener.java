package eu.compassresearch.ide.collaboration.distributedsimulation;

public interface ISimulationListener
{
	public void onRequestUpdate();

	public void onMsgUpdated(String process, boolean accepted);
}
