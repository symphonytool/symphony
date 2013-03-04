package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtSendFile extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: Please select a file or directory!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: init of RTT-MBT client failed!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// perform action using client
		Boolean success;
		File item = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!item.exists()) {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		if (item.isDirectory()) {
			success = client.uploadDirectory(item.getAbsolutePath(), true);
		} else if (item.isFile()) {
			success = client.uploadFile(item.getAbsolutePath());			
		} else {
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area: selection is not a file or directory!");			
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		if (success) {
			client.addLogMessage("[PASS]: Add Directory/File to RTT-MBT Server Work Area!");			
		} else {			
			client.addErrorMessage("[FAIL]: Add Directory/File to RTT-MBT Server Work Area!");			
		}
		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}