package eu.compassresearch.core.interpreter.cosim.communication.protocol;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.Dialect;
import org.overture.ast.node.Node;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.FieldValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NumericValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexTokenReader;
import org.overture.parser.syntax.TypeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableLabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
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

/**
 * This is a custom written serialization of interpreter objects to json
 * 
 * @author kel
 */
public class CoSimProtocolVersion3 implements ICoSimProtocol
{
	public static final String VERSION = "2.0.0";

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

	final Logger logger = LoggerFactory.getLogger(CoSimProtocolVersion3.class);
	JSONParser parser = new JSONParser();

	/**
	 * Sets a field on an object
	 * 
	 * @param name
	 * @param source
	 * @param value
	 * @throws IllegalAccessException
	 */
	public static void setField(String name, Object source, Object value)
			throws IllegalAccessException
	{
		Field field = null;

		List<Field> fields = Node.getAllFields(new Vector<Field>(), source.getClass());
		for (Field f : fields)
		{
			if (f.getName().equals(name))
			{
				f.setAccessible(true);
				field = f;
				break;
			}
		}

		if (field == null)
		{
			throw new InterpreterRuntimeException("Unable to access field "
					+ name);
		}

		field.set(source, value);
	}

	/**
	 * Gets the value of a field on an object
	 * 
	 * @param name
	 * @param source
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Object getField(String name, Object source)
			throws IllegalAccessException
	{
		Field field = null;

		List<Field> fields = Node.getAllFields(new Vector<Field>(), source.getClass());
		for (Field f : fields)
		{
			if (f.getName().equals(name))
			{
				f.setAccessible(true);
				field = f;
				break;
			}
		}

		if (field == null)
		{
			throw new InterpreterRuntimeException("Unable to access field "
					+ name);
		}

		return field.get(source);
	}

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
			return encodeObject(((CmlTransitionSet) o).asSet());
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
			return "{\"ExecuteCompletedMessage\": {}}";
		}

		return "NOT_SUPPORTED";
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T decode(byte[] data, Class<T> type) throws Exception
	{
		T res = null;

		Object obj = parser.parse(new String(data));

		if (obj instanceof JSONArray)
		{
			JSONArray jArr = (JSONArray) obj;

			if (!jArr.isEmpty())
			{
				Object item = jArr.get(0);
				if (item instanceof String
						&& RegisterSubSystemMessage.class.getName().equals(item
								+ ""))
				{
					if (jArr.size() < 2)
					{
						aboartDecode("decoding "
								+ RegisterSubSystemMessage.class.getName()
								+ " data missing");
					}
					res = (T) decodeMessage(RegisterSubSystemMessage.class, jArr.get(1));
				}
			}

		} else if (obj instanceof JSONObject)
		{
			JSONObject jObj = (JSONObject) obj;
			Object o = decode(jObj);
			if (o instanceof Map)
			{
				Map map = (Map) o;

				Class<?>[] messageTypes = new Class<?>[] {
						ExecuteCompletedMessage.class,
						FinishedReplyMessage.class, InspectReplyMessage.class };

				for (Class<?> class1 : messageTypes)
				{
					res = (T) tryDecode(map, jObj, class1);
					if (res != null)
					{
						break;
					}
				}

			}
			System.out.println(o);
		}

		if (res == null)
		{
			Object tmp = decode(obj);
			Object tmp2 = decodeCoSim(tmp);

			if (tmp2 != null)
			{
				res = (T) tmp2;
			} else
			{
				aboartDecode("unable to decode: " + new String(data));
			}
		}

		logger.trace("Decoded RAW: " + new String(data) + " as type: "
				+ type.getSimpleName() + " --- " + res);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object decodeCoSim(Object tmp) throws Exception
	{
		if (tmp instanceof Map)
		{

			Map map = (Map) tmp;
			// objects

			if (map.size() == 1)
			{
				Entry entry = (Entry) map.entrySet().iterator().next();

				String key = entry.getKey() + "";

				List<String> types = Arrays.asList(new String[] { "int",
						"bool", "?" });

				for (String type : types)
				{
					if (type.equals(key))
					{
						return createValue(type, entry.getValue());
					}
				}
			} else if (map.containsKey("type"))
			{
				final String type = "" + map.get("type");
				if (type.contains(TimedTransition.class.getSimpleName()))
				{
					TimedTransition transition = TimedTransition.class.newInstance();
					setField("timeLimit", transition, (long) map.get("timelimit"));
					setHashedEventSources(map, transition);
					return transition;

				} else if (type.contains(LabelledTransition.class.getSimpleName()))
				{
					Constructor<ObservableLabelledTransition> c = ObservableLabelledTransition.class.getDeclaredConstructor();
					c.setAccessible(true);
					ObservableLabelledTransition transition = c.newInstance();
					setHashedEventSources(map, transition);

					List vals = (List) map.get("values");
					ILexNameToken name =new CmlLexNameToken("", (String)map.get("name"), null) ;
					
					List<Value> values = new Vector<Value>();

					if (!vals.isEmpty())
					{
						List<Value> o = (List<Value>) decodeCoSim(vals);
						values.addAll(o);
					}
					
					ChannelValue channelName = new ChannelValue(new CmlChannel(null,name ),values);
					setField("channelName", transition, channelName);

					return transition;

				} else if (type.contains(TauTransition.class.getSimpleName()))
				{

				}
			}
		}else if(tmp instanceof List)
			{
				List list = new Vector();
				for (Object object : (List)tmp)
				{
					list.add(decodeCoSim(object));
				}
				return list;
			}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setHashedEventSources(Map map, CmlTransition transition)
			throws IllegalAccessException
	{
		SortedSet<Integer> hashedEventSources = new TreeSet<Integer>();

		for (Long id : (List<Long>) map.get("sources"))
		{
			hashedEventSources.add(id.intValue());
		}

		setField("hashedEventSources", transition, hashedEventSources);
	}

	private Object createValue(String type, Object value) throws Exception
	{
		if (type.equals("int"))
		{
			return new IntegerValue((long) value);
		} else if (type.equals("bool"))
		{
			return new BooleanValue((boolean) value);
		} else if (type.equals("?"))
		{
			LexTokenReader reader = new LexTokenReader(value + "", Dialect.VDM_SL);
			TypeReader typeReader = new TypeReader(reader);
			return new LatticeTopValue(typeReader.readType());
		}

		aboartDecode("unable to decode value of type: '" + type
				+ "' with value: '" + value + "'");
		return value;
	}

	@SuppressWarnings({ "rawtypes" })
	private JsonMessage tryDecode(Map m, JSONObject obj, Class<?> type)
			throws Exception
	{
		if (m.containsKey(type.getSimpleName()))
		{
			return decodeMessage(type, obj.values().iterator().next());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JsonMessage decodeMessage(Class<?> type, Object object)
			throws Exception
	{
		JsonMessage msg = null;
		JSONObject jObj = null;

		if (object instanceof JSONObject)
		{
			jObj = (JSONObject) object;
		} else
		{
			aboartDecode("message tail is not an object: " + object);
		}

		if (type == RegisterSubSystemMessage.class)
		{
			final List<Object> rawProcesses = decode(jObj.get("processes"));
			List<String> processes = (List<String>) rawProcesses.get(1);
			String version = decode(jObj.get("version"));

			msg = new RegisterSubSystemMessage(processes, version);
		} else if (type == ExecuteCompletedMessage.class)
		{
			msg = new ExecuteCompletedMessage();
		} else if (type == FinishedReplyMessage.class)
		{
			String process = decode(jObj.get("process"));
			Boolean finished = decode(jObj.get("finished"));
			msg = new FinishedReplyMessage(process, finished);
		}else if (type == InspectReplyMessage.class)
		{
			
			String process = decode(jObj.get("process"));
			
			List<CmlTransition> list =(List<CmlTransition>) decodeCoSim(decode(jObj.get("transitions")));
			
			SortedSet<CmlTransition> transitions = new TreeSet<CmlTransition>();
			transitions.addAll(list);
			
			CmlTransitionSet transitionsSet = new CmlTransitionSet(transitions);
			msg = new InspectReplyMessage(process, transitionsSet);
		}

		return msg;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> T decode(Object o) throws Exception
	{
		if (o instanceof JSONArray)
		{
			List list = new Vector();
			JSONArray arr = (JSONArray) o;
			for (Object object : arr)
			{
				list.add(decode(object));
			}
			return (T) list;
		} else if (o instanceof JSONObject)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			for (Object en : ((JSONObject) o).entrySet())
			{
				Map.Entry entry = (Entry) en;
				final Object key = entry.getKey();
				if (key instanceof String)
				{
					map.put("" + key, decode(entry.getValue()));
				} else
				{
					aboartDecode("decode of map with none string key not supported");
				}
			}
			return (T) map;
		} else if (o instanceof String || o instanceof Integer
				|| o instanceof Boolean || o instanceof Long)
		{
			return (T) o;
		}

		throw new Exception("Aborting decode. Failed '" + o + "' type: '"
				+ o.getClass().getSimpleName() + "'");
	}

	public void aboartDecode(String msg) throws Exception
	{
		throw new Exception("Aborting decode. " + msg);
	}
}
