package eu.compassresearch.ide.collaboration.communication.messages;

import org.eclipse.ecf.core.identity.ID;

public class RelayMessage extends BaseMessage
{
	private static final long serialVersionUID = 1005137147422242162L;

	private String inputData; 
	
	public RelayMessage(ID sender, String collabProjectID, String inputData)
	{
		super(sender, collabProjectID);
		this.inputData = inputData;
	}

	public String getInputData()
	{
		return inputData;
	}
}
