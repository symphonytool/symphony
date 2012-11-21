package eu.compassresearch.ide.cml.rttplugin;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class RttMbtCleanRttTestProcedure extends RttMbtConcreteTestProcedureAction {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		String rttTestProc = null;
		String rttTestProcPath = null;
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				rttTestProc = folder.getName();
				rttTestProcPath = folder.getFullPath().toString();
			}
		}
		if (rttTestProcPath == null) {
			return null;
		}

		if (!initClient(rttTestProcPath)) {
			return null;
		}

		// clean test procedure
		if (client.cleanTestProcedure(rttTestProc)) {
			System.out.println("[PASS]: clean test");
		} else {
			System.err.println("[FAIL]: clean test");
			return null;
		}

		return null;
	}
}
