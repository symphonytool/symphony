package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlProcess;

public interface SchedulingPolicy {

	CmlProcess scheduleNextProcess(List<CmlProcess> runningProcesses);
}
