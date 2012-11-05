package eu.compassresearch.core.interpreter.cml;

public interface CMLProcessNetwork {

	// Network User Configuration Options
//	public CSPProcessNetworkOptions networkOptions();
	// JDASCO integration
	public void interrupt();
	public boolean isInterrupted();
	public void clearInterrupted();
	public boolean tryToInterruptOnDeadlock();
	public boolean setTryToInterruptOnDeadlock(boolean value);
	public boolean isInitialized();
	public boolean isProcessRunning(CMLProcessNew process);
	public boolean isProcessRunning(String processName);
	public boolean anyProcessRunning();
	//public void associateInvocation(CSPProcess userProcess, RecoverableMethodObject rmo,
	//SynchronizationPredicate sp, RecoveryObject ro, Object userDefined);
	public Object networkLock();
	public ThreadGroup JACKThreadGroup();
	//public CSPProcessNetworkStatus getProcessNetworkStatus();
	// Environment information - views
	//public CMLCommunicationEnvironment tracer();
	//public CSPProcessExecutionHistory processExecutionHistory();
	//public void setProcessNetworkStatus(CSPProcessNetworkStatus status);
	//public void removeProcess(CSPProcess process) throws CSPProcessException;

}
