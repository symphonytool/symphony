package eu.compassresearch.ide.cml.pogplugin.commands;

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
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "COMPASS", "Proof Obligations will be sent to Theorem Prover (Coming soon!)");
		return null;
	}

}
