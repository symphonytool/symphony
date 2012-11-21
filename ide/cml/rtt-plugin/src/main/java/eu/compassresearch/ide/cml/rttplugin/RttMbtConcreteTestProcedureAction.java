package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtConcreteTestProcedureAction extends AbstractHandler {
	protected RttMbtClient client = null;
	
	// this must be implemented by all clients
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}

	// RTT_Testprocedure utility functions

	// check if the selected item is an rt-tester test procedure
	public Boolean isRttTestProcedureSelected() {
		return false;
	}
	
	// initialize the client according to the selection
	public Boolean initClient(String selectionFullPath) {
		Boolean success = true;
		
		if (selectionFullPath == null) {
			return false;
		}
		
		// get RTT-MBT TMS client
    	client = Activator.getClient();

    	// set workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());

		// calculate CML project name from selected folder
		String current = selectionFullPath.substring(1, selectionFullPath.length());
		String cmlProject = current.substring(0,current.indexOf(File.separator));
		// calculate RTT-MBT project name from selected folder
		int pos = current.indexOf(File.separator);
		current = current.substring(pos + 1, current.length());
		String rttProject = current.substring(0,current.indexOf(File.separator));

		// set CML project name
		client.setCmlProject(File.separator + cmlProject);

		// set RTT-MBT project name
		client.setProjectName(rttProject);
		
		// test connection to rtt-mbt-tms server
		if (client.testConenction()) {
			System.out.println("[PASS]: test RTT-MBT server connection");
		} else {
			System.err.println("[FAIL]: test RTT-MBT server connection");
			return false;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			System.out.println("[PASS]: begin RTT-MBT session");
		} else {
			System.err.println("[FAIL]: begin RTT-MBT session");
			return false;
		}
		
		return success;
	}
}
