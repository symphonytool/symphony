package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtPopupMenuAction extends AbstractHandler  {

	protected RttMbtClient client = null;
	protected String selectedObject;
	protected String selectedObjectPath;
	protected Boolean isFolderSelected = false;
	protected Boolean isFileSelected = false;

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
				isFolderSelected = true;
			} else if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFile)) {
				IFile file = (IFile)treeSelection.getFirstElement();
				selectedObject = file.getName();
				selectedObjectPath = file.getFullPath().toString();
				isFileSelected = true;
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
			client.addErrorMessage("[FAIL]: unable to initialize client (no path to the selected object provided)!");
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
			cmlProject = current.substring(0, pos);
		} else {
			pos = current.indexOf('/');
			if (pos == -1) {
				client.addErrorMessage("[FAIL]: no RTT-MBT component selected");
				return false;
			} else {
				cmlProject = current.substring(0, pos);
			}
		}
		// calculate RTT-MBT project name from selected folder
		current = current.substring(pos + 1, current.length());
		pos = current.indexOf(File.separator);
		if (pos == -1) pos = current.indexOf('/');
		if (pos == -1) pos = current.length();
		String rttProject = current.substring(0,pos);

		// set CML project name
		client.setCmlProject(cmlProject);

		// set RTT-MBT project name
		client.setProjectName(rttProject);

		// set console name
		client.setConsoleName(selectedObject);
		
		// test connection to rtt-mbt-tms server
		if (client.testConenction()) {
			if (client.getVerboseLogging()) {
				client.addLogMessage("[PASS]: test RTT-MBT server connection");
			}
		} else {
			client.addErrorMessage("[FAIL]: test RTT-MBT server connection");
			return false;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			if (client.getVerboseLogging()) {
				client.addLogMessage("[PASS]: begin RTT-MBT session");
			}
		} else {
			client.addErrorMessage("[FAIL]: begin RTT-MBT session");
			return false;
		}
		
		return success;
	}
	
	public static boolean isRttMbtProject() {
		return false;
	}
	
	// check if the selected item is a test procedure generation context
	public Boolean isTProcGenCtxSelected() {
		if (selectedObjectPath == null) {
			return false;
		}
		int idx = selectedObjectPath.lastIndexOf(selectedObject);
		if (idx < 1) { return false; }
		String path = selectedObjectPath.substring(0, idx - 1);
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		idx = path.lastIndexOf(TProcGenCtx);
		if (idx < 1) { return false; }
		if ((path.substring(idx - 1, idx).compareTo("/") != 0) &&
			(path.substring(idx - 1, idx).compareTo(File.separator) != 0)) { return false; }
		String objectContainer = path.substring(idx, path.length());
		return objectContainer.compareTo(TProcGenCtx) == 0;
	}
	
	// check if the selected item is an rtt-test-procedure
	public Boolean isRttTestProcSelected() {
		if (selectedObjectPath == null) {
			return false;
		}
		int idx = selectedObjectPath.lastIndexOf(selectedObject);
		if (idx < 1) { return false; }
		String path = selectedObjectPath.substring(0, idx - 1);
		String RttTestProc = client.getRttMbtTestProcFolderName();
		idx = path.lastIndexOf(RttTestProc);
		if (idx < 1) { return false; }
		if ((path.substring(idx - 1, idx).compareTo("/") != 0) &&
			(path.substring(idx - 1, idx).compareTo(File.separator) != 0)) { return false; }
		String objectContainer = path.substring(idx, path.length());
		return objectContainer.compareTo(RttTestProc) == 0;
	}

	public void getRttTestProcPathFromTProcGenCtxPath() {
		if (selectedObjectPath == null) {
			return;
		}
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		int idx = selectedObjectPath.lastIndexOf(TProcGenCtx);
		if (idx < 1) { return; }
		String path = selectedObjectPath.substring(0, idx);
		String RttTestProc = client.getRttMbtTestProcFolderName();
		selectedObjectPath = path + RttTestProc + File.separator + selectedObject;
	}

	public void getTProcGenCtxPathFromRttTestProcPath() {
		if (selectedObjectPath == null) {
			return;
		}
		String RttTestProc = client.getRttMbtTestProcFolderName();
		int idx = selectedObjectPath.lastIndexOf(RttTestProc);
		if (idx < 1) { return; }
		String path = selectedObjectPath.substring(0, idx);
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		selectedObjectPath = path + TProcGenCtx + File.separator + selectedObject;
	}
}
