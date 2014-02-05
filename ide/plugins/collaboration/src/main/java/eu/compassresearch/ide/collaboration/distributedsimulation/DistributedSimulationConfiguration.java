package eu.compassresearch.ide.collaboration.distributedsimulation;

public class DistributedSimulationConfiguration
{
	private String process;
	private String selectedCollaborator;

	public DistributedSimulationConfiguration(String process,
			String selectedCollaborator)
	{
		this.process = process;
		this.selectedCollaborator = selectedCollaborator;
	}
	
	public String getProcess()
	{
		return process;
	}

	public String getCollaborator()
	{
		return selectedCollaborator;
	}
}
