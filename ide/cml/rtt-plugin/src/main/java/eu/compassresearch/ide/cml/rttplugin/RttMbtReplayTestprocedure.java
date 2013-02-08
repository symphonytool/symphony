package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtReplayTestprocedure extends RttMbtConcreteTestProcedureAction {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		if (!getSelectedObject(event)) {
			client.addErrorMessage("[FAIL]: Please select an abstract test procedure!\n");
			return null;
		}

		// get RttMbtClient for this action
		if (!initClient(selectedObjectPath)) {
			return null;
		}

		// replay test procedure
		if (client.replayTestProcedure(selectedObject)) {
			System.out.println("[PASS]: reply test");
		} else {
			System.err.println("[FAIL]: reply test");
			return null;
		}

		return null;
	}
}
