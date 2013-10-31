package eu.compassresearch.ide.pog.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.pog.PogPluginRunner;

public class GeneratePosHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		PogPluginRunner doer = new PogPluginRunner(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
		doer.runPog();


		return null;
	}

}
