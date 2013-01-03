package eu.compassresearch.ide.cml.interpreter_plugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

public final class CmlUtil {

	public static IProject getCurrentSelectedProject() {
	    IProject project = null;
	    ISelectionService selectionService = 
	        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

	    ISelection selection = selectionService.getSelection();

	    if(selection instanceof IStructuredSelection) {
	        Object element = ((IStructuredSelection)selection).getFirstElement();

	        if (element instanceof IResource) {
	            project= ((IResource)element).getProject();
	        } 
//	        else if (element instanceof PackageFragmentRoot) {
//	            IJavaProject jProject = 
//	                ((PackageFragmentRoot)element).getJavaProject();
//	            project = jProject.getProject();
//	        } else if (element instanceof IJavaElement) {
//	            IJavaProject jProject= ((IJavaElement)element).getJavaProject();
//	            project = jProject.getProject();
//	        }
	    }
	    return project;
	}
	
	public static String getProjectPath(IProject project)
	{
		String projectPath = project.getFullPath().toOSString();
		//String pat = System.getProperty("user.dir");
		
		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString();
		
		return workspacePath + projectPath;
	}
}
