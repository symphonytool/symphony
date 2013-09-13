package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCheckModel extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an RTT-MBT component!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: check model: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get model file name
		final File model = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!model.exists()) {
			client.addErrorMessage("[FAIL]: check model: unable to find model file " +
		                           model.getAbsolutePath() + "!");
			return null;
		}

		Job job = new Job("Check Model") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("performing livelock check on model " + model.getAbsolutePath() + " ... please wait for the task to be finished.");
				// run live lock check on the model
				if (client.livelockCheckModelFile(model)) {
					client.addLogMessage("[PASS]: livelock check - check test results in model/LivelockReport.log");
				} else {
					client.addErrorMessage("[FAIL]: livelock check");
				}
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
				
		return null;
	}
}
