package eu.compassresearch.core.interpreter;

import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ThreadState;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.RealValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.TokenValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableLabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.assistant.CmlInterpreterAssistantFactory;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteCompletedMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.CoSimProtocolVersion3;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.ICoSimProtocol;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

public class CoSimProtocolV3Test
{

	private static final String INSPECT_REPLY_MESSAGE = "{\"InspectReplyMessage\":{\"process\":\"P\",\"transitions\":[{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"?\":\"int\"}]}]}}";
	private static final String TIMED_TRANSITION_TIMELIMIT_0 = "{\"type\":\"TimedTransition\",\"sources\":[%s],\"timelimit\":0}";
	private static final String LABELLED_TRANSITION_INT_5 = "{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"int\":5}]}";
	private static final String LABELLED_TRANSITION_NO_VALUE = "{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[]}";
	private static final String LATTICE_SEQ_OF_INT = "{\"?\": \"seq of (int)\"}";
	private static final String LATTICE_INT = "{\"?\": \"int\"}";
	private static final String REAL_1_23 = "{\"real\": 1.23}";
	private static final String INT_1 = "{\"int\": 1}";
	private static final String QUOTE_A = "{\"quote\": \"a\"}";
	private static final String Token_1 = "{\"token\": {\"int\":1}}";
	private static final String BOOL_FALSE = "{\"bool\": false}";
	private static final String FINISHED_REPLY_MESSAGE = "{\"FinishedReplyMessage\":{\"process\":\"A\",\"finished\":false}}";
	private static final String EXECUTE_COMPLETED_MESSAGE = "{\"ExecuteCompletedMessage\": {}}";
	private static final String REG_SUB_SYSTEM = "[\"eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage\", {	\"processes\" : [\"java.util.Vector\", [\"A\",\"B\"]],\"version\" : \"3.0.0\"}]";
	ICoSimProtocol protocol = new CoSimProtocolVersion3();

	/* messages */

	@Test
	public void testRegisterSubSystemV1() throws Exception
	{
		String expected = REG_SUB_SYSTEM;
		JsonMessage msg = CoSimProtocolTestData.getRegisterSubSystemMessage();
		compare(msg, expected);

	}

	@Test
	public void testDisconnectMessage() throws Exception
	{
		String expected = "{\"DisconnectMessage\" : {}}";
		JsonMessage msg = CoSimProtocolTestData.getDisconnectMessage();
		compare(msg, expected);
	}

	@Test
	public void testFinishedRequestMessage() throws Exception
	{
		String expected = "{\"FinishedRequestMessage\":{\"process\":\"A\"}}";
		JsonMessage msg = CoSimProtocolTestData.getFinishedRequestMessage();
		compare(msg, expected);
	}

	@Test
	public void testFinishedReplyMessage() throws Exception
	{
		String expected = FINISHED_REPLY_MESSAGE;
		JsonMessage msg = CoSimProtocolTestData.getFinishedReplyMessage();
		compare(msg, expected);
	}

	@Test
	public void testInspectMessage() throws Exception
	{
		String expected = "{\"InspectMessage\":{\"process\" : \"A\"}}";
		JsonMessage msg = CoSimProtocolTestData.getInspectMessage();
		compare(msg, expected);
	}

	@Test
	public void testInspectReplyMessage() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new LatticeTopValue(AstFactory.newAIntNumericBasicType(null)));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format(INSPECT_REPLY_MESSAGE, val.getHashedEventSources().first());
		JsonMessage msg = CoSimProtocolTestData.getInspectReplyMessage(val);
		compare(msg, expected);
	}

	@Test
	public void testExecuteMessage() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new LatticeTopValue(AstFactory.newAIntNumericBasicType(null)));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"ExecuteMessage\":{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"?\":\"int\"}]}}", val.getHashedEventSources().first());
		JsonMessage msg = CoSimProtocolTestData.getExecuteMessage(val);
		compare(msg, expected);
	}

	@Test
	public void testExecuteCompletedMessage() throws Exception
	{
		String expected = EXECUTE_COMPLETED_MESSAGE;
		JsonMessage msg = CoSimProtocolTestData.getExecuteCompletedMessage();
		compare(msg, expected);
	}

	/* values */

	@Test
	public void testBooleanValue() throws Exception
	{
		String expected = BOOL_FALSE;
		Value val = new BooleanValue(false);
		compare(val, expected);
	}

	@Test
	public void testIntegerValue() throws Exception
	{
		String expected = INT_1;
		Value val = new IntegerValue(1);
		compare(val, expected);
	}

	@Test
	public void testRealValue() throws Exception
	{
		String expected = REAL_1_23;
		Value val = new RealValue(1.23);
		compare(val, expected);
	}
	
	@Test
	public void testQuoteValue() throws Exception
	{
		String expected = QUOTE_A;
		Value val = new QuoteValue("a");
		compare(val, expected);
	}
	
	@Test
	public void testTokenValue() throws Exception
	{
		String expected = Token_1;
		Value val = new TokenValue(new IntegerValue(1));
		compare(val, expected);
	}

	@Test
	public void testRecordValue() throws Exception
	{
		String expected = "{\"R1\":[{\"f1\":{\"bool\":false}},{\"f2\":{\"int\":5}}]}";
		List<AFieldField> fields = new Vector<AFieldField>();
		fields.add(AstFactory.newAFieldField(new CmlLexNameToken("m", "f1", null), "f1", AstFactory.newABooleanBasicType(null), false));
		fields.add(AstFactory.newAFieldField(new CmlLexNameToken("m", "f2", null), "f2", AstFactory.newAIntNumericBasicType(null), false));
		ARecordInvariantType recType = AstFactory.newARecordInvariantType(new CmlLexNameToken("m", "R1", null), fields);
		ValueList values = new ValueList();
		values.add(new BooleanValue(false));
		values.add(new IntegerValue(5));
		final Context ctxt = new Context(new CmlInterpreterAssistantFactory(), null, null, null);
		BasicSchedulableThread.add(new InitThread(Thread.currentThread()));
		ctxt.threadState = new ThreadState(null, null);
		Value val = new RecordValue(recType, values, ctxt);
		compare(val, expected);
	}

	@Test
	public void testLatticeTopValue() throws Exception
	{
		String expected = LATTICE_INT;
		Value val = new LatticeTopValue(AstFactory.newAIntNumericBasicType(null));
		compare(val, expected);
	}

	@Test
	public void testLatticeTopValueSeq() throws Exception
	{
		String expected = LATTICE_SEQ_OF_INT;
		Value val = new LatticeTopValue(AstFactory.newASeqSeqType(null, AstFactory.newAIntNumericBasicType(null)));
		compare(val, expected);
	}

	/* transitions */

	@Test
	public void testTimedTransition() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		TimedTransition val = new TimedTransition(behaviour, 0);

		String expected = String.format(TIMED_TRANSITION_TIMELIMIT_0, val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransition() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		ChannelValue channelVal = new ChannelValue(channel);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format(LABELLED_TRANSITION_NO_VALUE, val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransitionInt() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new IntegerValue(5));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format(LABELLED_TRANSITION_INT_5, val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransitionLatticeInt() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new VanillaInterpreterFactory().getBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new LatticeTopValue(AstFactory.newAIntNumericBasicType(null)));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"?\":\"int\"}]}", val.getHashedEventSources().first());

		compare(val, expected);
	}

	/* decoding */
	@Test
	public void testDecodeRegisterSubSystem() throws Exception
	{
		String msg = REG_SUB_SYSTEM;
		JsonMessage m = protocol.decode(msg.getBytes(), JsonMessage.class);
		Assert.assertEquals("Wrong class type", m.getClass(), RegisterSubSystemMessage.class);
		
		RegisterSubSystemMessage rm = (RegisterSubSystemMessage) m;
		
		Assert.assertEquals(rm.getVersion(), "3.0.0");
		Assert.assertTrue("Missing process A", rm.getProcesses().contains("A"));
		Assert.assertTrue("Missing process B", rm.getProcesses().contains("B"));
	}
	
	@Test
	public void testDecodeExecuteCompleted() throws Exception
	{
		String msg = EXECUTE_COMPLETED_MESSAGE;
		JsonMessage m = protocol.decode(msg.getBytes(), JsonMessage.class);
		Assert.assertEquals("Wrong class type", m.getClass(), ExecuteCompletedMessage.class);
	}
	
	
	@Test
	public void testDecodeFinishedReply() throws Exception
	{
		JsonMessage m = protocol.decode(FINISHED_REPLY_MESSAGE.getBytes(), JsonMessage.class);
		Assert.assertEquals("Wrong class type", m.getClass(), FinishedReplyMessage.class);
		
		FinishedReplyMessage fr = (FinishedReplyMessage) m;
		
		Assert.assertEquals("Wrong process","A" ,fr.getProcess());
		Assert.assertFalse("Wrong finished value", fr.isFinished());
		
	}
	
	@Test
	public void testDecodeInspectReply() throws Exception
	{
		JsonMessage m = protocol.decode(String.format(INSPECT_REPLY_MESSAGE,"5").getBytes(), JsonMessage.class);
		Assert.assertEquals("Wrong class type", m.getClass(), InspectReplyMessage.class);
		
		InspectReplyMessage msg = (InspectReplyMessage) m;
		
		Assert.assertEquals("Wrong number of transitions",1 ,msg.getTransitions().size());
		Assert.assertEquals("Wrong transition type",ObservableLabelledTransition.class ,msg.getTransitions().iterator().next().getClass());
	}
	
	@Test
	public void testDecodeIntValue() throws Exception
	{
		String msg = INT_1;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), IntegerValue.class);
	}

	
	@Test
	public void testDecodeBooleanValue() throws Exception
	{
		String msg = BOOL_FALSE;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), BooleanValue.class);
	}
	
	
	@Test
	public void testDecodeQuoteValue() throws Exception
	{
		String msg = QUOTE_A;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), QuoteValue.class);
		Assert.assertEquals(((QuoteValue)m).value, "a");
	}
	
	@Test
	public void testDecodeTokenValue() throws Exception
	{
		String msg = Token_1;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), TokenValue.class);
		final Value tokenValue = CoSimProtocolVersion3.getTokenValue(((TokenValue)m));
		Assert.assertEquals(tokenValue.getClass(), IntegerValue.class);
		Assert.assertEquals(((IntegerValue)tokenValue).intValue(null), 1);
	}
	
	
	@Test
	public void testDecodeLatticeTopValue() throws Exception
	{
		String msg = LATTICE_INT;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), LatticeTopValue.class);
	}
	
	@Test
	public void testDecodeLatticeTopValueSeqInt() throws Exception
	{
		String msg = LATTICE_SEQ_OF_INT;
		Value m = protocol.decode(msg.getBytes(), Value.class);
		Assert.assertEquals("Wrong class type", m.getClass(), LatticeTopValue.class);
	}
	
	
	@Test
	public void testDecodeTimedTransition() throws Exception
	{
		String msg = String.format(TIMED_TRANSITION_TIMELIMIT_0.replace('0', '6'),5);
		CmlTransition m = protocol.decode(msg.getBytes(), CmlTransition.class);
		Assert.assertEquals("Wrong class type", m.getClass(), TimedTransition.class);
		
		
		Assert.assertEquals("Time limit is wrong", 6,((TimedTransition)m).getTimeLimit());
		Assert.assertTrue("Source id not in hashed sources", m.getHashedEventSources().contains(5));
	}
	
	@Test
	public void testDecodeLabelledTransition() throws Exception
	{
		String msg = String.format(LABELLED_TRANSITION_NO_VALUE,5);
		CmlTransition m = protocol.decode(msg.getBytes(), CmlTransition.class);
		Assert.assertEquals("Wrong class type", ObservableLabelledTransition.class,m.getClass());
		
		Assert.assertTrue("Source id not in hashed sources", m.getHashedEventSources().contains(5));
		Assert.assertEquals("Wrong name", "c",((LabelledTransition)m).getChannelName().getChannel().getName());
	}
	
	@Test
	public void testDecodeLabelledTransitionInt() throws Exception
	{
		String msg = String.format(LABELLED_TRANSITION_INT_5,5);
		CmlTransition m = protocol.decode(msg.getBytes(), CmlTransition.class);
		Assert.assertEquals("Wrong class type", ObservableLabelledTransition.class,m.getClass());
		
		Assert.assertTrue("Source id not in hashed sources", m.getHashedEventSources().contains(5));
		final LabelledTransition transition = (LabelledTransition)m;
		Assert.assertEquals("Wrong name", "c",transition.getChannelName().getChannel().getName());
		
		Assert.assertEquals("Wrong number of values",1,transition.getChannelName().getValues().size());
		Assert.assertEquals("Wrong value type",IntegerValue.class,transition.getChannelName().getValues().get(0).getClass());
		Assert.assertEquals("Wrong value value",5,transition.getChannelName().getValues().get(0).intValue(null));
	}
	
	/* utility methods */

	private void compare(Object msg, String expectedJsonText) throws Exception
	{
		String expected = expectedJsonText.replaceAll(" ", "").replaceAll("\t", "");

		byte[] res = protocol.encode(msg);
		String x = new String(res);
		x = x.replaceAll(" ", "").replaceAll("\t", "");
		Assert.assertEquals("Decoded message didnt match excepted \nActual:\n"
				+ x + "\n\nExpected:\n" + expected + "\n", expected, x);
	}

}
