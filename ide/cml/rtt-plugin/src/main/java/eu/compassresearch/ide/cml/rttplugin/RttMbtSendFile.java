package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtSendFile extends RttMbtPopupMenuAction {

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

		// init progress bar
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		
		// perform action using client
		File item = new File(client.getCmlWorkspace() + selectedObjectPath);
		if (!item.exists()) {
			client.addErrorMessage("file or directory '" + item.getAbsolutePath() + "' does not exist!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}
		
		if (item.isDirectory()) {
			client.uploadDirectory(item.getAbsolutePath(), true);
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else if (item.isFile()) {
			client.uploadFile(item.getAbsolutePath());			
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		} else {
			client.addErrorMessage("selection is not a file or directory!");			
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		return null;
	}
}
