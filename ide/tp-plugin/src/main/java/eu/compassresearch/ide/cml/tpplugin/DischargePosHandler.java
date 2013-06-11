package eu.compassresearch.ide.cml.tpplugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.cml.pogplugin.PogPluginUtility;


public class DischargePosHandler extends AbstractHandler
{

	FetchPosUtil util = null;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// we should probably send the project to te TP handler
		// we already from the POs (or at least we should have it)
		
		if (util == null) {
			util = new FetchPosUtil(HandlerUtil.getActiveWorkbenchWindow(event));
		}
		
		util.fetchPOs(PogPluginUtility.getPoggedProject());
		
		String message = "Proof Obligations sent to Theorem Prover (Coming soon!)";
		
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(),"COMPASS", message);
		return null;
	}

}
