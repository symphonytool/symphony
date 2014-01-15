package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressMonitor;

public class RttMbtProgressMonitor implements IRttMbtProgressMonitor {
	protected IProgressMonitor monitor;
	
	public RttMbtProgressMonitor() {
		monitor = null;
	}

	public void setMonitor(IProgressMonitor m) {
		monitor = m;
	}

	public void beginTask(String taskname, int totalWork) {
		if (monitor != null) {
			monitor.beginTask(taskname, totalWork);
		}
	}

	public void setSubTaskName(String taskname) {
		if (monitor != null) {
			monitor.subTask(taskname);
		}
	}

	public void addCompletedTaskItems(int itemsWorked) {
		if (monitor != null) {
			monitor.worked(itemsWorked);
		}
	}

	public boolean isCanceled() {
		if (monitor != null) {
			return monitor.isCanceled();
		}
		return false;
	}

	public void setCanceled(boolean canceled) {
		if (monitor != null) {
			monitor.setCanceled(canceled);
		}
	}
}
