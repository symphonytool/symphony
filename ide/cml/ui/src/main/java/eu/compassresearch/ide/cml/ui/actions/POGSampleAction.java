package eu.compassresearch.ide.cml.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.jface.dialogs.MessageDialog;
import org.overture.ast.node.INode;

import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
//import eu.compassresearch.core

//import  eu.compassresearch.core.analysis.pog.obligations

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class POGSampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public POGSampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		CmlEditor edi = (CmlEditor) window.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (edi == null)
		    return;
		FileEditorInput fei = (FileEditorInput) edi.getEditorInput();
		CmlSourceUnit csu = CmlSourceUnit.getFromFileResource(fei.getFile());
		
		//sProof
		
		MessageDialog.openInformation(
			window.getShell(),
			"COMPASS",
			"POG test run to come... \n Working with: "+csu.toString());
		//TODO add POG run plus output
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}