package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtRunRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select a test procedure!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: execute test procedure: init of RTT-MBT client failed!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
			client.addLogMessage("adjusting selected object to '" + selectedObjectPath + "'\n");
		}
		
		// check that a test procedure is selected
		if (!isRttTestProcSelected()) {
			client.addErrorMessage("Please select a valid test procedure!\n");
		}

		Job job = new Job("Run Test") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("executing test procedure " + selectedObject + "... please wait for the task to be finished.\n");
				// run test procedure
				if (client.runTestProcedure(selectedObject)) {
					client.addLogMessage("[PASS]: execute test procedure\n");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {
					client.addErrorMessage("[FAIL]: execute test procedure\n");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		return null;
	}
}
