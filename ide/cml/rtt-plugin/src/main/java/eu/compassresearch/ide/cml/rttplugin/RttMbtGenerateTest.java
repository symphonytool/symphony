package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtGenerateTest extends RttMbtAbstractTestProcedureAction  {

	@Override
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

		// generate concrete test procedure
		if (client.generateTestProcedure(selectedObject)) {
			client.addLogMessage("[PASS]: generate test\n");
		} else {
			client.addErrorMessage("[FAIL]: generate test\n");
			return null;
		}

		return null;
	}
}
