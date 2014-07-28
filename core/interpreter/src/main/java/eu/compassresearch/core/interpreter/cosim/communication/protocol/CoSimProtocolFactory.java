package eu.compassresearch.core.interpreter.cosim.communication.protocol;

import java.util.HashMap;
import java.util.Map;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;

/**
 * Cosim protocol factory
 * 
 * @author kel
 */
public class CoSimProtocolFactory
{
	Map<String, Class<?>> protocols = new HashMap<String, Class<?>>();

	public final String DEFAULT_VERSION = CoSimProtocolVersion1.VERSION;

	public CoSimProtocolFactory()
	{
		protocols.put(CoSimProtocolVersion1.VERSION, CoSimProtocolVersion1.class);
		protocols.put(CoSimProtocolVersion2.VERSION, CoSimProtocolVersion2.class);
		protocols.put(CoSimProtocolVersion3.VERSION, CoSimProtocolVersion3.class);
	}

	/**
	 * Obtain an new instance of the protocol with the given version number. If not able to obtain the protocol
	 * {@link InterpreterRuntimeException}s are thrown with the cause.
	 * 
	 * @param version
	 * @return the protocol
	 */
	public ICoSimProtocol getInstance(String version)
	{
		Class<?> c = this.protocols.get(version);
		if (c == null)
		{
			throw new InterpreterRuntimeException("Unable to obtain cosim protocol with version: "
					+ version);
		}

		try
		{
			return (ICoSimProtocol) c.newInstance();
		} catch (InstantiationException e)
		{
			throw new InterpreterRuntimeException("Unable to instanciate cosim protocol with version: "
					+ version);
		} catch (IllegalAccessException e)
		{
			throw new InterpreterRuntimeException("Unable to instanciate (illegalaccess) cosim protocol with version: "
					+ version);
		}
	}
}
