package eu.compassresearch.ide.pog.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.PogPluginRunner;
import eu.compassresearch.ide.pog.PogPluginUtils;

public class GeneratePosHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		
		IProject proj = PogPluginUtils.getCurrentlySelectedProject();

		if (proj == null)
		{
			PogPluginUtils.popErrorMessage(window,"No project selected.");
			return null;
		}
		
		ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
		
		PogPluginRunner doer = new PogPluginRunner(window, HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getActivePart().getSite(), cmlProj);
		doer.runPog();
		
		return null;
	}
}
