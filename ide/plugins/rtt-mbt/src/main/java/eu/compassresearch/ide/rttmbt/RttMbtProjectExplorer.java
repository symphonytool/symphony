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

    private void initClient(IProject project) {
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
    }

    private void evaluteSelection(IStructuredSelection selection) {
	    // evaluate selection
		selectedObject = null;
		selectedObjectPath = null;
		isFolderSelected = false;
		isFileSelected = false;

    	// set workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspaceDirectory = workspace.getRoot().getLocation().toFile();

		// clear tree selection in Activator
		Activator.setCurrentProjectExplorerItem(null);

		IProject project = null;
		if (selection instanceof TreeSelection) {		    
			TreeSelection treeSelection = (TreeSelection)selection;
			Activator.setCurrentProjectExplorerItem(treeSelection);
			if (treeSelection.size() == 0) {
				setAllKeysFlase();
				return;
			}
			if ((treeSelection.size() > 1) && (!client.getAllowMultipleRttMbtTmsTasks())) {
				setAllKeysFlase();
				return;
			}
			// if more than one item is selected, check that the same type of elements is selected
			boolean wasGenerationContextSelected = true;
			boolean wasExecutionContextSelected = true;
			boolean wasModelDumpSelected = true;
			boolean wasRttMbtProjectSelected = true;
			boolean wasMakefileSelected = true;
			Object elements[] = treeSelection.toArray();
			for (int idx = 0; idx < elements.length; idx++) {
				// get current element
				Object element = elements[idx];
				if (element == null) continue;
				if (element instanceof IFolder) {
					IFolder folder = (IFolder)element;
					project = folder.getProject();
					selectedObject = folder.getName();
					selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(folder.getLocationURI());
					isFolderSelected = true;
				} else if (element instanceof IProject) {
					project = (IProject)element;
					selectedObject = project.getName();
					selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(project.getLocationURI());
					isFolderSelected = true;
				} else if (element instanceof IFile) {
					IFile file = (IFile)element;
					project = file.getProject();
					selectedObject = file.getName();
					selectedObjectPath = RttMbtClient.getAbsolutePathFromFileURI(file.getLocationURI());
					isFileSelected = true;
				} else {
					setAllKeysFlase();
					continue;
				}

				// init client according to properties of the currently selected project
				initClient(project);

				// enable RTT-MBT actions
				if (isGenerationContextSelected() && (wasGenerationContextSelected)) {
					setService(RttMbtCommandState.keyIsGenerationContextTP,RttMbtCommandState.TRUE);
					setService(RttMbtCommandState.keyIsExecutionContextTP,RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
					wasExecutionContextSelected = false;
					wasModelDumpSelected = false;
					wasRttMbtProjectSelected = false;
					wasMakefileSelected = false;
				} else if ((isExecutionContextSelected() || RttMbtClient.isRtt6TestProcedure(selectedObjectPath)) &&
						   (wasExecutionContextSelected)) {
					setService(RttMbtCommandState.keyIsExecutionContextTP,RttMbtCommandState.TRUE);
					setService(RttMbtCommandState.keyIsGenerationContextTP,RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
					wasGenerationContextSelected = false;
					wasModelDumpSelected = false;
					wasRttMbtProjectSelected = false;
					wasMakefileSelected = false;
					if (hasGenerationContext()) {
						setService(RttMbtCommandState.keyHasGenerationContextTP,RttMbtCommandState.TRUE);
					} else {
						setService(RttMbtCommandState.keyHasGenerationContextTP,RttMbtCommandState.FALSE);
					}
				} else if (isModelDumpSelected() && (wasModelDumpSelected)) {
					setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.TRUE);
					setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
					wasGenerationContextSelected = false;
					wasExecutionContextSelected = false;
					wasRttMbtProjectSelected = false;
					wasMakefileSelected = false;
				} else if (isRttMbtProjectSelected() && (wasRttMbtProjectSelected)) {
					setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.TRUE);
					setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
					wasGenerationContextSelected = false;
					wasExecutionContextSelected = false;
					wasModelDumpSelected = false;
					wasMakefileSelected = false;
				} else if (isMakefileSelected() && (wasMakefileSelected)) {
					setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.TRUE);
					setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
					setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
					wasGenerationContextSelected = false;
					wasExecutionContextSelected = false;
					wasModelDumpSelected = false;
					wasRttMbtProjectSelected = false;
				} else {
					setAllKeysFlase();
					wasGenerationContextSelected = false;
					wasExecutionContextSelected = false;
					wasModelDumpSelected = false;
					wasRttMbtProjectSelected = false;
					wasMakefileSelected = false;
				}
			}
		}

		return;
	}

    private void setAllKeysFlase() {
		setService(RttMbtCommandState.keyIsGenerationContextTP, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsExecutionContextTP, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsModelDumpSelected, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsRttMbtProjectSelected, RttMbtCommandState.FALSE);
		setService(RttMbtCommandState.keyIsMakefileSelected, RttMbtCommandState.FALSE);
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
    
    private String getService(String key) {
	    // get service provider
	    ISourceProviderService sourceProviderService = 
	    		(ISourceProviderService) getSite().getService(ISourceProviderService.class);
	    RttMbtCommandState commandStateService = 
	    		(RttMbtCommandState) sourceProviderService.getSourceProvider(RttMbtCommandState.keyIsGenerationContextTP);
	    // get value
	    return commandStateService.getValue(key);
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

	public Boolean hasGenerationContext() {
		if (selectedObjectPath == null) {
			return false;
		}
		String RttTestProc = client.getRttMbtTestProcFolderName();
		String TProcGenCtx = client.getRttMbtTProcGenCtxFolderName();
		int idx = selectedObjectPath.lastIndexOf(RttTestProc);
		if (idx < 1) { return false; }
		if ((selectedObjectPath.substring(idx - 1, idx).compareTo("/") != 0) &&
			(selectedObjectPath.substring(idx - 1, idx).compareTo(File.separator) != 0)) { return false; }
		String path = selectedObjectPath.substring(0, idx) + TProcGenCtx + selectedObjectPath.substring(idx + RttTestProc.length());
		System.out.println("genCtx: '" + path + "'");
		File genCtx = new File(path);
		return (genCtx.isDirectory());
	}

	public Boolean isModelDumpSelected() {
		if (selectedObject == null) {
			return false;
		}
		return (((selectedObject.compareTo("model_dump.xml") == 0) &&
				 (getService(RttMbtCommandState.keyIsRttPerspectiveActive).compareTo(RttMbtCommandState.TRUE) == 0))
				||
				((selectedObjectPath.substring(selectedObjectPath.length() - 4).compareTo(".uml") == 0)) &&
				 (client.getIsPapyrusMode())
			   );
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
