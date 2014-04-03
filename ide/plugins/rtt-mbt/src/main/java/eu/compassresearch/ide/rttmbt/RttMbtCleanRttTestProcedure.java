package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtCleanRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Clean Test";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("cleanup test procedure " + selectedObjectName, 5);

		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
			client.addLogMessage("adjusting selected object to '" + selectedObjectWorkspacePath + "'");
		}

		// check that a test procedure is selected
		if ((isRttTestProcSelected()) || (RttMbtClient.isRtt6TestProcedure(selectedObjectFilesystemPath))) {

			// clean test procedure
			String rttTestprocPath = client.getPathInsideRttTestcontext(selectedObjectFilesystemPath);
			client.addLogMessage("cleanup test procedure " + rttTestprocPath + "... please wait for the task to be finished.");
			if (client.cleanTestProcedure(rttTestprocPath)) {
				client.addLogMessage("[PASS]: clean up test procedure " + selectedObjectName);
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			} else {
				client.addErrorMessage("[FAIL]: clean up test procedure " + selectedObjectName);
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			}
			
		} else {
			client.addErrorMessage("[FAIL]: clean up test procedure: " + selectedObjectName + "is not a valid test procedure!");
			status = Status.CANCEL_STATUS;
			client.addCompletedTaskItems(4);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
