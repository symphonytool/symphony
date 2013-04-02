package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtAbstractTestProcedureAction extends RttMbtPopupMenuAction {

	// this must be implemented by all clients
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}

	// check if the selected item is an rt-tester test procedure
	public Boolean isTestProcedureSelected() {
		return false;
	}
	
}
