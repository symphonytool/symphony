package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtBuildMakeTarget extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Build Make Target";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// get Makefile path
		final File makefile = new File(selectedObjectFilesystemPath);
		if (!makefile.exists()) {
			client.addErrorMessage("[FAIL]: build make target: unable to find makefile " +
		                           makefile.getAbsolutePath() + "!");
			return Status.CANCEL_STATUS;
		}

		// get parameters for the rtt-mbt server task
		String maketarget="all";

		// start task
		client.beginTask("performing task '" + client.getMakeToolProperty() + " " + maketarget + "'", 5);
		client.addLogMessage("build make target from " + makefile.getAbsolutePath() + " ... please wait for the task to be finished.");
		// build make target
		if (client.buildMakeTargetCommand(makefile.getAbsolutePath(), maketarget)) {
			client.addLogMessage("[PASS]: build make target");
		} else {
			client.addErrorMessage("[FAIL]: build make target");
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return Status.OK_STATUS;
	}

}
