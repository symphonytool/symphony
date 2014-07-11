package eu.compassresearch.ide.s2c.commands;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.core.s2c.S2cTranslator;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.s2c.ICmlS2cConstants;

public class S2cCommand extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IFile)
			{
				final IFile iFile = (IFile) firstElement;
				final Display display = Display.getCurrent();
				
				
				Job convert = new Job("Importing UML")
				{

					@Override
					protected IStatus run(IProgressMonitor progress)
					{
						progress.beginTask("Importing", 100);
						progress.worked(5);

						S2cTranslator translator = new S2cTranslator();
						
						IProject p = iFile.getProject();
						ICmlProject cmlP = (ICmlProject) p.getAdapter(ICmlProject.class);
						File output = cmlP.getModelBuildPath().getModelSrcPaths().get(0).getLocation().toFile();
						
						try
						{
							translator.translate(iFile.getLocation().toFile(), output);
						} catch (Exception e)
						{
							e.printStackTrace();
							return new Status(IStatus.ERROR, ICmlS2cConstants.PLUGIN_ID, "Failed importing .xmi file.",e);
						} 
						

						progress.worked(40);
						display.asyncExec(new Runnable()
						{
							public void run()
							{

								try
								{
									iFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
								} catch (CoreException e)
								{
									// ignore
								}
							}
						});

						progress.worked(50);

						progress.worked(5);
						progress.done();

						return Status.OK_STATUS;
					}
				};
				convert.schedule();
			}
		}
		return null;
	}

}
