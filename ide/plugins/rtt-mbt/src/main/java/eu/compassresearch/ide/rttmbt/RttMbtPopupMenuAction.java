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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.rttMbtTmsClientApi.IRttMbtProgressBar;
import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtPopupMenuAction extends AbstractHandler  {

	protected RttMbtClient client = null;

	// storage for the tree selection that is used as argument
	// for the pop up menu action
	TreeSelection treeSelectionArgument;

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

	// flag if RTT-MBT server is needed for this task
	protected Boolean isServerConnectionNeeded = true;

	// name of the current task
	protected String taskname;
	
	// constructor
	public RttMbtPopupMenuAction() {
		// get RTT-MBT TMS client
    	client = Activator.getClient();
    	taskname = "generic popup memnu action";
	}

	public void setTaskName(String name) {
		taskname = name;
	}

	public String getTaskName() {
		return taskname;
	}

	/**
	 * This function can be used by sub classes to implement
	 *  behaviour that has to be executed before the job is started
	 */
	public void performJobPreprocessing() {
		return;
	}

	// the generic evaluation of the selected item(s) and job creation
	public Object execute(ExecutionEvent event) throws ExecutionException {

		performJobPreprocessing();

		// get tree selection (can be more than one element)
		if (!getTreeSelection(event)) {
			client.addErrorMessage("[FAIL]: invalid selection of objects in the project explorer!");
			client.setProgress(IRttMbtProgressBar.Tasks.Global, 100);
			return null;
		}

		// start one job for this action that performs the action for
		// all selected elements. This way, they cannot be parallelized
		Job job = new Job(getTaskName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				// perform action on each element of the tree selection
				Object elements[] = treeSelectionArgument.toArray();
				IStatus overallStatus = Status.OK_STATUS;
				Activator.setMonitor(monitor);
				for (int idx = 0; idx < treeSelectionArgument.size(); idx++) {
					// get current element
					Object element = elements[idx];
					if (element == null) continue;

					// retrieve path variables from this element
					if ((initPathVariables(element)) && (initClient())) {

						IStatus status = performSingleTask(monitor);
						if (status != Status.OK_STATUS) {
							overallStatus = status;
						}

					} else {
						client.addErrorMessage("[FAIL]: " + getTaskName() + ": invalid selected object");
					}

					// check if the task has been cancelled
					if (client.isCurrentTaskCanceled()) {
						break;
					}
				}
				Activator.setMonitor(null);
				return overallStatus;
			}
		};
		job.schedule();

		return null;
	}

	// this must be implemented by all clients
	public IStatus performSingleTask(IProgressMonitor monitor) {
		client.addLogMessage("RttMbtPopupMenuAction::performSingleTask");
		return Status.OK_STATUS;
	}

	public Boolean getTreeSelection(ExecutionEvent event) {
		
		// get the Selection from the event
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		// if the selection is valis, store the selection and return true
		if (selection instanceof TreeSelection) {
			treeSelectionArgument = (TreeSelection) selection;
			return true;
		}

		// if the selection from the event is invalid, use the selection stored in Activator
		if ((!(selection instanceof TreeSelection)) ||
			(!((((TreeSelection)selection).getFirstElement() instanceof IFolder) &&
			   (((TreeSelection)selection).getFirstElement() instanceof IFile) &&
			   (((TreeSelection)selection).getFirstElement() instanceof IProject)))) {
			if (Activator.getCurrentProjectExplorerItem() != null) {
				treeSelectionArgument = Activator.getCurrentProjectExplorerItem();
				return true;
			} else {
				treeSelectionArgument = null;
				return false;
			}
		}

		// if no vallis selction can be retrieve, return false
		treeSelectionArgument = null;
		return false;
	}

	public Boolean initPathVariables(Object element) {

		// retrieve path information from selection element
		String selectedObjectWorkspaceProjectPath = null;
		isFileSelected = false;
		isFolderSelected = false;
		if (element instanceof IFolder) {
			IFolder folder = (IFolder)element;
			project = folder.getProject();
			selectedObjectName = folder.getName();
			selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(folder.getLocationURI());
			selectedObjectWorkspacePath = folder.getFullPath().toString();
			isFolderSelected = true;
		} else if (element instanceof IFile) {
			IFile file = (IFile)element;
			project = file.getProject();
			selectedObjectName = file.getName();
			selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(file.getLocationURI());
			selectedObjectWorkspacePath = file.getFullPath().toString();
			isFileSelected = true;
		} else if (element instanceof IProject) {
			project = (IProject)element;
			selectedObjectName = project.getName();
			selectedObjectFilesystemPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
			selectedObjectWorkspacePath = project.getFullPath().toString();
			isFolderSelected = true;
		} else {
			project = null;
			selectedObjectName = null;
			selectedObjectFilesystemPath = null;
			selectedObjectWorkspacePath = null;
			selectedObjectWorkspaceProjectName = null;
			selectedObjectWorkspaceProjectPath = null;
			client.addErrorMessage("retrieving information about the selected object failed!");
			return false;
		}
		// retrieve project information
		selectedObjectWorkspaceProjectName = project.getName();
		selectedObjectWorkspaceProjectPath = project.getFullPath().toString();

		// check for null pointers
		if ((project == null) ||
			(selectedObjectName == null) ||
			(selectedObjectFilesystemPath == null) ||
			(selectedObjectWorkspacePath == null) ||
			(selectedObjectWorkspaceProjectName == null) ||
			(selectedObjectWorkspaceProjectPath == null)) {
			client.addErrorMessage("retrieving information about the selected object failed!");
			return false;
		}

		// calculate selectedObjectWorkspaceProjectName and selectedObjectWorkspaceProjectPrefix
		// from selectedObjectWorkspacePath and selectedObjectFilesystemPath
		// iterate from the last pat item to the first and check for RT-Tester project criteria
		String filesystempath = selectedObjectFilesystemPath;
		String workspacepath = selectedObjectWorkspacePath;
		String fileSeparator = selectedObjectFilesystemPath.substring(0, 1);
		int fpos = filesystempath.lastIndexOf(fileSeparator);
		int wpos = workspacepath.lastIndexOf(fileSeparator);
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
			fpos = filesystempath.lastIndexOf(fileSeparator);
			wpos = workspacepath.lastIndexOf(fileSeparator);
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
		if ((selectedObjectWorkspaceProjectPrefix != null) && (selectedObjectWorkspaceProjectPrefix.startsWith(fileSeparator))) {
			selectedObjectWorkspaceProjectPrefix = selectedObjectWorkspaceProjectPrefix.substring(1);
		}

		// debug output
		/*
		client.addLogMessage("project: " + project.getName());
		client.addLogMessage("selectedObjectName: " + selectedObjectName);
		client.addLogMessage("selectedObjectFilesystemPath: " + selectedObjectFilesystemPath);
		client.addLogMessage("selectedObjectWorkspacePath: " + selectedObjectWorkspacePath);
		client.addLogMessage("selectedObjectFilesystemProjectName: " + selectedObjectFilesystemProjectName);
		client.addLogMessage("selectedObjectWorkspaceProjectName: " + selectedObjectWorkspaceProjectName);
		client.addLogMessage("selectedObjectFilesystemProjectPath: " + selectedObjectFilesystemProjectPath);
		client.addLogMessage("selectedObjectWorkspaceProjectPrefix: " + selectedObjectWorkspaceProjectPrefix);
		client.addLogMessage("isFolderSelected: " + isFolderSelected);
		client.addLogMessage("isFileSelected: " + isFileSelected);
		 */

		// indicate success
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
		value = RttMbtProjectPropertiesPage.getPropertyValue(project, "RttMbtSutMakeTool");
		if ((value != null) && (value.length() > 0)) {
			client.setMakeToolProperty(value);
		} else {
			client.setDefaultMakeToolProperty();
		}
		value = RttMbtProjectPropertiesPage.getPropertyValue(project, "RttMbtFileIgnorePattern");
		if ((value != null) && (value.length() > 0)) {
			client.setIgnorePatternProperty(value);
		} else {
			client.setDefaultIgnorePatternProperty();
		}

		// test connection to rtt-mbt-tms server
		if (isServerConnectionNeeded) {
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

	public Boolean hasTProcGenCtx() {
		if (selectedObjectFilesystemPath == null) {
			return false;
		}
		String RttTestProc = client.getRttMbtTestProcFolderName();
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		int idx = selectedObjectFilesystemPath.lastIndexOf(RttTestProc);
		if (idx < 1) { return false; }
		if ((selectedObjectFilesystemPath.substring(idx - 1, idx).compareTo("/") != 0) &&
			(selectedObjectFilesystemPath.substring(idx - 1, idx).compareTo(File.separator) != 0)) { return false; }
		String path = selectedObjectFilesystemPath.substring(0, idx) + TProcGenCtx + selectedObjectFilesystemPath.substring(idx + RttTestProc.length());
		System.out.println("genCtx: '" + path + "'");
		File genCtx = new File(path);
		return (genCtx.isDirectory());
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
