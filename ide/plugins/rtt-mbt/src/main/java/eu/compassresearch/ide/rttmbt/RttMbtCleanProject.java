package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCleanProject extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Clean Project";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// check if the selected item is a project
		File item = new File(selectedObjectFilesystemPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}
		if (!item.isDirectory()) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: the selected item '" + selectedObjectName + "' is not a directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("cleanup project " + selectedObjectName, 5);

		// cleanup project
		client.addLogMessage("cleanup RTT-MBT project " + selectedObjectName + " ... please wait for the task to be finished.");
		if (client.cleanProject(selectedObjectName)) {
			client.addLogMessage("[PASS]: clean RTT-MBT project " + selectedObjectName);
		} else {
			client.addLogMessage("[FAIL]: clean RTT-MBT project " + selectedObjectName);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
