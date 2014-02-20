package eu.compassresearch.core.interpreter.utility.messaging;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.interpreter.values.BooleanValue;
import org.overture.interpreter.values.CharacterValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NaturalOneValue;
import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.RationalValue;
import org.overture.interpreter.values.RealValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;

@RunWith(Parameterized.class)
public class JsonValueTest
{
	@Parameters(name = "{index} : {0} - \"{1}\"")
	public static Collection<Object[]> getCmlfilePaths() throws Exception
	{
		Value[] values = new Value[] { new QuoteValue("TEST"),
				new BooleanValue(false), new CharacterValue('c'),
				new IntegerValue(-45), new NaturalValue(0),
				new NaturalOneValue(3), new RationalValue(1 / 3),
				new RealValue(123.123),new SeqValue("My test string"), new LatticeTopValue(new AIntNumericBasicType()) };

		List<Object[]> tests = new Vector<Object[]>();

		for (Value p : values)
		{
			tests.add(new Object[] { p.getClass().getSimpleName(), p });
		}

		return tests;
	}

	private Value val;

	public JsonValueTest(String name, Value val)
	{
		this.val = val;
	}

	@Test
	public void test() throws IOException
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		MessageCommunicator.sendRawMessage(outStream, val);

		ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Object v = MessageCommunicator.receiveRawMessage(reader);

		Assert.assertEquals(val.toString(), v.toString());
	}
}
