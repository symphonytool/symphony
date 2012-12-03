package eu.compassresearch.ide.cml.interpreter_plugin;

public class CmlDbgRequestMessage extends CmlMessage {

	protected CmlDebugCommand command;

	public CmlDebugCommand getCommand() {
		return command;
	}

	public void setCommand(CmlDebugCommand command) {
		this.command = command;
	} 
	
	@Override
	public CmlMessageType getType() {
		return CmlMessageType.REQUEST;
	}
}
