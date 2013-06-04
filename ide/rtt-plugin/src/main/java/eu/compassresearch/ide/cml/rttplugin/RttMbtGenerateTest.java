package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtGenerateTest extends RttMbtAbstractTestProcedureAction  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select a test procedure generation context!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: generate test procedure: init of RTT-MBT client failed!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
			client.addLogMessage("adjusting selected object to '" + selectedObjectPath + "'\n");
		}
		
		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!\n");
		}
		
		Job job = new Job("Generate Test Procedure") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("generating test procedure " + selectedObject + "... please wait for the task to be finished.\n");
				// generate concrete test procedure
				if (client.generateTestProcedure(selectedObject)) {
					client.addLogMessage("[PASS]: generate test procedure\n");
				} else {
					client.addErrorMessage("[FAIL]: generate test procedure\n");
				}
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
