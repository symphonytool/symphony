package eu.compassresearch.ide.cml.interpreter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.util.GlobalEnvironmentBuilder;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

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
	
	public static List<AProcessDefinition> GetGlobalProcessesFromProject(IProject project)
	{
		return CmlUtil.GetGlobalProcessesFromSource(CmlUtil.getCmlAstSourcesFromProject(project));
	}
	
	public static List<PSource> getCmlAstSourcesFromProject(IProject project) 
	{
		List<PSource> sources = new LinkedList<PSource>();
		
		try {
			for(IResource res : project.members())
			{
				if(res instanceof IFile && ((IFile)res).getFileExtension().toLowerCase().equals("cml"))
				{
					PSource source = CmlSourceUnit.getFromFileResource((IFile)res).getSourceAst();
					if(source != null)
						sources.add(source);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return sources;
	}
	
	public static List<String> getCmlSourcesPathsFromProject(IProject project) 
	{
		List<String> sources = new LinkedList<String>();
		
		try {
			for(IResource res : project.members())
			{
				if(res instanceof IFile && ((IFile)res).getFileExtension().toLowerCase().equals("cml"))
				{
					PSource source = CmlSourceUnit.getFromFileResource((IFile)res).getSourceAst();
					if(source != null && source instanceof AFileSource)
						sources.add(((AFileSource)source).getFile().getCanonicalPath());
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sources;
	}
	
	public static String getProjectPath(IProject project)
	{
		String projectPath = project.getFullPath().toOSString();
		//String pat = System.getProperty("user.dir");
		
		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString();
		
		return workspacePath + projectPath;
	}
	
	public static List<AProcessDefinition> GetGlobalProcessesFromSource(List<PSource> projectSources)
	{
		if(projectSources.isEmpty())
			return new LinkedList<AProcessDefinition>();
		
		GlobalEnvironmentBuilder builder = new GlobalEnvironmentBuilder(projectSources);
		
		return builder.getGlobalProcesses();

	}

	//	static public IVdmProject getVdmProject(ILaunchConfiguration configuration)
	//	throws CoreException
	//{
	//
	//IProject project = getProject(configuration);
	//
	//if (project != null)
	//{
	//	IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);
	//	return vdmProject;
	//}
	//return null;
	//}
}
