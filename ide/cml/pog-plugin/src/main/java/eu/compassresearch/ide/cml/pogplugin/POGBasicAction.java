package eu.compassresearch.ide.cml.pogplugin;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class POGBasicAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public POGBasicAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		try {


			IProject proj = PogPluginUtility.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No project selected.");
				return;
			}

			ArrayList<IResource> cmlfiles = PogPluginUtility
					.getAllCFilesInProject(proj);

			for (IResource cmlfile : cmlfiles) {
				CmlSourceUnit source = CmlSourceUnit
						.getFromFileResource((IFile) cmlfile);
				if (!CmlTypeChecker.Utils.isWellType(source.getSourceAst())) {
					popErrorMessage("There were type errors in "
							+ source.getFile().getName());
					return;
				}
			}

			String workspaceLoc = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toString();
			File tempPo = new File(workspaceLoc, "proofobligations");
			tempPo.deleteOnExit();

			FileWriter fw;

			fw = new FileWriter(tempPo);
			fw.write(getPOsfromSource(cmlfiles, proj.getName()));
			fw.flush();
			fw.close();

			File fileToOpen = new File(workspaceLoc, "proofobligations");

			if (fileToOpen.exists() && fileToOpen.isFile()) {
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(
						fileToOpen.toURI());
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				IDE.openEditorOnFileStore(page, fileStore);

			}

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate POs.\n\n" + message);
	}

	private char[] getPOsfromSource(ArrayList<IResource> cmlfiles,
			String projName) throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("-- Auto-Generated Proof Obligations for " + projName + " project \n");
		sb.append("-- CAUTION: this file is temporary!\n");
		sb.append("=================================================================\n\n");
		for (IResource cmlfile : cmlfiles) {
			CmlSourceUnit source = CmlSourceUnit
					.getFromFileResource((IFile) cmlfile);
			PSource psAux = source.getSourceAst();
			ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
			ProofObligationList pol = new ProofObligationList();
			sb.append("-- File: "+ cmlfile.getName() + ": \n\n");
			try {
				pol = pog.generatePOs();
				for (ProofObligation po : pol)
					sb.append(po.toString() + "\n");
				sb.append("-----------------------------------------------\n\n");
			}

			catch (Exception e) {
				throw e;
			}
		}
		return sb.toString().toCharArray();
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}