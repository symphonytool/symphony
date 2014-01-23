package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCheckModel extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Check Model";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// get model file name
		final File model = new File(selectedObjectFilesystemPath);
		if (!model.exists()) {
			client.addErrorMessage("[FAIL]: check model: unable to find model file " +
		                           model.getAbsolutePath() + "!");
			return Status.OK_STATUS;
		}
		
		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("livelock check " + selectedObjectName, 4);

		// perform livelock check
		client.addLogMessage("performing livelock check on model " + model.getAbsolutePath() + " ... please wait for the task to be finished.");
		// run live lock check on the model
		if (client.livelockCheckModelFile(model)) {
			client.addLogMessage("[PASS]: livelock check - check test results in model/LivelockReport.log");
		} else {
			client.addErrorMessage("[FAIL]: livelock check");
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
