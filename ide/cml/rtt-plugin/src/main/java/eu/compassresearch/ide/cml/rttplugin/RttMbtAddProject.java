package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtAddProject extends RttMbtPopupMenuAction  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an RTT-MBT component!\n");
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			return null;
		}

		// check if the selected item is a project
		File item = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!item.exists()) {
			client.addErrorMessage("file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		if (!item.isDirectory()) {
			client.addErrorMessage("The selected item '" + selectedObject + "' is not a RTT-MBT component directory!");
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
				client.addErrorMessage("The selected item '" + selectedObject + "' is not a RTT-MBT component directory!");
				if ((!inc.exists()) || (!inc.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObject + File.separator + "inc' does not exist or is not a directory!");					
				}
				if ((!scripts.exists()) || (!scripts.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObject + File.separator + "scripts' does not exist or is not a directory!");					
				}
				if ((!specs.exists()) || (!specs.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObject + File.separator + "specs' does not exist or is not a directory!");					
				}
				if ((!stubs.exists()) || (!stubs.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObject + File.separator + "stubs' does not exist or is not a directory!");					
				}
				if ((!TMPL.exists()) || (!TMPL.isDirectory())) {
					client.addErrorMessage("Required sub directory '" + selectedObject + File.separator + "TMPL' does not exist or is not a directory!");					
				}
				client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
				return null;
			}
		}

		// check/create work area on the server

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
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "conf'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "conf", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "inc'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "inc", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "model'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "model", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "scripts'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "scripts", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "specs'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "specs", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "stubs'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "stubs", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 0);
		client.addLogMessage("uploading '" + selectedObject + File.separator + "TMPL'...");
		ret = client.uploadDirectory(item.getAbsolutePath() + File.separator + "TMPL", true);
		success = success && ret;
		if (ret) {
			client.addLogMessage("[PASS]\n");
		} else {
			client.addLogMessage("[FAIL]\n");
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		if (success) {
			client.addLogMessage("[PASS]: Add RTT-MBT component '" + selectedObject + "'\n");
		} else {
			client.addLogMessage("[FAIL]: Add RTT-MBT component '" + selectedObject + "'\n");
		}
		return null;
	}
}
