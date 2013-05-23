package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;


public class RttMbtImportModel extends RttMbtPopupMenuAction {

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

		// get model file name
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FileDialog getModelFile = new FileDialog(activeShell);
		String ModelFile = getModelFile.open();
		if (ModelFile == null) {
			return null;
		}
		client.addLogMessage("model file '" + ModelFile + "'\n");
		int pos = ModelFile.lastIndexOf(File.separator);
		if (pos == -1) pos = 0; else pos += 1;
		String ModelName = ModelFile.substring(pos, ModelFile.length());
		client.addLogMessage("model name '" + ModelName + "'\n");

		// initialize project with a model
		if (client.initProject(ModelName, client.getUserId(), ModelFile)) {
			client.addLogMessage("[PASS]: init project\n");
		} else {
			client.addErrorMessage("[FAIL]: init project\n");
			return null;
		}
				
		return null;
	}
}
