package eu.compassresearch.ide.collaboration.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;

import eu.compassresearch.ide.collaboration.Activator;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationDataModelManager;
import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.menu.NewCollaborationProjectDialog;

public class NewCollaborationProjectHandler extends AbstractHandler
{

	private NewCollaborationProjectDialog collaborationProjectDialog;

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		CollaborationDataModelManager dataModelManager = Activator.getDefault().getDataModelManager();
		collaborationProjectDialog = CollaborationDialogs.getInstance().getCollaborationProjectDialog(dataModelManager.getCollaborationProjects());
		
		if(collaborationProjectDialog.open() == Window.OK) 
		{

			dataModelManager.addCollaborationProject(collaborationProjectDialog.getProject(), collaborationProjectDialog.getTitle(), collaborationProjectDialog.getDescription());
		}
		
		return null;
	}

}
