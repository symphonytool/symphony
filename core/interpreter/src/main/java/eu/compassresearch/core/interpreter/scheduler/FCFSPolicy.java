package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlProcess;

public class FCFSPolicy implements SchedulingPolicy {

	@Override
	public CmlProcess scheduleNextProcess(List<CmlProcess> runningProcesses) {

		return runningProcesses.get(0);
	}

}
