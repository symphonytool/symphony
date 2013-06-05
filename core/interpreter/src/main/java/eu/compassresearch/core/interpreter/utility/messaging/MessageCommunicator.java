package eu.compassresearch.core.interpreter.utility.messaging;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;


public class MessageCommunicator {

	private static ObjectMapper mapper = null;
	
	protected static ObjectMapper mapperInstance()
	{
		if(mapper == null)
		{
			mapper = new ObjectMapper();
			mapper.enableDefaultTyping();
			mapper.configure(MapperFeature.AUTO_DETECT_GETTERS,false);
			mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS,false);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			mapper.configure(Feature.FLUSH_PASSED_TO_STREAM, false);
			mapper.configure(Feature.AUTO_CLOSE_TARGET, false);
		}
		
		return mapper;
	}
	
	public static void sendMessage(OutputStream outStream, Message message)
	{
		MessageContainer messageContainer = new MessageContainer(message);
		try {
			mapperInstance().writeValue(outStream,messageContainer);
			outStream.write(System.lineSeparator().getBytes());
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//PrintWriter writer = new PrintWriter(outStream);
		//writer.println(mapperInstance().toJson(messageContainer));
		//writer.flush();
	}
	
	/**
	 * Constructs a message by deserializing the output from requestReader and 
	 * returns it in a CmlMessageContainer.
	 * @param requestReader
	 * @return
	 * @throws IOException
	 */
	public static MessageContainer receiveMessage(BufferedReader requestReader) throws IOException
	{
		MessageContainer message = null;
		String strMessage = requestReader.readLine();
		if(strMessage != null)
		{
			message = mapperInstance().readValue(strMessage, MessageContainer.class); 
		}
//		else
//		{
//			message = new MessageContainer(new CmlDbgStatusMessage(CmlDbgpStatus.CONNECTION_CLOSED));
//		}
		
		return message;
	}
	
	/**
	 * Constructs a message by deserializing the output from requestReader and 
	 * returns it in a MessageContainer.
	 * @param requestReader
	 * @return
	 * @throws IOException
	 */
	public static MessageContainer receiveMessage(BufferedReader requestReader, MessageContainer connectionClosedMessage) throws IOException
	{
		MessageContainer message = null;
		String strMessage = requestReader.readLine();
		if(strMessage != null)
		{
			message = mapperInstance().readValue(strMessage, MessageContainer.class); 
		}
		else
		{
			message = connectionClosedMessage;
		}
		
		return message;
	}
	
}
