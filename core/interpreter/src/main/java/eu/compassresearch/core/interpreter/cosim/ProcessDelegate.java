package eu.compassresearch.core.interpreter.cosim;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.cosim.communication.ConnectionThread;

/**
 * A concrete process delegate
 * 
 * @author kel
 */
public class ProcessDelegate implements IProcessDelegate
{
	private final String processName;
	private ConnectionThread conn;

	public ProcessDelegate(String processName, ConnectionThread conn)
	{
		this.processName = processName;
		this.conn = conn;
	}

	@Override
	public CmlTransitionSet inspect() throws Exception
	{
		return conn.inspect(this.processName);
	}

	@Override
	public void execute(CmlTransition transition) throws Exception
	{
		conn.execute(transition);
	}

	@Override
	public String getProcessName()
	{
		return this.processName;
	}

	@Override
	public boolean isFinished() throws Exception
	{
		return conn.isFinished(processName);
	}

	@Override
	public void disconnect() throws Exception
	{
		conn.disconnect();
	}

}
