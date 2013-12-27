package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCleanTestProcedureGenerationContext extends RttMbtAbstractTestProcedureAction  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select a test procedure generation context!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient()) {
			client.addErrorMessage("[FAIL]: cleanup test procedure: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// if a test procedure is selected, switch to test procedure generation context
		if ((!isTProcGenCtxSelected()) && (isRttTestProcSelected())) {
			getTProcGenCtxPathFromRttTestProcPath();
		}
		
		// check that test procedure generation context is selected
		if (!isTProcGenCtxSelected()) {
			client.addErrorMessage("Please select a valid test procedure generation context!");
		}
		
		Job job = new Job("Cleanup Test Procedure") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("cleaning up test procedure generation context " + selectedObjectName + "... please wait for the task to be finished.");
				// cleanup concrete test procedure
				if (client.cleanTestProcedureGenerationContext(selectedObjectName)) {
					client.addLogMessage("[PASS]: cleanup test procedure");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {
					client.addErrorMessage("[FAIL]: cleanup test procedure");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
		return null;
	}
}
