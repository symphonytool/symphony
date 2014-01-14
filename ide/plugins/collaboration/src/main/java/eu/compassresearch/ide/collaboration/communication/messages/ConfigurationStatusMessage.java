package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.user.IUser;

public class ConfigurationStatusMessage extends BaseMessage
{
	private static final long serialVersionUID = 2945404088404137893L;

	private final String configurationId;
	private final NegotiationStatus status; 

	public enum NegotiationStatus {
	    ACCEPT, REJECT, RENEGOTIATE; 
	}
	
	public ConfigurationStatusMessage(IUser sentBy, IUser sentTo, String projectID, String configurationId, NegotiationStatus status)
	{
		super(sentBy, sentTo, projectID);
		
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
