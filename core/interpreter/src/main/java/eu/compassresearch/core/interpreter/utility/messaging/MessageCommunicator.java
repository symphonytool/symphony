package eu.compassresearch.core.interpreter.utility.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class MessageCommunicator {

//	private class DateTimeDeserializer implements JsonDeserializer<CmlMessageContainer> {
//		  public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//		      throws JsonParseException {
//		    return new DateTime(json.getAsJsonPrimitive().getAsString());
//		  }
//		}
	public static void sendMessage(OutputStream outStream, Message message)
	{
		MessageContainer messageContainer = new MessageContainer(message);
		Gson gson = new Gson();
		PrintWriter writer = new PrintWriter(outStream);
		writer.println(gson.toJson(messageContainer));
		writer.flush();
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
			Gson gson = new Gson();
			message = gson.fromJson(strMessage, MessageContainer.class); 
		}
//		else
//		{
//			message = new MessageContainer(new CmlDbgStatusMessage(CmlDbgpStatus.CONNECTION_CLOSED));
//		}
		
		return message;
	}
	
	/**
	 * Constructs a message by deserializing the output from requestReader and 
	 * returns it in a CmlMessageContainer.
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
			Gson gson = new Gson();
			message = gson.fromJson(strMessage, MessageContainer.class); 
		}
		else
		{
			message = connectionClosedMessage;
		}
		
		return message;
	}
	
}
