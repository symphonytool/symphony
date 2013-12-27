package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtPopupMenuAction extends AbstractHandler  {

	protected RttMbtClient client = null;

	// the project of the selected item.
	// properties that are defined for a project will override
	// globally defined preferences.
	protected IProject project;

	// the name of the selected object without oath information
	protected String selectedObjectName;

	// the absolute path to the selected object in the local file system
	protected String selectedObjectFilesystemPath;

	// the absolute path to the selected object in the current workspace
	protected String selectedObjectWorkspacePath;

	// the name of the selected project in the file system
	protected String selectedObjectFilesystemProjectName;

	// the name of the project of the selected object in the current workspace
	protected String selectedObjectWorkspaceProjectName;

	// the path to the selected project in the file system (including the project)
	protected String selectedObjectFilesystemProjectPath;

	// the relative path to the project of the selected object in the current workspace
	protected String selectedObjectWorkspaceProjectPrefix;

	// flags whether a folder of a file is selected
	protected Boolean isFolderSelected = false;
	protected Boolean isFileSelected = false;

	// constructor
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
		
		// get the different path informations
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		String selectedObjectWorkspaceProjectPath = null;
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				project = folder.getProject();
				selectedObjectName = folder.getName();
				selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(folder.getLocationURI());
				selectedObjectWorkspacePath = folder.getFullPath().toString();
				selectedObjectWorkspaceProjectName = folder.getProject().getName();
				selectedObjectWorkspaceProjectPath = folder.getProject().getFullPath().toString();
				isFolderSelected = true;
			} else if ((treeSelection.getFirstElement() != null) &&
				       (treeSelection.getFirstElement() instanceof IFile)) {
				IFile file = (IFile)treeSelection.getFirstElement();
				project = file.getProject();
				selectedObjectName = file.getName();
				selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(file.getLocationURI());
				selectedObjectWorkspacePath = file.getFullPath().toString();
				selectedObjectWorkspaceProjectName = file.getProject().getName();
				selectedObjectWorkspaceProjectPath = file.getProject().getFullPath().toString();
				isFileSelected = true;
			} else if ((treeSelection.getFirstElement() != null) &&
					   (treeSelection.getFirstElement() instanceof IProject)) {
				project = (IProject)treeSelection.getFirstElement();
				selectedObjectName = project.getName();
				selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
				selectedObjectWorkspacePath = project.getFullPath().toString();
				selectedObjectWorkspaceProjectName = project.getName();
				selectedObjectWorkspaceProjectPath = project.getFullPath().toString();
				isFolderSelected = true;
			}
		}

		// check for null pointers
		if ((project == null) ||
			(selectedObjectName == null) ||
			(selectedObjectFilesystemPath == null) ||
			(selectedObjectWorkspacePath == null) ||
			(selectedObjectWorkspaceProjectName == null) ||
			(selectedObjectWorkspaceProjectPath == null)) {
			client.addErrorMessage("*** error: retrieving information about the selected object failed!");
			return false;
		}

		// calculate selectedObjectWorkspaceProjectName and selectedObjectWorkspaceProjectPrefix
		// from selectedObjectWorkspacePath and selectedObjectFilesystemPath
		// iterate from the last pat item to the first and check for RT-Tester project criteria
		String filesystempath = selectedObjectFilesystemPath;
		String workspacepath = selectedObjectWorkspacePath;
		int fpos = filesystempath.lastIndexOf(File.separator);
		int wpos = workspacepath.lastIndexOf(File.separator);
		while ((fpos != -1) && (wpos != -1)) {

			// check that the path does not end with '/'
			if ((filesystempath.length() > fpos + 1) && (workspacepath.length() > wpos + 1)) {
				// get last item of path
				String fitem = filesystempath.substring(fpos + 1);
				String witem = workspacepath.substring(wpos + 1);
				// the first RT-Tester project in the path must be
				// the project of the selected item
				if (RttMbtClient.isRttProject(filesystempath)) {
					selectedObjectFilesystemProjectName = fitem;
					selectedObjectFilesystemProjectPath = filesystempath;
					selectedObjectWorkspaceProjectName = witem;
					selectedObjectWorkspaceProjectPath = workspacepath;
					break;
				}
				// if the items do not match, the file system item
				// must be the file system project name
				if (fitem.compareTo(witem) != 0) {
					System.out.println(fitem + "and" + witem + " differ.");
					selectedObjectFilesystemProjectName = fitem;
					selectedObjectFilesystemProjectPath = filesystempath;
					selectedObjectWorkspaceProjectName = witem;
					selectedObjectWorkspaceProjectPath = workspacepath;
					break;
				}
			}

			// prepare next loop
			filesystempath = filesystempath.substring(0, fpos);
			workspacepath = workspacepath.substring(0, wpos);
			fpos = filesystempath.lastIndexOf(File.separator);
			wpos = workspacepath.lastIndexOf(File.separator);
		}

		// remove selectedObjectWorkspaceProjectName from selectedObjectWorkspaceProjectPrefix
		int pos = selectedObjectWorkspaceProjectPath.indexOf(selectedObjectWorkspaceProjectName);
		if (pos == -1) {
			System.err.println("*** error: unable to find workspace project " +
		                       selectedObjectWorkspaceProjectName + " in workspace project path " +
		                       selectedObjectWorkspaceProjectPath);
		}
		selectedObjectWorkspaceProjectPrefix = selectedObjectWorkspaceProjectPath.substring(0, pos - 1);
		if (selectedObjectWorkspaceProjectPrefix.length() == 0) {
			selectedObjectWorkspaceProjectPrefix = null;
		}

		// remove leading file separator characters from workspace project prefix
		if ((selectedObjectWorkspaceProjectPrefix != null) && (selectedObjectWorkspaceProjectPrefix.startsWith(File.separator))) {
			selectedObjectWorkspaceProjectPrefix = selectedObjectWorkspaceProjectPrefix.substring(1);
		}

		return true;
	}

	// initialize the client according to the selection
	public Boolean initClient() {
		Boolean success = true;
		
		// set RT-Tester project name (folder in the file system)
		client.setRttProjectName(selectedObjectFilesystemProjectName);

		// set the path to the RT-Tester project in the filesystem
		client.setRttProjectPath(selectedObjectFilesystemProjectPath);

		// set eclipse project name in the current workspace that contains the RT-Tester project
		client.setWorkspaceProjectName(selectedObjectWorkspaceProjectName);

		// set the prefix of the RT-Tester project within the current workspace.
		client.setWorkspaceProjectPrefix(selectedObjectWorkspaceProjectPrefix);

		// set the path in the local file system to the current eclipse workspace.
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String workspacePath = workspace.getRoot().getLocation().toFile().getAbsolutePath();
		client.setWorkspacePath(workspacePath);

		// set console name
		client.setConsoleName(selectedObjectWorkspaceProjectName);

		// set project specific properties
		String value = RttMbtProjectPropertiesPage.getPropertyValue(project, "RttMbtrttProjectDatabase");
		if (value != null) {
			client.setProjectDatabaseName(value);
		}
		value = RttMbtProjectPropertiesPage.getPropertyValue(project, "RttMbtRttTprocPrefix");
		if ((value != null) && (value.length() > 0)) {
			client.setRttMbtTestProcFolderName(value);
		} else {
			client.setRttMbtTestProcFolderName(Activator.getPreferenceValue("RttMbtRttTprocPrefix"));
		}
		value = RttMbtProjectPropertiesPage.getPropertyValue(project, "RttMbtTProcGenCtx");
		if ((value != null) && (value.length() > 0)) {
			client.setRttMbtTProcGenCtxFolderName(value);
		} else {
			client.setRttMbtTProcGenCtxFolderName(Activator.getPreferenceValue("RttMbtTProcGenCtx"));
		}

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
		if (selectedObjectWorkspacePath == null) {
			return false;
		}
		int idx = selectedObjectWorkspacePath.lastIndexOf(selectedObjectName);
		if (idx < 1) { return false; }
		String path = selectedObjectWorkspacePath.substring(0, idx - 1);
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
		if (selectedObjectWorkspacePath == null) {
			return false;
		}
		int idx = selectedObjectWorkspacePath.lastIndexOf(selectedObjectName);
		if (idx < 1) { return false; }
		String path = selectedObjectWorkspacePath.substring(0, idx - 1);
		String RttTestProc = client.getRttMbtTestProcFolderName();
		idx = path.lastIndexOf(RttTestProc);
		if (idx < 1) { return false; }
		if ((path.substring(idx - 1, idx).compareTo("/") != 0) &&
			(path.substring(idx - 1, idx).compareTo(File.separator) != 0)) { return false; }
		String objectContainer = path.substring(idx, path.length());
		return objectContainer.compareTo(RttTestProc) == 0;
	}

	public void getRttTestProcPathFromTProcGenCtxPath() {
		if (selectedObjectWorkspacePath == null) {
			return;
		}
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		int idx = selectedObjectWorkspacePath.lastIndexOf(TProcGenCtx);
		if (idx < 1) { return; }
		// adjust selectedObjectWorkspacePath
		String path = selectedObjectWorkspacePath.substring(0, idx);
		String RttTestProc = client.getRttMbtTestProcFolderName();
		selectedObjectWorkspacePath = path + RttTestProc + File.separator + selectedObjectName;
		// adjust selectedObjectFilesystemPath
		idx = selectedObjectFilesystemPath.lastIndexOf(TProcGenCtx);
		if (idx < 1) { return; }
		path = selectedObjectFilesystemPath.substring(0, idx);
		RttTestProc = client.getRttMbtTestProcFolderName();
		selectedObjectFilesystemPath = path + RttTestProc + File.separator + selectedObjectName;
	}

	public void getTProcGenCtxPathFromRttTestProcPath() {
		if (selectedObjectWorkspacePath == null) {
			return;
		}
		String RttTestProc = client.getRttMbtTestProcFolderName();
		int idx = selectedObjectWorkspacePath.lastIndexOf(RttTestProc);
		if (idx < 1) { return; }
		// adjust selectedObjectWorkspacePath
		String path = selectedObjectWorkspacePath.substring(0, idx);
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		selectedObjectWorkspacePath = path + TProcGenCtx + File.separator + selectedObjectName;
		idx = selectedObjectFilesystemPath.lastIndexOf(RttTestProc);
		if (idx < 1) { return; }
		// adjust selectedObjectFilesystemPath
		path = selectedObjectFilesystemPath.substring(0, idx);
		TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		selectedObjectFilesystemPath = path + TProcGenCtx + File.separator + selectedObjectName;
	}
}
