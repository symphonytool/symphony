package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import eu.compassResearch.rttMbtTmsClientApi.IRttMbtProgressBar;

public class RttMbtDocRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		client.setProgress(IRttMbtProgressBar.Tasks.ALL, 0);
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an abstract test procedure!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			client.addErrorMessage("[FAIL]: generate test procedure documentation: init of RTT-MBT client failed!\n");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// clean test procedure
		if (client.docTestProcedure(selectedObject)) {
			client.addLogMessage("[PASS]: generate test procedure documentation");
		} else {
			client.addErrorMessage("[FAIL]: generate test procedure documentation");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
		return null;
	}
}
