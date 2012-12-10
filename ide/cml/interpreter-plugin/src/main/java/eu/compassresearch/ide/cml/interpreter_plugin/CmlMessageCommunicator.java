package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class CmlMessageCommunicator {

//	private class DateTimeDeserializer implements JsonDeserializer<CmlMessageContainer> {
//		  public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//		      throws JsonParseException {
//		    return new DateTime(json.getAsJsonPrimitive().getAsString());
//		  }
//		}
	public static void sendMessage(OutputStream outStream, CmlMessage message)
	{
		CmlMessageContainer messageContainer = new CmlMessageContainer(message);
		Gson gson = new Gson();
		PrintWriter writer = new PrintWriter(outStream);
		writer.println(gson.toJson(messageContainer));
		writer.flush();
	}
	
	public static CmlMessageContainer receiveMessage(BufferedReader requestReader) throws IOException
	{
		CmlMessageContainer message = null;
		String strMessage = requestReader.readLine();
		if(strMessage != null)
		{
			Gson gson = new Gson();
			message = gson.fromJson(strMessage, CmlMessageContainer.class); 
		}
		else
		{
			message = new CmlMessageContainer(new CmlDbgStatusMessage(CmlDbgpStatus.CONNECTION_CLOSED));
		}
		
		return message;
	}
	
}
