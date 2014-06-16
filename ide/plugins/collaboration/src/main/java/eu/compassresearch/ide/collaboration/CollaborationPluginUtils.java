package eu.compassresearch.ide.collaboration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class CollaborationPluginUtils
{
	public static ArrayList<IResource> getAllCFilesInProject(IProject project)
	{
		ArrayList<IResource> allCFiles = new ArrayList<IResource>();
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		IPath path = project.getLocation();

		recursiveFindCMLFiles(allCFiles, path, myWorkspaceRoot);
		return allCFiles;
	}

	private static void recursiveFindCMLFiles(ArrayList<IResource> allCMLFiles,
			IPath path, IWorkspaceRoot myWorkspaceRoot)
	{
		IContainer container = myWorkspaceRoot.getContainerForLocation(path);

		try
		{
			IResource[] iResources;
			iResources = container.members();
			for (IResource iR : iResources)
			{
				// for c files
				if ("cml".equalsIgnoreCase(iR.getFileExtension()))
					allCMLFiles.add(iR);
				if (iR.getType() == IResource.FOLDER)
				{
					IPath tempPath = iR.getLocation();
					recursiveFindCMLFiles(allCMLFiles, tempPath, myWorkspaceRoot);
				}
			}
		} catch (CoreException e)
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e));
		}
		
	}

	public static IProject getCurrentSelectedProject()
	{

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (window != null)
		{
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection("eu.compassresearch.ide.ui.CmlNavigator");
			IResource res = extractSelection(selection);
			if (res != null)
			{
				IProject project = res.getProject();
				return project;
			}
		}
		return null;
	}
	
	public static IProject[] getProjectsInWorkbench()
	{
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	static IResource extractSelection(ISelection sel)
	{
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}

	public static String convertStreamToString(InputStream is) {
		Scanner scanner = new Scanner(is,"UTF-8");
		try {
			if(scanner.hasNext()){
				String inputStreamString = scanner.useDelimiter("\\A").next();
				
				return inputStreamString;
			} else {
				return "";
			}
		} finally {
			scanner.close();
		}
	}
}
