package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtRetrieveFileOrDirectory extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Pull from Server";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// check argument
		Boolean success;
		File item = new File(selectedObjectFilesystemPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Retrieve file or directory " + selectedObjectName + ": file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}
		
		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("Retrieve file or directory " + selectedObjectName, 1);

		// perform action using client
		if (item.isDirectory()) {
			success = client.downloadDirectory(item.getAbsolutePath());
		} else if (item.isFile()) {
			success = client.downloadFile(item.getAbsolutePath());
		} else {
			client.addErrorMessage("[FAIL]: Retrieve file or directory " + selectedObjectName + ": selection is not a file or directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			client.addCompletedTaskItems(1);
			return Status.CANCEL_STATUS;
		}
		
		if (success) {
			client.addLogMessage("[PASS]: Retrieve file or directory " + selectedObjectName);			
		} else {			
			client.addErrorMessage("[FAIL]: Retrieve file or directory" + selectedObjectName);			
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
