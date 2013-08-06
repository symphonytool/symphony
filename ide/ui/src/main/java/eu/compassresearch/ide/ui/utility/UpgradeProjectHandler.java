package eu.compassresearch.ide.ui.utility;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.ui.wizard.NewCmlProjectWizard;

public class UpgradeProjectHandler extends AbstractHandler implements IHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IProject)
			{
				IProject project = (IProject) firstElement;
				try
				{
					NewCmlProjectWizard.setVdmBuilder(project);
					IProjectDescription d = project.getDescription();
					List<String> natureIds = Arrays.asList(d.getNatureIds());

					natureIds.remove("eu.compassresearch.ide.ui.cmlnature");
					d.setNatureIds(natureIds.toArray(new String[] {}));
					project.setDescription(d, new NullProgressMonitor());
					project.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
				} catch (CoreException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
