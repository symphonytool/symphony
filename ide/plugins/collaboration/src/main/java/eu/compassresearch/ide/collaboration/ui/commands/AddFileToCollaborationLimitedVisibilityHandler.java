package eu.compassresearch.ide.collaboration.ui.commands;

import java.util.List;

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
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.files.FileStatus;
import eu.compassresearch.ide.collaboration.files.FileStatus.FileState;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.AddFileLimitedVisibilityDialog;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class AddFileToCollaborationLimitedVisibilityHandler extends
		AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		//TODO merge with AffFileToCollaboration
		if (selection instanceof IStructuredSelection)
		{
			// get selected file
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();
			IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
			
			String projectName = file.getProject().getName();
			CollaborationProject collaborationProject = dataModelManager.getCollaborationProject(projectName);
			
			AddFileLimitedVisibilityDialog addFileDialog = CollaborationDialogs.getInstance().getAddFileLimitedVisibilityDialog(file.getName(), collaborationProject);
			 
			if(addFileDialog.open() == Window.OK) {
				
				List<String> selectedCollaborators = addFileDialog.getSelectedCollaborator();
				
				//add file to collaboration
				FileStatus fileStatus = dataModelManager.addFileWithLimitedVisibility(file, selectedCollaborators);
				
				if (fileStatus.getState() == FileState.ADDED)
				{
					CollaborationDialogs.getInstance().displayNotificationPopup("", "File added to collaboration");
				} else if (fileStatus.getState() == FileState.UNCHANGED)
				{
					CollaborationDialogs.getInstance().displayNotificationPopup("", "File already part of collaboration");
				}
			}
		} else {
			ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Notification.Collab_File_NO_FILE_FROM_SELECTION
					+ " " + selection, null));
		}
		return null;
	}
}
