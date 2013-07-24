package eu.compassresearch.ide.cml.pogplugin;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.overture.ide.core.resources.IVdmProject;

import eu.compassresearch.ide.cml.pogplugin.view.PoListView;

public class PogPluginUtility
{

	private IWorkbenchSite site;

	public static ArrayList<IResource> getAllCFilesInProject(IProject project)
	{
		ArrayList<IResource> allCFiles = new ArrayList<IResource>();
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		IPath path = project.getLocation();

		recursiveFindCMLFiles(allCFiles, path, myWorkspaceRoot);
		return allCFiles;
	}

	public PogPluginUtility(IWorkbenchSite site)
	{
		this.site = site;
	}

	public void openPoviewPerspective()
	{
		try
		{
			PlatformUI.getWorkbench().showPerspective(POConstants.PO_PERSPECTIVE_ID, site.getWorkbenchWindow());
		} catch (WorkbenchException e)
		{

			e.printStackTrace();
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the project currently loaded in the Proof Obligation Viewer
	 * 
	 * @return the project or null, if no project is loaded or this method
	 * is called from a non-UI thread
	 */
	public static IProject getPoggedProject()
	{
		try
		{
			PoListView v = (PoListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(POConstants.PO_OVERVIEW_TABLE);
			IVdmProject proj = v.getProject();

			return (IProject) proj.getAdapter(IProject.class);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static IProject getCurrentlySelectedProject()
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

}
