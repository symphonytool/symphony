package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class ConfigurationStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 2945404088404137893L;

	private final String configurationId;
	private final NegotiationStatus status; 

	public enum NegotiationStatus {
	    ACCEPT, REJECT, RENEGOTIATED; 
	}
	
	public ConfigurationStatusMessage(ID sender, String projectID, String configurationId, NegotiationStatus status)
	{
		super(sender,projectID);
		
		this.configurationId = configurationId;
		this.status = status;
	}

	public String getConfigurationId()
	{
		return configurationId;
	}

	public NegotiationStatus getStatus()
	{
		return status;
	}
}
