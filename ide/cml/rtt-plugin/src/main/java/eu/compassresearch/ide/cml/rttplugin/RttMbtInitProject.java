package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

public class RttMbtInitProject extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		String selectionName = "";
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				selectionName = folder.getName();
			}
		}
		System.out.println("selected item: '" + selectionName + "'");

		// try to print something to the console view (not working, yet)
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int idx = 0; idx < consoles.length; idx++) {
			System.out.println("console #" + idx + ": " + consoles[idx].getName() + "'");
		}
		if (consoles.length == 0) {
			System.err.println("[FAIL]: no active console present!");
		}
		
		// create RTT-MBT TMS client
    	RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());
		// @todo: calculate CML project and RTT-MBT component name from selected folder
		//client.setCmlProject(project.getFullPath().toString());
		System.out.println("projectWorkingDir: '" + client.getCmlWorkspace() + client.getCmlProject() + "'");
		return null;
	}
}
