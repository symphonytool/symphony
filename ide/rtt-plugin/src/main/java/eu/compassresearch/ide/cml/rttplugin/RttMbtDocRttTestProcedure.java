package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtDocRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an abstract test procedure!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: generate test procedure documentation: init of RTT-MBT client failed!\n");
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

		Job job = new Job("Generate Documentation") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("generating documentation for " + selectedObject + "... please wait for the task to be finished.\n");
				// generate test procedure documentation
				if (client.docTestProcedure(selectedObject)) {
					client.addLogMessage("[PASS]: generate test procedure documentation\n");
				} else {
					client.addErrorMessage("[FAIL]: generate test procedure documentation\n");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
