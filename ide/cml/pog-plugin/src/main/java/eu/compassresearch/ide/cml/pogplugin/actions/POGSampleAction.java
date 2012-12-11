package eu.compassresearch.ide.cml.pogplugin.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

//import  eu.compassresearch.core.analysis.pog.obligations

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
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

	// sProof

	// add file and write POs to it
	String workspaceLoc = ResourcesPlugin.getWorkspace().getRoot()
		.getLocation().toString();
	File tempPo = new File(workspaceLoc, "proofobligation.tmp");

	FileWriter fw;
	try {
	    fw = new FileWriter(tempPo);
	    fw.write(csu.toString());
	    fw.flush();
	    fw.close();

	    File fileToOpen = new File(workspaceLoc, "proofobligation.tmp");

	    if (fileToOpen.exists() && fileToOpen.isFile()) {
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(
			fileToOpen.toURI());
		IWorkbenchPage page = PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage();
		IDE.openEditorOnFileStore(page, fileStore);

	    } else {
		// Do something if the file does not exist
	    }

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (PartInitException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// tempPo.

	MessageDialog.openInformation(window.getShell(), "COMPASS",
		"POG test run to come... \n Working with: " + csu.toString());
	// TODO add POG run plus output
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