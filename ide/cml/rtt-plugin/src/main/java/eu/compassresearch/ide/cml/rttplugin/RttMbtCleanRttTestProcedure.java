package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtCleanRttTestProcedure extends RttMbtConcreteTestProcedureAction {

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

		// clean test procedure
		if (client.cleanTestProcedure(selectedObject)) {
			System.out.println("[PASS]: clean test");
		} else {
			System.err.println("[FAIL]: clean test");
			return null;
		}

		return null;
	}
}
