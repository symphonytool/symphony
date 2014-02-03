package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtGenerateTest extends RttMbtAbstractTestProcedureAction  {

	@Override
	public String getTaskName() {
		return "Generate Test";
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
		client.beginTask("generate test procedure " + selectedObjectName, 5);

		client.addLogMessage("generating test procedure " + selectedObjectName + "... please wait for the task to be finished.");
		// generate concrete test procedure
		if (client.generateTestProcedure(selectedObjectName)) {
			client.addLogMessage("[PASS]: generate test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure " + selectedObjectName);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
