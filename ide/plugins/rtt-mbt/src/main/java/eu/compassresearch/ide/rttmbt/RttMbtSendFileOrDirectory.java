package eu.compassresearch.ide.rttmbt;

import java.io.File;
import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class RttMbtSendFileOrDirectory extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: Please select a file or directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient()) {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		Job job = new Job("push to server") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// perform action using client
				Boolean success;
				File item = new File(selectedObjectFilesystemPath);
				if (!item.exists()) {
					client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: file or directory '" + item.getAbsolutePath() + "' does not exist!");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}
				
				if (item.isDirectory()) {
					success = client.deleteRemoteFileOrDir(item.getAbsolutePath());
					success = success && client.uploadDirectory(item.getAbsolutePath(), true);
				} else if (item.isFile()) {
					success = client.uploadFile(item.getAbsolutePath());			
				} else {
					client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: selection is not a file or directory!");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
					return Status.OK_STATUS;
				}

				if (success) {
					client.addLogMessage("[PASS]: Add Directory/File to RTT-MBT Server Work Area!");			
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {			
					client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area!");			
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		return null;
	}
}
