package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

public class FCFSPolicy implements SchedulingPolicy {

	@Override
	public CmlBehaviourThread scheduleNextProcess(List<CmlBehaviourThread> runningProcesses) {

		return runningProcesses.get(0);
	}

}
