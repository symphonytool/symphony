package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtCompileRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Compile Test";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
		}
		
		// check that a test procedure is selected
		if ((!isRttTestProcSelected()) && (!RttMbtClient.isRtt6TestProcedure(selectedObjectFilesystemPath))) {
			client.addErrorMessage("Please select a valid test procedure!");
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("compile test procedure " + selectedObjectName, 5);
		String rttTestprocPath = client.getPathInsideRttTestcontext(selectedObjectFilesystemPath);
		client.addLogMessage("compiling test procedure " + rttTestprocPath + "... please wait for the task to be finished.");

		// compile test procedure
		if (client.compileTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: compile test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: compile test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
