package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtBuildMakeTarget extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select a Makefile!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient()) {
			client.addErrorMessage("[FAIL]: build make target: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get Makefile path
		final File makefile = new File(selectedObjectFilesystemPath);
		if (!makefile.exists()) {
			client.addErrorMessage("[FAIL]: build make target: unable to find makefile " +
		                           makefile.getAbsolutePath() + "!");
			return null;
		}

		Job job = new Job("build make target") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("build make target from " + makefile.getAbsolutePath() + " ... please wait for the task to be finished.");
				// run live lock check on the model
				if (client.buildMakeTargetCommand(makefile.getAbsolutePath(), "all", "make")) {
					client.addLogMessage("[PASS]: build make target");
				} else {
					client.addErrorMessage("[FAIL]: build make target");
				}
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
				
		return null;
	}
}
