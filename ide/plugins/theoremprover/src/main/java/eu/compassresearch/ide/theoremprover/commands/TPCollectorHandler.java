package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;
import eu.compassresearch.ide.theoremprover.TPPluginUtils;

public class TPCollectorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage();
		TPPluginDoStuff doer = new TPPluginDoStuff(
				HandlerUtil.getActiveWorkbenchWindow(event), page
						.getActivePart().getSite());
		IProject proj = TPPluginUtils.getCurrentlySelectedProject();
		try {
			doer.explicitCheckUnsupported(proj);
		} catch (Exception e) {
			MessageDialog.openError(null, "COMPASS",
					"Internal error while checking compatibility with the theorem prover: \n\n"
							+ e.getMessage()
					);
			e.printStackTrace();
		}
		return null;

	}

}
