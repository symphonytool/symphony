package eu.compassresearch.core.interpreter.debug;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.Remote;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;

public interface CmlDebugger extends Remote
{

	public void connect(String host, int port) throws UnknownHostException,
			IOException;

	public void initialize(CmlInterpreter cmlInterpreter)
			throws AnalysisException;

	public void start(SelectionStrategy strategy);
	
	public void dicsonnect();

	// public void stop();

	// public CmlDbgpStatus getStatus();

	// public void suspend();
}
