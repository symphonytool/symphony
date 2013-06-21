package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtRetrieveFileOrDirectory extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Retrieve Directory/File from RTT-MBT Server Work Area: Please select an File or directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: Retrieve Directory/File from RTT-MBT Server Work Area: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		Job job = new Job("pull from server") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// perform action using client
				Boolean success;
				File item = new File(client.getCmlWorkspace() + selectedObjectPath);
				if (!item.exists()) {
					client.addErrorMessage("[FAIL]: Retrieve Directory/File from RTT-MBT Server Work Area: file or directory '" + item.getAbsolutePath() + "' does not exist!");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}
				
				if (item.isDirectory()) {
					success = client.downloadDirectory(item.getAbsolutePath());
				} else if (item.isFile()) {
					success = client.downloadFile(item.getAbsolutePath());
				} else {
					client.addErrorMessage("[FAIL]: Retrieve Directory/File from RTT-MBT Server Work Area: selection is not a file or directory!");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}
				
				if (success) {
					client.addLogMessage("[PASS]: Retrieve Directory/File from RTT-MBT Server Work Area!");			
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {			
					client.addErrorMessage("[FAIL]: Retrieve Directory/File from RTT-MBT Server Work Area!");			
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		return null;
	}
}
