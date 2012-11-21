package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import eu.compassResearch.rttMbtTmsClientApi.RttMbtClient;

/*
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
*/

public class RttMbtInitProject extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// get selected object
		String selectionName = "";
		String selectionFullName = "";
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection)selection;
			if ((treeSelection.getFirstElement() != null) &&
				(treeSelection.getFirstElement() instanceof IFolder)) {
				IFolder folder = (IFolder)treeSelection.getFirstElement();
				selectionName = folder.getName();
				IPath fullpath = folder.getFullPath();
				selectionFullName = fullpath.toString();
			}
		}
		
		// calculate CML project name from full path to selected folder
		String CmlProject = selectionFullName.substring(0, selectionFullName.length() - selectionName.length() - 1);

		/*
		// try to print something to the console view (not working, yet)
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int idx = 0; idx < consoles.length; idx++) {
			System.out.println("console #" + idx + ": " + consoles[idx].getName() + "'");
		}
		if (consoles.length == 0) {
			System.err.println("[FAIL]: no active console present!");
		}
        */

		// create RTT-MBT TMS client
    	RttMbtClient client = new RttMbtClient("localhost", 9116, "uwe", "uschulze@informatik.uni-bremen.de");

    	// set CML workspace
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		client.setCmlWorkspace(workspaceDirectory.getAbsolutePath());
		
		// set CML project name
		client.setCmlProject(CmlProject);

		// set RTT-MBT project name
		client.setProjectName(selectionName);

		System.out.println("RttMbtProjectDir: '" + client.getRttProjectRoot() + "'");
		
		// test connection to rtt-mbt-tms server
		if (client.testConenction()) {
			System.out.println("[PASS]: test RTT-MBT server connection");
		} else {
			System.err.println("[FAIL]: test RTT-MBT server connection");
			return null;
		}

		// start RTT-MBT-TMS session
		if (client.beginRttMbtSession()) {
			System.out.println("[PASS]: begin RTT-MBT session");
		} else {
			System.err.println("[FAIL]: begin RTT-MBT session");
			return null;
		}

		// get model file name
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FileDialog getModelFile = new FileDialog(activeShell);
		String ModelFile = getModelFile.open();
		System.out.println("model file '" + ModelFile + "'");

		// initialize project with a model
		if (client.initProject("turnIndicationUML", "1.0", ModelFile)) {
			System.out.println("[PASS]: init project");
		} else {
			System.err.println("[FAIL]: init project");
			return null;
		}
				
		return null;
	}
}
