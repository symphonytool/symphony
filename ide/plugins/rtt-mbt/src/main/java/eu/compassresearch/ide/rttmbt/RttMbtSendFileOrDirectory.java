package eu.compassresearch.ide.rttmbt;

import java.io.File;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RttMbtSendFileOrDirectory extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Push to Server";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// check argument
		Boolean success;
		File item = new File(selectedObjectFilesystemPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Add file or directory " + selectedObjectName + " to server: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}
		
		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("Add file or directory " + selectedObjectName, 1);

		// perform action using client
		if (item.isDirectory()) {
			success = client.deleteRemoteFileOrDir(item.getAbsolutePath());
			success = success && client.uploadDirectory(item.getAbsolutePath(), true);
		} else if (item.isFile()) {
			success = client.uploadFile(item.getAbsolutePath());			
		} else {
			client.addErrorMessage("[FAIL]: Add file or directory " + selectedObjectName + " to server: selection is not a file or directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(1);
			return Status.CANCEL_STATUS;
		}

		if (success) {
			client.addLogMessage("[PASS]: Add file or directory " + selectedObjectName + " to server");
		} else {			
			client.addErrorMessage("[FAIL]: Add file or directory " + selectedObjectName + " to server");			
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
