package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtDocRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public String getTaskName() {
		return "Generate Test Documentation";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
		}
		
		// check that a test procedure is selected
		if ((!isRttTestProcSelected()) && (!RttMbtClient.isRtt6TestProcedure(selectedObjectFilesystemPath))) {
			client.addErrorMessage("Please select a valid test procedure!\n");
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("generate test procedure documentation for " + selectedObjectName, 5);
		String rttTestprocPath = client.getPathInsideRttTestcontext(selectedObjectFilesystemPath);
		client.addLogMessage("generating documentation for " + rttTestprocPath + "... please wait for the task to be finished.");

		// generate test procedure documentation
		if (client.docTestProcedure(rttTestprocPath)) {
			client.addLogMessage("[PASS]: generate test procedure documentation for " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure documentation for " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
