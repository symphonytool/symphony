package eu.compassresearch.core.interpreter.debug;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.utility.messaging.Message;
import eu.compassresearch.core.interpreter.utility.messaging.MessageType;


public class CmlDbgStatusMessage extends Message {

	private CmlInterpreterState interpreterStatus;
	
	//dummy for serialization
	private CmlDbgStatusMessage(){}
	
	public CmlDbgStatusMessage(CmlInterpreterState interpreterStatus)
	{
		this.interpreterStatus = interpreterStatus;
	}
	
	public CmlDbgStatusMessage(CmlInterpretationStatus state)
	{
		this.interpreterStatus = new CmlInterpreterState(state);
	}
	
	public CmlInterpretationStatus getStatus() {
		if(this.interpreterStatus != null)
			return this.interpreterStatus.getInterpreterState();
		else 
			return CmlInterpretationStatus.TERMINATED;
	}
	
	public CmlInterpreterState getInterpreterStatus() {
		return interpreterStatus;
	}

	@Override
	public String toString() {
		return CmlDbgStatusMessage.class.getSimpleName() + System.lineSeparator() +
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
