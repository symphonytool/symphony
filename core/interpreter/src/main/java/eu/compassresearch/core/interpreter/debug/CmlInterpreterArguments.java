package eu.compassresearch.core.interpreter.debug;

public enum CmlInterpreterArguments
{

	CML_SOURCES_PATH("sources_path"),

	CML_EXEC_MODE("exec_mode"),

	PROCESS_NAME("process_name"),

	PORT("port"),

	HOST("host"),

	REMOTE_NAME("remote"), AUTO_FILTER_TOCK_EVENTS("auto_filter_tock_events"),

	COSIM_MODE("cosim_mode"),

	COSIM_EXTERNAL_PROCESSES("cosim_external"),

	COSIM_HOST("cosim_host");

	final String PREFIX = "eu.compassresearch.ide.interpreter.";

	public final String key;

	private CmlInterpreterArguments(String s)
	{
		key = PREFIX + s;
	}

	public String toString()
	{
		return key;
	}

}
