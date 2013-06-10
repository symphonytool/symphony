package eu.compassresearch.ide.cml.pogplugin.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.cml.pogplugin.PogPluginDoStuff;

public class GeneratePosHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		PogPluginDoStuff doer = new PogPluginDoStuff(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
		doer.run(event);


		return null;
	}

}
