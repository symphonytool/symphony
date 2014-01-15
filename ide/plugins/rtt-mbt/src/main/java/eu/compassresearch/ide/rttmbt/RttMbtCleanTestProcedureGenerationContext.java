package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCleanTestProcedureGenerationContext extends RttMbtAbstractTestProcedureAction  {

	@Override
	public String getTaskName() {
		return "Clean Test Generation";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("cleanup test procedure generation context " + selectedObjectName, 4);

		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}
		
		// check that test procedure generation context is selected
		if (isTProcGenCtxSelected()) {
			client.addLogMessage("cleaning up test procedure generation context " + selectedObjectName + "... please wait for the task to be finished.");
			// cleanup concrete test procedure
			if (client.cleanTestProcedureGenerationContext(selectedObjectName)) {
				client.addLogMessage("[PASS]: cleanup test procedure generation context " + selectedObjectName);
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			} else {
				client.addErrorMessage("[FAIL]: cleanup test procedure generation context " + selectedObjectName);
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			}
		} else {
			client.addErrorMessage("[FAIL]: cleanup test procedure generation context " + selectedObjectName + ": please select a valid test procedure generation context!");
			status = Status.CANCEL_STATUS;
			client.addCompletedTaskItems(3);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
