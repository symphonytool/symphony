package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCompileRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select a test procedure!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: compile test procedure: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// if a test procedure generation context is selected, switch to test procedure
		if ((!isRttTestProcSelected()) && (isTProcGenCtxSelected())) {
			getRttTestProcPathFromTProcGenCtxPath();
		}
		
		// check that a test procedure is selected
		if (!isRttTestProcSelected()) {
			client.addErrorMessage("Please select a valid test procedure!");
		}

		Job job = new Job("Compile Test") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("compiling test procedure " + selectedObject + "... please wait for the task to be finished.");
				// compile test procedure
				if (client.compileTestProcedure(selectedObject)) {
					client.addLogMessage("[PASS]: compile test procedure");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {
					client.addErrorMessage("[FAIL]: compile test procedure");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		return null;
	}
}
