package eu.compassresearch.core.interpreter.cosim.communication.protocol;

public interface ICoSimProtocol
{
	public String getVersion();

	public byte[] encode(Object o) throws Exception;

	public <T> T decode(byte[] data, Class<T> type) throws Exception;
}
