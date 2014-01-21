package eu.compassresearch.ide.rttmbt;

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
		client.setCurrentTaskCanceled(true);
		
		// abort job if a valid job ID has been received
		if (jobIdString != null && jobIdString.compareTo("-1") != 0) {
			if (client.abortCommand(jobIdString)) {
				client.addLogMessage("[PASS]: aborting command with id " + jobIdString + ". Termination request has been sent to local tasks.");
			} else {
				client.addErrorMessage("[FAIL]: aborting command with id " + jobIdString + ". Termination request has been sent to local tasks.");
			}
		} else {
			client.addLogMessage("[PASS]: aborting command: no active server task running. Termination request has been sent to local tasks.");
		}

		return null;
	}
}
