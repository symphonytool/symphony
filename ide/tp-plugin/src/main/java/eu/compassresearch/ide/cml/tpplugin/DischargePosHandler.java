package eu.compassresearch.ide.cml.tpplugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;




public class DischargePosHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// we should probably send the project to te TP handler
		// we already from the POs (or at least we should have it)
		
		FetchPosUtil util = new FetchPosUtil(HandlerUtil.getActiveWorkbenchWindow(event));
		
		util.fetchPOs();
		
		String message = "Proof Obligations sent to Theorem Prover (Coming soon!)";
		
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(),"COMPASS", message);
		return null;
	}

}
