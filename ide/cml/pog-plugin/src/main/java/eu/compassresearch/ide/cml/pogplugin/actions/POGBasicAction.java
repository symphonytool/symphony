package eu.compassresearch.ide.cml.pogplugin.actions;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.util.POException;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;
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
		CmlEditor edi = (CmlEditor) window.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (edi == null)
			return;
		FileEditorInput fei = (FileEditorInput) edi.getEditorInput();
		CmlSourceUnit csu = CmlSourceUnit.getFromFileResource(fei.getFile());

		String workspaceLoc = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString();
		File tempPo = new File(workspaceLoc, "proofobligations");
		tempPo.deleteOnExit();


		FileWriter fw;
		try {
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

			} else {
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof POException){
				POException poe = (POException) e;
				StringBuilder sb = new StringBuilder();
				sb.append("Error "+ extractLine(poe.getErrorNode()));
				String s = sb.toString();
				popErrorMessage(s);
			}
			else
			popErrorMessage(e.getMessage());
		}

	}

	private String extractLine(INode i){
		String r = "Unknonwn location.";
		
		for (Method m : i.getClass().getMethods()) {
			if ("getLocation".equals(m.getName())) {
				try {
					LexLocation loc = (LexLocation) m.invoke(i, new Object[0]);
					r =  loc.toShortString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}
	
	private void popErrorMessage(String s) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate POs.\n" + s);
	}

	private char[] getPOsfromSource(CmlSourceUnit csu) throws Exception {

		if (!CmlTypeChecker.Utils.isWellType(csu.getSourceAst())) {
			throw new AnalysisException("Type errors in " + csu.getFile().getName());
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