package eu.compassresearch.core.interpreter.cosim.communication.protocol;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;
 
public class CoSimProtocolVersion1 implements ICoSimProtocol
{
	final Logger logger = LoggerFactory.getLogger(CoSimProtocolVersion1.class);
	@Override
	public <T> T decode(byte[] data, Class<T> type) throws Exception
	{
		T res=null;
		res=(T) MessageCommunicator.mapperInstance().readValue(data, type);
		logger.trace("Decoded RAW: " +new String( data)+" as type: "+type.getSimpleName()+ " --- "+res);
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
		MessageCommunicator.mapperInstance().writeValue(out, new Object[] { o });
		out.flush();
		logger.trace("Encoded RAW: " + out.toString());
		return out.toByteArray();
	}
}
