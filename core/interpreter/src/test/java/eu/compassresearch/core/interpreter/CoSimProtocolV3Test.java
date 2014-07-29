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
import org.overture.interpreter.values.RealValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.ObservableLabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.assistant.CmlInterpreterAssistantFactory;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.CoSimProtocolVersion3;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.ICoSimProtocol;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

public class CoSimProtocolV3Test
{

	ICoSimProtocol protocol = new CoSimProtocolVersion3();

	/* messages */

	@Test
	public void testRegisterSubSystemV1() throws Exception
	{
		String expected = "[\"eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage\", {	\"processes\" : [\"java.util.Vector\", [\"A\",\"B\"]],\"version\" : \"3.0.0\"}]";
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
		String expected = "{\"FinishedReplyMessage\":{\"process\":\"A\",\"finished\":false}}";
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
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new LatticeTopValue(AstFactory.newAIntNumericBasicType(null)));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"InspectReplyMessage\":{\"process\":\"P\",\"transitions\":[{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"?\":\"int\"}]}]}}", val.getHashedEventSources().first());
		JsonMessage msg = CoSimProtocolTestData.getInspectReplyMessage(val);
		compare(msg, expected);
	}

	
	
	@Test
	public void testExecuteMessage() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
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
		String expected = "{\"ExecuteCompletedMessage\", {}}";
		JsonMessage msg = CoSimProtocolTestData.getExecuteCompletedMessage();
		compare(msg, expected);
	}

	/* values */

	@Test
	public void testBooleanValue() throws Exception
	{
		String expected = "{\"bool\": false}";
		Value val = new BooleanValue(false);
		compare(val, expected);
	}

	@Test
	public void testIntegerValue() throws Exception
	{
		String expected = "{\"int\": 1}";
		Value val = new IntegerValue(1);
		compare(val, expected);
	}

	@Test
	public void testRealValue() throws Exception
	{
		String expected = "{\"real\": 1.23}";
		Value val = new RealValue(1.23);
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
		String expected = "{\"?\": \"int\"}";
		Value val = new LatticeTopValue(AstFactory.newAIntNumericBasicType(null));
		compare(val, expected);
	}

	@Test
	public void testLatticeTopValueSeq() throws Exception
	{
		String expected = "{\"?\": \"seq of (int)\"}";
		Value val = new LatticeTopValue(AstFactory.newASeqSeqType(null, AstFactory.newAIntNumericBasicType(null)));
		compare(val, expected);
	}

	/* transitions */

	@Test
	public void testTimedTransition() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		TimedTransition val = new TimedTransition(behaviour, 0);

		String expected = String.format("{\"type\":\"TimedTransition\",\"sources\":[%s],\"timelimit\":0}", val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransition() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		ChannelValue channelVal = new ChannelValue(channel);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[]}", val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransitionInt() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new IntegerValue(5));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"int\":5}]}", val.getHashedEventSources().first());

		compare(val, expected);
	}

	@Test
	public void testLabelledTransitionLatticeInt() throws Exception
	{
		AActionProcess p = new AActionProcess();
		CmlBehaviour behaviour = new DefaultCmlBehaviorFactory().newCmlBehaviour(p, null, new CmlBehaviour.BehaviourName("p"), null);
		CmlChannel channel = new CmlChannel(null, new CmlLexNameToken("M", "c", null));
		List<Value> values = new ValueList();
		values.add(new LatticeTopValue(AstFactory.newAIntNumericBasicType(null)));
		ChannelValue channelVal = new ChannelValue(channel, values);
		ObservableLabelledTransition val = new ObservableLabelledTransition(behaviour, channelVal);

		String expected = String.format("{\"type\":\"LabelledTransition\",\"sources\":[%s],\"name\":\"c\",\"values\":[{\"?\":\"int\"}]}", val.getHashedEventSources().first());

		compare(val, expected);
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
