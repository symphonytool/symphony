package eu.compassresearch.core.interpreter.cosim.communication.protocol;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.FieldValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NumericValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.cosim.communication.DisconnectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteCompletedMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedRequestMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;

/**
 * This is a custom written serialization of interpreter objects to json
 * 
 * @author kel
 */
public class CoSimProtocolVersion3 implements ICoSimProtocol
{
	final Logger logger = LoggerFactory.getLogger(CoSimProtocolVersion3.class);
	public static final String VERSION = "2.0.0";

	@SuppressWarnings("rawtypes")
	private static String encodeObject(Object o) throws Exception
	{
		if (o == null)
		{
			return "null";
		} else if (o instanceof String)
		{
			return "\"" + o + "\"";
		} else if (o instanceof Integer || o instanceof Long
				|| o instanceof Double || o instanceof Boolean)
		{
			return o.toString();
		} else if (o instanceof JsonMessage)
		{
			return encodeMessage((JsonMessage) o);
		} else if (o instanceof Value)
		{
			return encodeValue((Value) o);
		} else if (o instanceof FieldValue)
		{
			FieldValue field = (FieldValue) o;
			return String.format(ValueTemplate, encodeObject(field.name), encodeObject(field.value));
		} else if (o instanceof PType)
		{
			return encodeType((PType) o);
		} else if (o instanceof CmlTransition)
		{
			return encodeTransition((CmlTransition) o);
		} else if (o instanceof List)
		{
			return encodeObject(((List) o).toArray());
		} else if (o instanceof Set)
		{
			return encodeObject(((Set) o).toArray());
		} else if (o instanceof CmlTransitionSet)
		{
			return encodeObject(((CmlTransitionSet)o).asSet());
		} else if (o.getClass().isArray())
		{
			StringBuffer sb = new StringBuffer("[");

			int length = Array.getLength(o);

			for (int i = 0; i < length; i++)
			{
				sb.append(encodeObject(Array.get(o, i)));
				if (i < length - 1)
				{
					sb.append(",");
				}
			}
			sb.append("]");
			return sb.toString();
		}

		throw new Exception("unable to encode to jacon: " + o);
	}

	private static String encodeType(PType o) throws Exception
	{
		// TODO
		return encodeObject(o.toString());
	}

	private static String encodeTransition(CmlTransition o) throws Exception
	{

		if (o instanceof TauTransition)
		{
			// TODO: add tau transition, static node with skip
		} else if (o instanceof TimedTransition)
		{
			final String field = String.format(TimedTransotionField, encodeObject(((TimedTransition) o).getTimeLimit()));
			return String.format(Transition, "TimedTransition", encodeObject(o.getHashedEventSources()), field);
		} else if (o instanceof LabelledTransition)
		{
			final LabelledTransition lt = (LabelledTransition) o;
			final String field = String.format(LabelledTransotionField, encodeObject(lt.getChannelName().getChannel().getName()), encodeObject(lt.getChannelName().getValues()));
			return String.format(Transition, "LabelledTransition", encodeObject(o.getHashedEventSources()), field);
		}
		throw new Exception("Unable to encode transition: " + o);
	}

	private static String encodeValue(Value o) throws Exception
	{
		String value = null;

		if (o instanceof BooleanValue)
		{
			value = encodeObject(((BooleanValue) o).value);
		} else if (o instanceof NumericValue)
		{
			if (o instanceof IntegerValue)
			{
				value = encodeObject(((IntegerValue) o).intValue(null));
			} else
			{
				value = encodeObject(((NumericValue) o).value);
			}
		} else if (o instanceof SeqValue)
		{
			value = encodeObject(((SeqValue) o).values);
		} else if (o instanceof RecordValue)
		{
			value = encodeObject(((RecordValue) o).fieldmap);
		} else if (o instanceof LatticeTopValue)
		{
			value = encodeObject(((LatticeTopValue) o).getType());
		}

		// TODO: add missing values

		if (value != null)
		{
			return String.format(ValueTemplate, encodeObject(o.kind()), value);
		}

		throw new Exception("Unable to encode value: " + o);
	}

	final static String RegisterSubSystemTemplate = "[\"eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage\",{\"processes\":[\"java.util.Vector\",%s],\"version\" : %s}]";
	final static String FinishedRequestMessage = "{\"FinishedRequestMessage\":{\"process\":%s}}";
	final static String FinishedReplyMessage = "{\"FinishedReplyMessage\":{\"process\":%s,\"finished\":%s}}";
	final static String INSPECTMESSAGE_STRING = "{\"InspectMessage\":{\"process\" : %s}}";
	final static String InspectReplyMessage = "{\"InspectReplyMessage\": {\"process\": %s,\"transitions\": %s }}";
	final static String ExecuteMessage = "{\"ExecuteMessage\": %s}";

	final static String Transition = "{\"type\": \"%s\", \"sources\": %s, %s}";
	final static String TimedTransotionField = "\"timelimit\": %s";
	final static String LabelledTransotionField = "\"name\" : %s, \"values\": %s";

	final static String ValueTemplate = "{%s : %s}";

	private static String encodeMessage(JsonMessage msg) throws Exception
	{
		if (msg instanceof RegisterSubSystemMessage)
		{
			return String.format(RegisterSubSystemTemplate, encodeObject(((RegisterSubSystemMessage) msg).getProcesses()), encodeObject(((RegisterSubSystemMessage) msg).getVersion()));
		} else if (msg instanceof DisconnectMessage)
		{
			return "{\"DisconnectMessage\" : {}}";
		} else if (msg instanceof FinishedRequestMessage)
		{
			return String.format(FinishedRequestMessage, encodeObject(((FinishedRequestMessage) msg).getProcess()));
		} else if (msg instanceof FinishedReplyMessage)
		{
			FinishedReplyMessage rmsg = (FinishedReplyMessage) msg;
			return String.format(FinishedReplyMessage, encodeObject(rmsg.getProcess()), encodeObject(rmsg.isFinished()));
		} else if (msg instanceof InspectMessage)
		{
			return String.format(INSPECTMESSAGE_STRING, encodeObject(((InspectMessage) msg).getProcess()));
		} else if (msg instanceof InspectReplyMessage)
		{
			InspectReplyMessage rmsg = (InspectReplyMessage) msg;
			return String.format(InspectReplyMessage, encodeObject(rmsg.getProcess()), encodeObject(rmsg.getTransitions()));
		} else if (msg instanceof ExecuteMessage)
		{
			return String.format(ExecuteMessage, encodeObject(((ExecuteMessage) msg).getTransition()));
		} else if (msg instanceof ExecuteCompletedMessage)
		{
			return "{\"ExecuteCompletedMessage\", {}}";
		}

		return "NOT_SUPPORTED";
	}

	@Override
	public <T> T decode(byte[] data, Class<T> type) throws Exception
	{
		T res = null;
		res = MessageCommunicator.mapperInstance().readValue(data, type);

		logger.trace("Decoded RAW: " + new String(data) + " as type: "
				+ type.getSimpleName() + " --- " + res);
		return res;
	}

	@Override
	public String getVersion()
	{
		return "1.0.0";
	}

	@Override
	public byte[] encode(Object o) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(encodeObject(o).getBytes());
		out.flush();
		logger.trace("Encoded RAW: " + out.toString());
		return out.toByteArray();
	}
}
