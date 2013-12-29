package eu.compassresearch.ide.collaboration.ui.commands;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.notifications.Notification;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;

public class AddFileToCollaborationHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();

		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if (selection instanceof IStructuredSelection) {
			
			//get selected file
		    IStructuredSelection ssel = (IStructuredSelection) selection;
		    Object obj = ssel.getFirstElement();
		    IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
		
		    //get the collaboration project
		    IProject project = file.getProject();  
		    CollaborationProject collaborationProject = dataModelManager.getCollaborationProject(project.getName());
		    
		    if(collaborationProject != null) {
		    	
		    	//add the file to the collaboration
				try
				{
					if (collaborationProject.addNewFile(file)) {
						CollaborationDialogs.getInstance().displayNotificationPopup("", "File added to collaboration");
					} else {
						CollaborationDialogs.getInstance().displayNotificationPopup("", "File already part of collaboration");
					}
				} catch (CoreException | IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		    else
		    {
		    	CollaborationDialogs.displayErrorDialog(Notification.Collab_File_ERROR_ADD_FILE_FAILED, Notification.Collab_File_ERROR_NO_COLLAB_PROJECT_ATTACHED + project.toString());
		    }
		} 
		else 
		{
			ResourcesPlugin.getPlugin().getLog().log(new Status(Status.ERROR, selection.toString(), 0, Notification.Collab_File_NO_FILE_FROM_SELECTION + " " + selection, null));
		}
		
		return null;
	}

}
