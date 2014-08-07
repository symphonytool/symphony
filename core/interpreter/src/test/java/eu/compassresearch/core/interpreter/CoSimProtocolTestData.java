package eu.compassresearch.core.interpreter;

import java.util.Arrays;
import java.util.List;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.ObservableLabelledTransition;
import eu.compassresearch.core.interpreter.cosim.communication.DisconnectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteCompletedMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedRequestMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

public class CoSimProtocolTestData
{
	public static RegisterSubSystemMessage getRegisterSubSystemMessage()
	{
		List<String> processes = Arrays.asList(new String[] { "A", "B" });
		RegisterSubSystemMessage msg = new RegisterSubSystemMessage(processes, "3.0.0");
		return msg;
	}
	
	public static DisconnectMessage getDisconnectMessage()
	{
		DisconnectMessage msg = new DisconnectMessage();
		return msg;
	}
	
	public static FinishedRequestMessage getFinishedRequestMessage()
	{
		return new FinishedRequestMessage("A");
	}
	
	public static FinishedReplyMessage getFinishedReplyMessage()
	{
		return new FinishedReplyMessage("A",false);
	}
	
	public static InspectMessage getInspectMessage()
	{
		return new InspectMessage("A");
	}

	public static JsonMessage getExecuteCompletedMessage()
	{
		return new ExecuteCompletedMessage();
	}

	public static InspectReplyMessage getInspectReplyMessage(ObservableLabelledTransition val)
	{
		InspectReplyMessage msg = new InspectReplyMessage("P", new CmlTransitionSet(val));
		return msg;
	}

	public static JsonMessage getExecuteMessage(ObservableLabelledTransition val)
	{
		ExecuteMessage msg = new ExecuteMessage(val);
		return msg;
	}
}
