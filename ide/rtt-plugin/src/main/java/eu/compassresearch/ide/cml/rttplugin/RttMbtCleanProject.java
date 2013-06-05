package eu.compassresearch.ide.cml.rttplugin;

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
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: Please select an RTT-MBT component!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: init of RTT-MBT client failed!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// check if the selected item is a project
		File item = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		if (!item.isDirectory()) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: the selected item '" + selectedObject + "' is not a directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		Job job = new Job("Cleanup Project") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// cleanup project
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
