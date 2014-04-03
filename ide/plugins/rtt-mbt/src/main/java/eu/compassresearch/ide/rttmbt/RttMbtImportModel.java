package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;


public class RttMbtImportModel extends RttMbtPopupMenuAction {

	private String ModelFile;
	private String ModelName;

	@Override
	public String getTaskName() {
		return "Import Model";
	}

	@Override
	public void performJobPreprocessing() {
		// reset model
		ModelFile = null;
		ModelName = null;

		// get model file name
		if (!((PlatformUI.getWorkbench() != null) &&
			  (PlatformUI.getWorkbench().getDisplay() != null))) {
			client.addErrorMessage("[FAIL]: importing model: unable to get active shell!");
			if (PlatformUI.getWorkbench() == null) {
				client.addErrorMessage("reason: PlatformUI.getWorkbench() is null!");
				return;
			}
			if (PlatformUI.getWorkbench().getDisplay() == null) {
				client.addErrorMessage("reason: PlatformUI.getWorkbench().getDisplay() is null!");
				return;
			}
			return;
		}
		PlatformUI.getWorkbench().getDisplay().getActiveShell();
		Shell activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		FileDialog getModelFile = new FileDialog(activeShell);
		ModelFile = getModelFile.open();
		if (ModelFile == null) {
			client.addErrorMessage("[FAIL]: importing model: please select a vallid model file!");
			ModelFile = null;
			return;
		}
		int pos = ModelFile.lastIndexOf(File.separator);
		if (pos == -1) pos = 0; else pos += 1;
		ModelName = ModelFile.substring(pos, ModelFile.length());
	}

	@Override
	public IStatus performSingleTask(IProgressMonitor monitor) {

		// check if model file is selected
		if (ModelName == null || ModelFile == null) {
			return Status.CANCEL_STATUS;
		}

		// start task
		IStatus status = Status.OK_STATUS;
		client.beginTask("importing model " + ModelFile, 10);

		client.addLogMessage("importing model " + ModelFile + "... please wait for the task to be finished.");
		// initialize project with a model
		if (client.initProject(ModelName, client.getUserId(), ModelFile)) {
			client.addLogMessage("[PASS]: importing model " + ModelFile);
		} else {
			client.addErrorMessage("[FAIL]: importing model " + ModelFile);
		}

		// cleanup
		client.setSubTaskName("finishing task");
		client.addCompletedTaskItems(1);
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return status;
	}
}
