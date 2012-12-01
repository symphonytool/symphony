package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtGenerateSimulation extends RttMbtAbstractTestProcedureAction  {

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

		// generate simulation
		// - generate-simulation-command
		if (client.generateSimulation(selectedObject)) {
			client.addLogMessage("[PASS]: generate simulation\n");
		} else {
			client.addErrorMessage("[FAIL]: generate simulation\n");
			return null;
		}
		
		return null;
	}
}
