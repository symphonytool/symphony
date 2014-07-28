package eu.compassresearch.core.interpreter;

import org.junit.Assert;
import org.junit.Test;

import eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.CoSimProtocolVersion1;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.ICoSimProtocol;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

public class CoSimProtocolV1Test
{
	
	ICoSimProtocol protocol = new CoSimProtocolVersion1();

	@Test
	public void testRegisterSubSystemV1() throws Exception
	{
		String msg = "[[\"eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage\",{\"processes\":[\"java.util.Vector\",[\"B\"]]}]]";

		checkRegisterSystem( msg,"1.0.0");
		
	}
	
	@Test
	public void testRegisterSubSystemV2() throws Exception
	{
		String msg = "[[\"eu.compassresearch.core.interpreter.cosim.communication.RegisterSubSystemMessage\", {	\"processes\" : [\"java.util.Vector\", [\"B\"]],\"version\" : \"2.0.0\"}]]";

		checkRegisterSystem( msg,"2.0.0");

	}

	protected RegisterSubSystemMessage checkRegisterSystem( String msg, String version)
			throws Exception
	{
		JsonMessage res = protocol.decode(msg.getBytes(), JsonMessage.class);

		Assert.assertTrue("Type not "
				+ RegisterSubSystemMessage.class.getSimpleName() + ". Act: "
				+ msg.getClass().getSimpleName(), res instanceof RegisterSubSystemMessage);

		Assert.assertEquals("Wrong version", version,((RegisterSubSystemMessage)res).getVersion());
		return (RegisterSubSystemMessage)res;
	}

}
