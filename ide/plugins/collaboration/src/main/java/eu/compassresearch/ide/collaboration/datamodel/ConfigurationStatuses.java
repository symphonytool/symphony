package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ecf.core.identity.ID;

public class ConfigurationStatuses extends Model
{
	private static final long serialVersionUID = -1010290128250140048L;
	
	protected Map<String, ConfigurationStatus> configStatuses;
	
	public ConfigurationStatuses(Model parent)
	{
		super("Sent To", parent);
		configStatuses = new HashMap<String, ConfigurationStatus>();
	}

	public ConfigurationStatuses(Map<String, ConfigurationStatus> statuses,
			Model newParent)
	{
		this(newParent);
		configStatuses = statuses;
	}

	@Override
	public void addListener(IModelEventListener listener) {
		
		for (ConfigurationStatus configStatus : configStatuses.values())
		{
			configStatus.addListener(listener);
		}
		
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(IModelEventListener listener)
	{
		for (ConfigurationStatus configStatus : configStatuses.values())
		{
			configStatus.removeListener(listener);
		}
		
		super.removeListener(listener);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " (" + configStatuses.size() + ")";
	}
	
	
	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

	@Override
	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
	}

	public ConfigurationStatuses copy(Model newParent)
	{
		Map<String, ConfigurationStatus> clone = new HashMap<String,ConfigurationStatus>();
		
		ConfigurationStatus newCS;
		for (ConfigurationStatus cs : configStatuses.values())
		{
			newCS = cs.copy(newParent);
			clone.put(newCS.getName(), newCS);
		}
		
		return new ConfigurationStatuses(clone, newParent);
	}

	public void addConfigurationStatus(String user){
		ConfigurationStatus status = new ConfigurationStatus(user, this);
		configStatuses.put(status.getName(), status);
		status.addListener(listener);
		fireObjectUpdatedEvent(status);
	}
	
	public void addConfigurationStatus(List<String> sentTo)
	{
		for (String userName : sentTo)
		{
			ConfigurationStatus status = new ConfigurationStatus(userName, this);
			configStatuses.put(status.getName(), status);
			status.addListener(listener);
		}
		fireObjectUpdatedEvent(this);
	}
	
	
	public ConfigurationStatus getConfigurationStatus(ID connectedUser)
	{
		return configStatuses.get(connectedUser.getName());
	}
	
	public List<ConfigurationStatus> getStatusList() {
		return new ArrayList<ConfigurationStatus>(configStatuses.values());
	}
}
