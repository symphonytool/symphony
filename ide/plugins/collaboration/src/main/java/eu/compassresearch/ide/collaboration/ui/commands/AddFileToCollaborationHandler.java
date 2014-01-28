package eu.compassresearch.ide.collaboration.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class AddFileToCollaborationHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			// get selected file
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);

			// let modelManager handle the file
			FileStatus fileStatus = dataModelManager.handleFile(file);

			// display dialog to user with the status of the file addition.
			CollaborationDialogs.displayFileState(fileStatus, file);
		} else
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_NO_FILE_FROM_SELECTION
					+ " " + selection, null));
		}

		return null;
	}

}
