package eu.compassresearch.ide.cml.pogplugin.actions;

import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.overture.ast.analysis.AnalysisException;
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

	IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		try {

			// IWorkbenchWindow window =
			// PlatformUI.getWorkbench().getActiveWorkbenchWindow();

			ISelection selection = window.getSelectionService().getSelection(
					"org.overture.ide.ui.VdmExplorer");

			if (selection.isEmpty()) {
				popErrorMessage("No project selected selected.");
				return;
			}

			IResource res = extractSelection(selection);
			IProject proj = (IProject) res;

			// FileEditorInput fei = (FileEditorInput) edi.getEditorInput();
			CmlSourceUnit csu = null;// CmlSourceUnit.getFromFileResource(fei.getFile());

			String workspaceLoc = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toString();
			File tempPo = new File(workspaceLoc, "proofobligations");
			tempPo.deleteOnExit();

			FileWriter fw;

			fw = new FileWriter(tempPo);
			// fw.write(csu.toString());
			fw.write(getPOsfromSource(csu));
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
				"Could not generate POs.\n" + message);
	}

	private char[] getPOsfromSource(CmlSourceUnit csu) throws Exception {

		if (!CmlTypeChecker.Utils.isWellType(csu.getSourceAst())) {
			throw new AnalysisException("Type errors in "
					+ csu.getFile().getName());
		}

		StringBuilder sb = new StringBuilder();
		sb.append("-- AUTO-GENERATED PROOF OBLIGATIONS FOR: ");
		sb.append(((IFile) csu.getFile()).getName() + "\n");
		sb.append("-- CAUTION: this file is temporary and will be auto-deleted.\n");
		sb.append("------------------------------------------------------------\n");

		PSource psAux = csu.getSourceAst();
		ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
		ProofObligationList pol = new ProofObligationList();
		try {
			pol = pog.generatePOs();
			for (ProofObligation po : pol)
				sb.append(po.toString() + "\n");
		} catch (Exception e) {
			throw e;
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