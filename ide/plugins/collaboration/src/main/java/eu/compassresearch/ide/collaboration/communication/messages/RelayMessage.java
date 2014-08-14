package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class RelayMessage extends BaseMessage
{

	public enum RelayType {Client, Server};
	private static final long serialVersionUID = 1005137147422242162L;

	private String inputData;

	private RelayType relayFrom; 
	
	public RelayMessage(ID sender, String collabProjectID, String inputData, RelayType relayFrom)
	{
		super(sender, collabProjectID);
		this.inputData = inputData;
		this.relayFrom = relayFrom;
	}

	public String getInputData()
	{
		return inputData;
	}

	public RelayType getRelayFrom()
	{
		return relayFrom;
	}
}
