package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.PogPluginUtils;
import eu.compassresearch.ide.pog.view.PoListView;
import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;


public class DischargePosHandler extends AbstractHandler
{

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{

		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			PoListView view = PogPluginUtils.getMainView();
			ICmlProject proj = view.getProject();
			TPPluginDoStuff doer = new TPPluginDoStuff(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
			doer.dischargePos(proj);
			
		} catch (PartInitException e) {
			MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Symphony Internal Error", 
					"Could not load the POG View.");
			e.printStackTrace();
		}
		
		return null;
	}
}
