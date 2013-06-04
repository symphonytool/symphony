package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RttMbtAbort extends RttMbtPopupMenuAction {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get RttMbtClient for this action
		if (client == null) {
			return null;
		}

		// get job identification number
		String jobIdString = client.getCurrentJobId();
		
		// abort job
		if (client.abortCommand(jobIdString)) {
			client.addLogMessage("[PASS]: aborting command with id " + jobIdString);
		} else {
			client.addErrorMessage("[FAIL]: aborting command with id " + jobIdString);			
		}

		return null;
	}
}
