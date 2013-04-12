//package eu.compassresearch.core.interpreter.scheduler;
//
//import java.util.List;
//
//import org.overture.ast.analysis.AnalysisException;
//
//import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
//import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
//
//public interface CmlScheduler {
//
//	public void addProcess(CmlBehaviourThread process);
//	
////	public void removeProcess(CmlProcess process);
//	
//	public void clearProcesses();
//	
//	public List<CmlBehaviourThread> getRunningProcesses();
//	
//	public List<CmlBehaviourThread> getAllProcesses();
//	
//	public boolean hasRunningProcesses();
//	
//	public boolean hasWaitingProcesses();
//	
//	/**
//	 * Determines whether any Active CmlBehaviourThread object exists
//	 * @return true if there exists a CmlBehaviourThread object with one of the following
//	 * 		   States : RUNNABLE,RUNNING,WAIT_EVENT, WAIT_CHILD
//	 * 		   else false
//	 */
//	public boolean hasActiveProcesses();
//
//	/**
//	 * Determines whether the top process is deadlocked
//	 * @return true if the top process is deadlocked otherwise false
//	 */
//	public boolean isDeadlocked();
//	
//	/**
//	 * Start the scheduling
//	 */
//	public void start(CmlSupervisorEnvironment sve);
//	
//	/**
//	 * Stop the scheduling
//	 */
//	public void stop();
//	
//	/**
//	 * Returns the number of steps taken so far
//	 * @return The number of steps taken so far
//	 */
//	public long steps();
//}
