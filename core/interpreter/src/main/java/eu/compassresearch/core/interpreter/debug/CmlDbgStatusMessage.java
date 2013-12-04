package eu.compassresearch.core.interpreter.debug;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.debug.messaging.Message;
import eu.compassresearch.core.interpreter.debug.messaging.MessageType;

public class CmlDbgStatusMessage extends Message
{

	private CmlInterpreterStateDTO interpreterStatus;

	// dummy for serialization
	@SuppressWarnings("unused")
	private CmlDbgStatusMessage()
	{
	}

	public CmlDbgStatusMessage(CmlInterpreterStateDTO interpreterStatus)
	{
		this.interpreterStatus = interpreterStatus;
	}

	public CmlDbgStatusMessage(CmlInterpreterState state)
	{
		this.interpreterStatus = new CmlInterpreterStateDTO(state);
	}

	public CmlInterpreterState getStatus()
	{
		if (this.interpreterStatus != null)
		{
			return this.interpreterStatus.getInterpreterState();
		} else
		{
			return CmlInterpreterState.TERMINATED_BY_USER;
		}
	}

	public CmlInterpreterStateDTO getInterpreterStatus()
	{
		return interpreterStatus;
	}

	@Override
	public String toString()
	{
		return getStatus().toString();
	}

	@Override
	public MessageType getType()
	{
		return MessageType.STATUS;
	}

	@Override
	public String getKey()
	{
		return getStatus().toString();
	}
}
