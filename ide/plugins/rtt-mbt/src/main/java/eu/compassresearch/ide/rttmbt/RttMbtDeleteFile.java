package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtDeleteFile extends RttMbtPopupMenuAction {

	@Override
	public String getTaskName() {
		return "Delete Rtt-Mbt Resource";
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// check for valid resource
		Boolean success;
		File item = new File(selectedObjectFilesystemPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource " + selectedObjectName + ": file or directory '" + selectedObjectFilesystemPath + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}
		
		if ((!item.isFile()) && (!item.isDirectory())) {
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource " + selectedObjectName + ": selection is not a file or directory!");			
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("Delete RTT-MBT Resource " + selectedObjectName, 3);

		// remove resource on RTT-MBT server
		client.setSubTaskName("remove local resource");
		success = client.deleteRemoteFileOrDir(item.getAbsolutePath());
		client.addCompletedTaskItems(1);

		// remove local resource
		if (!success) {
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource: error while removing resource from RTT-MBT server");
			status = Status.CANCEL_STATUS;
		} else {
			client.setSubTaskName("remove local resource");
			if (item.isDirectory()) {
				success = client.deleteLocalDirectory(item, false);						
			} else if (item.isFile()) {
				success = item.delete();
			}
		}
		client.addCompletedTaskItems(1);

		// finishing task
		client.setSubTaskName("finishing task");
		if (success) {
			client.addLogMessage("[PASS]: Delete RTT-MBT Resource!");			
		} else {			
			client.addErrorMessage("[FAIL]: Delete RTT-MBT Resource!");			
		}
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
