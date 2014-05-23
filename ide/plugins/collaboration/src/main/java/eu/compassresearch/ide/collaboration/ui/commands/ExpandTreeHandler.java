package eu.compassresearch.ide.collaboration.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import eu.compassresearch.ide.collaboration.ui.menu.CollaborationDialogs;
import eu.compassresearch.ide.collaboration.ui.view.CollaborationView;

public class ExpandTreeHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		CollaborationView collaborationView = CollaborationDialogs.getCollaborationView();
		collaborationView.expandAll();

		return null;
	}
}