package eu.compassresearch.core.interpreter.utility.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.ast.types.PType;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.debug.CmlDebugCommand;


public class MessageCommunicator {

	private static ObjectMapper mapper = null;
	
	protected static ObjectMapper mapperInstance()
	{
		if(mapper == null)
		{
			abstract class MixIn {
				  MixIn(@JsonProperty("module")String module, 
						  @JsonProperty("name")String name, 
						  @JsonProperty("location")ILexLocation location){}
				  
				  @JsonIgnore List<PType> typeQualifier;
			}
//			abstract class OvertureLexNameTokenMixIn {
//				OvertureLexNameTokenMixIn(@JsonProperty("module")String module, 
//						  @JsonProperty("name")String name, 
//						  @JsonProperty("location")ILexLocation location){}
//				  
//				  @JsonIgnore List<PType> typeQualifier;
//			}
			
			abstract class NodeMixIn {
				@JsonIgnore INode parent;	
				@JsonIgnore Set _visitedNodes;
			}
			
			class MyModule extends SimpleModule
			{
				public MyModule() {
				    super("MyModule");
				  }
				  @Override
				  public void setupModule(SetupContext context)
				  {
				    context.setMixInAnnotations(LexNameToken.class, MixIn.class);
				    context.setMixInAnnotations(org.overture.ast.lex.LexNameToken.class, MixIn.class);
				    context.setMixInAnnotations(Node.class,NodeMixIn.class);
				    // and other set up, if any
				  }
			}
			
			mapper = new ObjectMapper();
			mapper.registerModule(new MyModule());
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
