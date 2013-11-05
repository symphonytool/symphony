package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.theoremprover.CmlTPPlugin;
import eu.compassresearch.ide.theoremprover.FetchPosUtil;
import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;
import eu.compassresearch.ide.theoremprover.TPPluginUtils;


public class DischargePosHandler extends AbstractHandler
{

	FetchPosUtil util = null;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
//		// we should probably send the project to te TP handler
//		// we already from the POs (or at least we should have it)
//		
//		if (util == null) {
//			
//			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
//			if (proj == null) {
//				Activator.log("no project selected",null);
//				return null;
//			}
//
//			ICmlProject cmlProject = (ICmlProject) proj
//					.getAdapter(ICmlProject.class);
//			util = new FetchPosUtil(HandlerUtil.getActiveShell(event),cmlProject);
//		}
//		
////		util.fetchPOs(PogPluginUtility.getPoggedProject());
//		util.fetchPOs();
//		
////		String message = "Proof Obligations sent to Theorem Prover (Coming soon!)";
////		
////		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(),"Symphony", message);
//		return null;
//		
//		

		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		TPPluginDoStuff doer = new TPPluginDoStuff(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
		doer.fetchPOs();
		
		return null;
	}
}
