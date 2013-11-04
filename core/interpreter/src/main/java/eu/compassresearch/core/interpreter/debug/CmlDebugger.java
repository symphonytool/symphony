package eu.compassresearch.core.interpreter.debug;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.Remote;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;

public interface CmlDebugger extends Remote
{

	public void connect(String host,int port) throws UnknownHostException, IOException;

	public void initialize(CmlInterpreter cmlInterpreter)
			throws AnalysisException;

	public void start(InterpreterExecutionMode mode);

	// public void stop();

	// public CmlDbgpStatus getStatus();

	// public void suspend();
}
