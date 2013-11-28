package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;


public class RttMbtImportModel extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an RTT-MBT component!");
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient()) {
			return null;
		}

		// get model file name
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FileDialog getModelFile = new FileDialog(activeShell);
		final String ModelFile = getModelFile.open();
		if (ModelFile == null) {
			return null;
		}
		client.addLogMessage("model file '" + ModelFile + "'");
		int pos = ModelFile.lastIndexOf(File.separator);
		if (pos == -1) pos = 0; else pos += 1;
		final String ModelName = ModelFile.substring(pos, ModelFile.length());
		client.addLogMessage("model name '" + ModelName + "'");

		Job job = new Job("Import Model") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				client.addLogMessage("importing model " + ModelFile + "... please wait for the task to be finished.");
				// initialize project with a model
				if (client.initProject(ModelName, client.getUserId(), ModelFile)) {
					client.addLogMessage("[PASS]: init project");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				} else {
					client.addErrorMessage("[FAIL]: init project");
					client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
				
		return null;
	}
}
