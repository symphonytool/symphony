package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtAddProject extends RttMbtPopupMenuAction  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: Please select an RTT-MBT component!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient()) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: init of RTT-MBT client failed!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// check if the selected item is a project
		File item = new File(selectedObjectFilesystemPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		if (!item.isDirectory()) {
			client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: the selected item '" + selectedObjectName + "' is not a directory!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		} else {
			File inc = new File(item, "inc");
			File scripts = new File(item, "scripts");
			File specs = new File(item, "specs");
			File stubs = new File(item, "stubs");
			File TMPL = new File(item, "TMPL");
			if (((!inc.exists()) || (!inc.isDirectory())) ||
				((!scripts.exists()) || (!scripts.isDirectory())) ||
				((!specs.exists()) || (!specs.isDirectory())) ||
				((!stubs.exists()) || (!stubs.isDirectory())) ||
				((!TMPL.exists()) || (!TMPL.isDirectory()))) {
				if ((!inc.exists()) || (!inc.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObjectName + File.separator + "inc' does not exist or is not a directory!");					
				}
				if ((!scripts.exists()) || (!scripts.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObjectName + File.separator + "scripts' does not exist or is not a directory!");					
				}
				if ((!specs.exists()) || (!specs.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObjectName + File.separator + "specs' does not exist or is not a directory!");					
				}
				if ((!stubs.exists()) || (!stubs.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObjectName + File.separator + "stubs' does not exist or is not a directory!");					
				}
				if ((!TMPL.exists()) || (!TMPL.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObjectName + File.separator + "TMPL' does not exist or is not a directory!");					
				}
				client.addErrorMessage("[FAIL]: Add an existing RTT-MBT Component to the RTT-MBT Server: the selected item '" + selectedObjectName + "' is not a RTT-MBT component directory!");
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return null;
			}
		}

		final String itempath = item.getAbsolutePath();
		Job job = new Job("Add Component to Server") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// create/update work area on the server

				// push global files/directories:
				// - conf
				// - inc
				// - model
				// - scripts
				// - specs
				// - stubs
				// - TMPL
				Boolean ret;
				Boolean success = true;
				
				// conf
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "conf'...");
				ret = client.uploadDirectory(itempath + File.separator + "conf", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// inc
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "inc'...");
				ret = client.uploadDirectory(itempath + File.separator + "inc", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// model
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "model'...");
				ret = client.uploadDirectory(itempath + File.separator + "model", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// scripts
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "scripts'...");
				ret = client.uploadDirectory(itempath + File.separator + "scripts", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// specs
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "specs'...");
				ret = client.uploadDirectory(itempath + File.separator + "specs", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// stubs
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "stubs'...");
				ret = client.uploadDirectory(itempath + File.separator + "stubs", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}
				
				// TMPL
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
				client.addLogMessage("uploading '" + selectedObjectName + File.separator + "TMPL'...");
				ret = client.uploadDirectory(itempath + File.separator + "TMPL", true);
				success = success && ret;
				if (ret) {
					client.addLogMessage("[PASS]");
				} else {
					client.addLogMessage("[FAIL]");
				}

				// final verdict of action
				if (success) {
					client.addLogMessage("[PASS]: Add RTT-MBT component '" + selectedObjectName + "'");
				} else {
					client.addLogMessage("[FAIL]: Add RTT-MBT component '" + selectedObjectName + "'");
				}
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
