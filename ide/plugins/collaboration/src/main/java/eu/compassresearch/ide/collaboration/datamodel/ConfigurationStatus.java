package eu.compassresearch.ide.collaboration.datamodel;

public class ConfigurationStatus extends Model
{
	public enum ConfigurationNegotiationStatus
	{
		ACCEPT, REJECT, RENEGOTIATED, NOTSET;
	};

	private ConfigurationNegotiationStatus status;

	public ConfigurationStatus(String userName, Model parent)
	{
		super(userName, parent);
		status = ConfigurationNegotiationStatus.NOTSET;
	}

	private static final long serialVersionUID = -5659255568664645397L;

	@Override
	public CollaborationProject getCollaborationProject()
	{
		return getParent().getCollaborationProject();
	}

	@Override
	public void accept(IModelVisitor visitor, Object passAlongArgument)
	{
	}

	@Override
	public String toString()
	{
		String statusText;
		switch (status)
		{
			case ACCEPT:
				statusText = " (Accepted)";
				break;
			case REJECT:
				statusText = " (Rejected)";
				break;
			case RENEGOTIATED:
				statusText = " (Renegotiated)";
				break;
			default:
				statusText = "";
				break;
		}

		return name + statusText;
	}

	public ConfigurationNegotiationStatus getStatus()
	{
		return status;
	}

	public void setStatus(ConfigurationNegotiationStatus status)
	{
		if (this.status == ConfigurationNegotiationStatus.NOTSET)
		{
			this.status = status;
			fireObjectUpdatedEvent(this);
		}
	}

	public ConfigurationStatus copy(Model newParent)
	{
		return new ConfigurationStatus(name,newParent);
	}
}
