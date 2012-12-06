package eu.compassresearch.core.interpreter.debug.messaging;

public class CmlDbgCommandMessage extends CmlMessage {

	protected CmlDebugCommand command;
	
	public CmlDbgCommandMessage(CmlDebugCommand command)
	{
		setCommand(command);
	}

	public CmlDebugCommand getCommand() {
		return command;
	}

	public void setCommand(CmlDebugCommand command) {
		this.command = command;
	} 
	
	@Override
	public CmlMessageType getType() {
		return CmlMessageType.COMMAND;
	}

	@Override
	public String getKey() {
		return getCommand().toString();
	}
}
