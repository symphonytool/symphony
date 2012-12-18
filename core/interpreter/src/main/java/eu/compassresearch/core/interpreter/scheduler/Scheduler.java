package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;

public interface Scheduler {

	public void addProcess(CmlProcess process);
	
	public void clearProcesses();
	
	public List<CmlProcess> getRunningProcesses();
	
	public List<CmlProcess> getAllProcesses();
	
	public boolean hasRunningProcesses();
	
	public boolean hasWaitingProcesses();
	
	public boolean hasActiveProcesses();
	
	public void start() throws AnalysisException;
	
	public void setCmlSupervisorEnvironment(CmlSupervisorEnvironment sve);
	
	public CmlSupervisorEnvironment getCmlSupervisorEnvironment();
	
	
}
