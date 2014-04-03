package eu.compassresearch.ide.refinementtool.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.node.IToken;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.refinementtool.INodeNearCaret;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;
import eu.compassresearch.ide.ui.editor.syntax.INodeFromCaret;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RefineHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public RefineHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		CmlEditor editor = (CmlEditor) window.getActivePage().getActiveEditor();
		
			
		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		
		INode node = null;

		FileEditorInput fei = (FileEditorInput) editor.getEditorInput();
		
		// FIXME get source unit, update implementation
		ICmlSourceUnit csu = (ICmlSourceUnit) fei.getFile().getAdapter(ICmlSourceUnit.class);
		if (csu == null || csu.hasParseErrors() || !csu.hasParseTree())
		{
			return null;
		}
		List<PDefinition> ast = csu.getParseListDefinitions();

		INodeNearCaret visitor = new INodeNearCaret(selection.getOffset(), ast.get(0));

		try
		{
			for (PDefinition def : ast)
			{
				def.apply(visitor);
			}
			// ast.apply(visitor);
			node = visitor.getBestCandidate();
		} catch (AnalysisException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		editor.selectAndReveal(node);
		
		selection = (ITextSelection) editor.getSelectionProvider().getSelection();
	
		
		
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
/*
		
		try {
			doc.replace(selection.getOffset(), selection.getLength(), "Hello!");
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		
		
	
/*		
		IRegion range = editor.getHighlightRange();
		
		int offset = range.getOffset();
		
		INode node = editor.getElementAt(offset);
		*/
		
		
		
		
		return null;
	}
}
