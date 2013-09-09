package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;

public class GenerateThyHandler extends AbstractHandler
{	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		TPPluginDoStuff doer = new TPPluginDoStuff(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
		doer.runTP();
		
		return null;
	}

}
