package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtDeleteFile extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: Please select a file or directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		Job job = new Job("Delete RTT-MBT Resource") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// perform action using client
				Boolean success;
				File item = new File(client.getCmlWorkspace() + selectedObjectPath);
				if (!item.exists()) {
					client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: file or directory '" + item.getAbsolutePath() + "' does not exist!");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}
				
				if ((!item.isFile()) && (!item.isDirectory())) {
					client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: selection is not a file or directory!");			
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}

				// remove resource on RTT-MBT server
				success = client.deleteRemoteFileOrDir(item.getAbsolutePath());
				if (!success) {
					client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: error while removing resource from RTT-MBT server");
				} else {
					if (item.isDirectory()) {
						success = client.deleteLocalDirectory(item);						
					} else if (item.isFile()) {
						success = item.delete();
					}
				}

				if (success) {
					client.addLogMessage("[PASS]: Delete RTT-MBT Resource!");			
				} else {			
					client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource!");			
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
