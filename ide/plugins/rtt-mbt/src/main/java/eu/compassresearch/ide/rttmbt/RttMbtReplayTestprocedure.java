package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtReplayTestprocedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Replay Test Result";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}
		
		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!");
			return Status.CANCEL_STATUS;
		}
		
		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("replay test procedure " + selectedObjectName, 5);

		client.addLogMessage("replaying test results from test procedure " + selectedObjectName + "... please wait for the task to be finished.");
		// replay test procedure
		if (client.replayTestProcedure(selectedObjectName)) {
			client.addLogMessage("[PASS]: replay test procedure " + selectedObjectName);
		} else {
			client.addErrorMessage("[FAIL]: replay test procedure " + selectedObjectName);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
