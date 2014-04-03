package eu.compassresearch.core.interpreter.debug.messaging;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	final static Logger logger = LoggerFactory.getLogger(MessageCommunicator.class);

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

			ObjectMapper	m = new ObjectMapper();
			MyModule module = new MyModule();
			// module.addAbstractTypeMapping(org.overture.ast.intf.lex.ILexNameToken.class,DefaultCmlLexNameToken.class);
			// module.a.addAbstractTypeMapping(org.overture.ast.node.NodeList.class, NodeListJsonWrapper.class);

			m.enableDefaultTyping();
			m.registerModule(module);
			m.enableDefaultTyping();
			m.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
			m.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
			m.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			m.configure(Feature.FLUSH_PASSED_TO_STREAM, false);
			m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			m.configure(Feature.AUTO_CLOSE_TARGET, false);
			mapper = m;

		}

		return mapper;
	}

	public static void sendMessage(OutputStream outStream, AbstractMessage message)
			throws IOException
	{
		MessageContainer messageContainer = new MessageContainer(message);
		sendRawMessage(outStream, messageContainer);
	}

	public static <T> void sendRawMessage(OutputStream outStream, T message)
			throws IOException
	{
		logger.debug("Sending..." + message);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapperInstance().writeValue(out,new Object[]{ message});
		logger.trace("Send RAW: "+out.toString());
		mapperInstance().writeValue(outStream,new Object[]{ message});
		outStream.write(System.lineSeparator().getBytes());
		outStream.flush();
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
		return receiveRawMessage(requestReader);
	}

	@SuppressWarnings("unchecked")
	public static <T> T receiveRawMessage(BufferedReader requestReader) throws IOException
	{
		T message = null;
		String strMessage = requestReader.readLine();
		
		logger.trace("Read RAW: "+strMessage);
		if (strMessage != null)
		{
			try
			{
				Object[] data = mapperInstance().readValue(strMessage, Object[].class);
				if(data!=null && data.length>0)
				{
					message = (T) data[0];
					logger.debug("Read "+message);
				}
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
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
			MessageContainer defaultMessage) throws IOException
	{
		MessageContainer message = receiveMessage(requestReader);
		if (message == null)
		{
			message = defaultMessage;
		}

		return message;
	}

}
