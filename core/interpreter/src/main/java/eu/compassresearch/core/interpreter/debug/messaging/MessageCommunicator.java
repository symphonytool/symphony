package eu.compassresearch.core.interpreter.debug.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;

import eu.compassresearch.ast.lex.CmlLexNameToken;

public class MessageCommunicator
{
	public static class DefaultCmlLexNameToken extends CmlLexNameToken
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 7135571662334504016L;

		public DefaultCmlLexNameToken()
		{
			super(null, null, null, false, false);
		}
	}

	private static class ILexNameTokenDeserializer extends KeyDeserializer
	{

		@Override
		public Object deserializeKey(String key, DeserializationContext ctxt)
				throws IOException, JsonProcessingException
		{
			System.out.println("Strange key: " + key);
			return new DefaultCmlLexNameToken();
		}
	}

	private static ObjectMapper mapper = null;

	protected static ObjectMapper mapperInstance()
	{
		if (mapper == null)
		{
			class MyModule extends SimpleModule
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 3893158551719185244L;

				public MyModule()
				{
					super("MyModule");
				}

				@Override
				public void setupModule(SetupContext context)
				{
					MessageCommunicatorMixins.setup(context);

					// and other set up, if any

					context.addKeyDeserializers(new KeyDeserializers()
					{

						@Override
						public KeyDeserializer findKeyDeserializer(
								JavaType type, DeserializationConfig config,
								BeanDescription beanDesc)
								throws JsonMappingException
						{
							if (type.getRawClass().equals(org.overture.ast.intf.lex.ILexNameToken.class))
							{
								return new ILexNameTokenDeserializer();
							}
							return null;
						}
					});

				}

			}

			mapper = new ObjectMapper();
			MyModule module = new MyModule();
			// module.addAbstractTypeMapping(org.overture.ast.intf.lex.ILexNameToken.class,DefaultCmlLexNameToken.class);
			// module.a.addAbstractTypeMapping(org.overture.ast.node.NodeList.class, NodeListJsonWrapper.class);

			mapper.enableDefaultTyping();
			mapper.registerModule(module);
			mapper.enableDefaultTyping();
			mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
			mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(Feature.FLUSH_PASSED_TO_STREAM, false);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(Feature.AUTO_CLOSE_TARGET, false);

		}

		return mapper;
	}

	public static void sendMessage(OutputStream outStream, Message message)
			throws IOException
	{
		System.out.println("Sending..." + message);
		MessageContainer messageContainer = new MessageContainer(message);
		mapperInstance().writeValue(outStream, messageContainer);
		outStream.write(System.lineSeparator().getBytes());
		outStream.flush();
		// System.out.println("Sendt..." + message);
		// PrintWriter writer = new PrintWriter(outStream);
		// writer.println(mapperInstance().toJson(messageContainer));
		// writer.flush();
	}

	/**
	 * Constructs a message by deserializing the output from requestReader and returns it in a CmlMessageContainer.
	 * 
	 * @param requestReader
	 * @return
	 * @throws IOException
	 */
	public static MessageContainer receiveMessage(BufferedReader requestReader)
			throws IOException
	{
		MessageContainer message = null;
		String strMessage = requestReader.readLine();
		// System.out.println("Read RAW:\n\t" + strMessage);
		if (strMessage != null)
		{
			message = mapperInstance().readValue(strMessage, MessageContainer.class);
		}

		return message;
	}

	/**
	 * Constructs a message by deserializing the output from requestReader and returns it in a MessageContainer.
	 * 
	 * @param requestReader
	 * @return
	 * @throws IOException
	 */
	public static MessageContainer receiveMessage(BufferedReader requestReader,
			MessageContainer connectionClosedMessage) throws IOException
	{
		MessageContainer message = null;
		String strMessage = requestReader.readLine();
		// System.out.println("Read RAW:\n\t" + strMessage);
		if (strMessage != null)
		{
			message = mapperInstance().readValue(strMessage, MessageContainer.class);
		} else
		{
			message = connectionClosedMessage;
		}

		return message;
	}

}
