package eu.compassresearch.ide.collaboration.distributedsimulation;

public class DistributedSimulationConfiguration
{
	private String selectedProcess;
	private String selectedCollaborator;
	private boolean approved;

	public DistributedSimulationConfiguration(String process,
			String selectedCollaborator)
	{
		this.selectedProcess = process;
		this.selectedCollaborator = selectedCollaborator;
		approved = false;
	}
	
	public String getProcess()
	{
		return selectedProcess;
	}

	public String getCollaborator()
	{
		return selectedCollaborator;
	}
	
	public boolean isApproved(){
		return approved;
	}
	
	public void approve(String process, String collaborator){
		
		if(selectedProcess.equals(process) && selectedCollaborator.equals(collaborator)){
			approved = true;
		}
	}
}
