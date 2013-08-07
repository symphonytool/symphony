package eu.compassresearch.core.interpreter.debug;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.utility.messaging.Message;
import eu.compassresearch.core.interpreter.utility.messaging.MessageType;


public class CmlDbgStatusMessage extends Message {

	//private CmlDbgpStatus status;
	private InterpreterStatus interpreterStatus;
	
//	protected CmlDbgStatusMessage()
//	{}
	
	public CmlDbgStatusMessage(InterpreterStatus interpreterStatus)
	{
		this.interpreterStatus = interpreterStatus;
		//this.setStatus(status);
	}
	
	public CmlDbgStatusMessage()
	{
		this.interpreterStatus = null;
		//this.setStatus(status);
	}
	
	public CmlInterpreterState getStatus() {
		if(this.interpreterStatus != null)
			return this.interpreterStatus.getInterpreterState();
		else 
			return null;
	}
	
	public InterpreterStatus getInterpreterStatus() {
		return interpreterStatus;
	}

//	protected void setStatus(CmlDbgpStatus status) {
//		this.status = status;
//	}
			
	@Override
	public String toString() {
		return CmlDbgStatusMessage.class.getSimpleName() + System.lineSeparator() +
			   "Status: "+ getStatus() + System.lineSeparator() +
			   this.interpreterStatus;
				
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
