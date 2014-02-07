package eu.compassresearch.ide.theoremprover.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.theoremprover.ProofSess;
import eu.compassresearch.ide.theoremprover.TPConstants;
import eu.compassresearch.ide.theoremprover.TPPluginUtils;

public class SubAllPosHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage();

		IProject proj = TPPluginUtils.getCurrentlySelectedProject();

		ICmlProject cmlProj = (ICmlProject) proj
				.getAdapter(ICmlProject.class);

		ProofSess ps = cmlProj.getModel().getAttribute(
				TPConstants.PROOF_SESSION_ID, ProofSess.class);

		ps.enqueueAllPOs();

		return null;
	}

}
