package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtCleanProject extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: Please select an RTT-MBT component!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// check if the selected item is a project
		File item = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		if (!item.isDirectory()) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: the selected item '" + selectedObject + "' is not a directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		if (client.getProjectName().compareTo(selectedObject) != 0) {
			client.addErrorMessage("[FAIL]: Cleanup RTT-MBT project: the selected item '" + selectedObject + "' is no RTT-MBT project!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		Job job = new Job("Cleanup Project") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// cleanup project
				if (client.cleanProject(selectedObject)) {
					client.addLogMessage("[PASS]: clean RTT-MBT project " + selectedObject);
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {
					client.addLogMessage("[FAIL]: clean RTT-MBT project " + selectedObject);
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
