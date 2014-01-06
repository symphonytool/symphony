package eu.compassresearch.ide.rttmbt;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.services.ISourceProviderService;

import eu.compassresearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtProjectExplorer extends org.eclipse.ui.navigator.CommonNavigator {

	protected String selectedObject = null;
	protected String selectedObjectPath = null;
	protected Boolean isFolderSelected = false;
	protected Boolean isFileSelected = false;
	protected RttMbtClient client = null;
	protected File workspaceDirectory = null;

	@Override
	public void init(IViewSite aSite, IMemento aMemento) throws PartInitException {
		// perform stuff from org.eclipse.ui.navigator.CommonNavigator
		super.init(aSite, aMemento);

		// get RTT-MBT client
		client = Activator.getClient();

		// set selection changed listener
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(commandStateListener);
	}

	private ISelectionListener commandStateListener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	        if (selection instanceof TreeSelection) {
	        	evaluteSelection((IStructuredSelection) selection);
	        }
		}
    };

    private void evaluteSelection(IStructuredSelection selection) {
	    // evaluate selection
		selectedObject = null;
		selectedObjectPath = null;
		isFolderSelected = false;
		isFileSelected = false;

    	// set workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspaceDirectory = workspace.getRoot().getLocation().toFile();

		IProject project = null;
		if (selection instanceof TreeSelection) {		    
			TreeSelection treeSelection = (TreeSelection)selection;
			if (treeSelection.size() == 0) {
				setAllKeysFlase();
				return;
			}
			if (treeSelection.size() > 1) {
				setAllKeysFlase();
				return;
			}
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				project = folder.getProject();
				selectedObject = folder.getName();
				selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(folder.getLocationURI());
				isFolderSelected = true;
			} else if ((treeSelection.getFirstElement() != null) &&
				       (treeSelection.getFirstElement() instanceof IFile)) {
				IFile file = (IFile)treeSelection.getFirstElement();
				project = file.getProject();
				selectedObject = file.getName();
				selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(file.getLocationURI());
				isFileSelected = true;
			} else if ((treeSelection.getFirstElement() != null) &&
				       (treeSelection.getFirstElement() instanceof IProject)) {
				project = (IProject)treeSelection.getFirstElement();
				selectedObject = project.getName();
				selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
				isFolderSelected = true;
			}
		}
		// check for invalid selection
		if ((project == null) ||
			(selectedObject == null) ||
			(selectedObjectPath == null)) {
			setAllKeysFlase();
			return;
		}

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

		// enable RTT-MBT actions
		if (isGenerationContextSelected()) {
			setService(RttMbtCommandState.keyIsGenerationContextTP,RttMbtCommandState.TRUE);
			setService(RttMbtCommandState.keyIsExecutionContextTP,RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
		} else if (isExecutionContextSelected()) {
			setService(RttMbtCommandState.keyIsExecutionContextTP,RttMbtCommandState.TRUE);
			setService(RttMbtCommandState.keyIsGenerationContextTP,RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
		} else if (isModelDumpSelected()) {
			setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.TRUE);
			setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
		} else if (isRttMbtProjectSelected()) {
			setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.TRUE);
			setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
		} else if (isMakefileSelected()) {
			setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.TRUE);
			setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
			setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
		} else {
			setAllKeysFlase();
		}
		return;
	}

    private void setAllKeysFlase() {
		setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
    }

    private void setService(String key, String value) {
	    // get service provider
	    ISourceProviderService sourceProviderService = 
	    		(ISourceProviderService) getSite().getService(ISourceProviderService.class);
	    RttMbtCommandState commandStateService = 
	    		(RttMbtCommandState) sourceProviderService.getSourceProvider(RttMbtCommandState.keyIsGenerationContextTP);
	    // set value
		commandStateService.setValue(key,value);    	
    }
    
    private Boolean hasModelSubdirectory() {
    	// check if the selected object is a directory
    	File thisFolder = new File(selectedObjectPath);
    	if (!thisFolder.isDirectory()) {
    		return false;
    	}

    	// search for child folder "model"
		File[] files = thisFolder.listFiles();
		if (files == null) {
			return false;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (files[i].getName().compareTo("model") == 0) {
					return true;
				}
			}
		}

		// if no sub folder "model" was found: return false
    	return false;
    }

    // check if the selected item is a test procedure generation context
    public Boolean isGenerationContextSelected() {
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
	public Boolean isExecutionContextSelected() {
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
	
	public Boolean isModelDumpSelected() {
		if (selectedObject == null) {
			return false;
		}
		return selectedObject.compareTo("model_dump.xml") == 0;
	}

	public Boolean isMakefileSelected() {
		if (selectedObject == null) {
			return false;
		}
		return selectedObject.startsWith("Makefile");
	}

	public Boolean isRttMbtProjectSelected() {
		if (selectedObjectPath == null) {
			return false;
		}

		if (RttMbtClient.isRttMbtProject(selectedObjectPath)) {
			return true;
		}
		// check if the selected directory has a sub directory "model"
		if (!hasModelSubdirectory()) {
			return false;
		}

		return false;
	}
}
