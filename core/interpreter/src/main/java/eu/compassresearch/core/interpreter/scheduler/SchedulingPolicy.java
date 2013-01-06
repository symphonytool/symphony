package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

public interface SchedulingPolicy {

	CmlBehaviourThread scheduleNextProcess(List<CmlBehaviourThread> runningProcesses);
}
