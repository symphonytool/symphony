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

public class RttMbtPopupMenuAction extends AbstractHandler  {

	protected RttMbtClient client = null;
	protected String selectedObject;
	protected String selectedObjectPath;

	public RttMbtPopupMenuAction() {
		// get RTT-MBT TMS client
    	client = Activator.getClient();
	}
	
	// this must be implemented by all clients
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}
	
	// get selected object (and path)
	public Boolean getSelectedObject(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				selectedObject = folder.getName();
				selectedObjectPath = folder.getFullPath().toString();
			}
		}
		if ((selectedObject == null) || (selectedObjectPath == null)) {
			return false;
		}
		
		return true;
	}

	// initialize the client according to the selection
	public Boolean initClient(String selectionFullPath) {
		Boolean success = true;
		
		if (selectionFullPath == null) {
			client.addErrorMessage("[FAIL]: unable to initialize client (no path to the selected object provided)!\n");
			return false;
		}
		
    	// set workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());

		// calculate CML project name from selected folder
		String current = selectionFullPath.substring(1, selectionFullPath.length());
		int pos = current.indexOf(File.separator);
		String cmlProject;
		if (pos > -1) {
			cmlProject = current.substring(0,pos);
		} else {
			pos = current.indexOf('/');
			if (pos == -1) {
				client.addErrorMessage("[FAIL]: no RTT-MBT component selected\n");
				return false;
			} else {
				cmlProject = current.substring(0,pos);
			}
		}
		// calculate RTT-MBT project name from selected folder
		current = current.substring(pos + 1, current.length());
		pos = current.indexOf(File.separator);
		if (pos == -1) pos = current.indexOf('/');
		if (pos == -1) pos = current.length();
		String rttProject = current.substring(0,pos);

		// set CML project name
		client.setCmlProject(File.separator + cmlProject);

		// set RTT-MBT project name
		client.setProjectName(rttProject);

		// set console name
		client.setConsoleName(selectedObject);
		
		// test connection to rtt-mbt-tms server
		if (client.testConenction()) {
			client.addLogMessage("[PASS]: test RTT-MBT server connection\n");
		} else {
			client.addErrorMessage("[FAIL]: test RTT-MBT server connection\n");
			return false;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			client.addLogMessage("[PASS]: begin RTT-MBT session\n");
		} else {
			client.addErrorMessage("[FAIL]: begin RTT-MBT session\n");
			return false;
		}
		
		return success;
	}
	
	public static boolean isRttMbtProject() {
		return false;
	}
}
