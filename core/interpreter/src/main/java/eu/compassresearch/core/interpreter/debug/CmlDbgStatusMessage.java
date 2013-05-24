package eu.compassresearch.core.interpreter.debug;

import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.utility.messaging.Message;
import eu.compassresearch.core.interpreter.utility.messaging.MessageType;


public class CmlDbgStatusMessage extends Message {

	private CmlDbgpStatus status;
	private InterpreterStatus interpreterStatus;
	
	public CmlDbgStatusMessage(CmlDbgpStatus status, InterpreterStatus interpreterStatus)
	{
		this.interpreterStatus = interpreterStatus;
		this.setStatus(status);
	}
	
	public CmlDbgStatusMessage(CmlDbgpStatus status)
	{
		this.interpreterStatus = null;
		this.setStatus(status);
	}
	
	public CmlDbgpStatus getStatus() {
		return status;
	}
	
	public InterpreterStatus getInterpreterStatus() {
		return interpreterStatus;
	}

	protected void setStatus(CmlDbgpStatus status) {
		this.status = status;
	}
			
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		return CmlDbgStatusMessage.class.getSimpleName() + newLine +
			   "Status: "+ status;
	}

	@Override
	public MessageType getType() {
		return MessageType.STATUS;
	}

	@Override
	public String getKey() {
		return getStatus().toString();
	}
}
