package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtGenerateSimulation extends AbstractHandler  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		String absTestProc = null;
		String absTestProcPath = null;
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				absTestProc = folder.getName();
				absTestProcPath = folder.getFullPath().toString();
			}
		}
		if (absTestProc == null) {
			return null;
		}

		// get RTT-MBT TMS client
    	RttMbtClient client = Activator.getClient();

    	// set workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());

		// calculate CML project name from selected folder
		String current = absTestProcPath.substring(1,absTestProcPath.length());
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
			return null;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			System.out.println("[PASS]: begin RTT-MBT session");
		} else {
			System.err.println("[FAIL]: begin RTT-MBT session");
			return null;
		}

		// generate simulation
		// - generate-simulation-command
		if (client.generateSimulation(absTestProc)) {
			System.out.println("[PASS]: generate simulation");
		} else {
			System.err.println("[FAIL]: generate simulation");
			return null;
		}
		
		return null;
	}
}
