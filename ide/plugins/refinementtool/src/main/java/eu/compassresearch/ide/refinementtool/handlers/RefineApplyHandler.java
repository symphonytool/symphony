package eu.compassresearch.ide.refinementtool.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefConstants;
import eu.compassresearch.ide.refinementtool.view.RefineLawView;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

public class RefineApplyHandler extends AbstractHandler {

	public RefineApplyHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		CmlEditor editor = (CmlEditor) window.getActivePage().getActiveEditor();
		
		RefineLawView rv = null;
		
		try {
			rv = (RefineLawView) window.getActivePage().showView(RefConstants.REF_LAW_VIEW);
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection selection = rv.getSelection();
		IRefineLaw law = rv.getCurrentlySelectedLaw(); 
			
		if (law != null) {
			
			try {			
				doc.replace(selection.getOffset(), selection.getLength(), law.apply(rv.getNode()).getResult());
				rv.clearLaws();
			    // editor.getDocumentProvider().saveDocument(new NullProgressMonitor(), null, doc, true);
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// TODO Auto-generated method stub
		return null;
	
	}

}
