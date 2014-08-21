package eu.compassresearch.ide.refinementtool.handlers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.view.PoListView;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefConstants;
import eu.compassresearch.ide.refinementtool.RefUtils;
import eu.compassresearch.ide.refinementtool.Refinement;
import eu.compassresearch.ide.refinementtool.view.RefineLawView;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

public class RefineApplyHandler extends AbstractHandler {

	public RefineApplyHandler() {
		super();
	}

	static IResource extractSelection(ISelection sel) {
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
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		CmlEditor editor = (CmlEditor) window.getActivePage().getActiveEditor();

		IStructuredSelection sel = (IStructuredSelection) window
				.getSelectionService().getSelection(
						"eu.compassresearch.ide.ui.CmlNavigator");
		IResource res = extractSelection(sel);

		IProject project = null;
		if (res != null ) {
			project = res.getProject();
		}
		
		ICmlProject cmlProj = (ICmlProject) project
				.getAdapter(ICmlProject.class);
		
		
		RefineLawView rv = null;
		PoListView pt = null;
		
		try {
			rv = (RefineLawView) window.getActivePage().showView(RefConstants.REF_LAW_VIEW);
			pt = (PoListView) window.getActivePage().showView(RefConstants.REF_POG_VIEW);
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		ITextSelection selection = rv.getSelection();
		IRefineLaw law = rv.getCurrentlySelectedLaw(); 
			
		int lineOffset = 0;
		
		try {
			int l1 = doc.getLineOfOffset(selection.getOffset());
			int l2 = doc.getLineOffset(l1);
		    lineOffset = selection.getOffset() - l2;
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (law != null) {
			INode node = rv.getNode();
			while (node instanceof AActionStm || node instanceof AStmAction) {
				if (node instanceof AActionStm)
					node = ((AActionStm)node).getAction();
				if (node instanceof AStmAction)
					node = ((AStmAction)node).getStatement();

			}
			try {
				Refinement ref = null;
				if (law.getMetas().size() > 0) {
					
					boolean failed = false;
					Map<String, INode> mv = new HashMap<String, INode>();
					
					for (String m : law.getMetas().keySet()) {
						MetaVarValidator mvv = new MetaVarValidator(law.getMetas().get(m));
						InputDialog id = new InputDialog(window.getShell()
								,"Input meta-variable"
								,"Please enter " + m + " of type " + law.getMetas().get(m)
								,""
								,mvv);
						id.open();
						
						if (id.getValue() != null ) {
							mv.put(m, mvv.getMVnode());
						} else {
							failed = true;
							break;
						}
					}
					
					if (!failed)
						ref = law.apply(mv, node, lineOffset);
					
				}
				else {				
					ref = law.apply(new HashMap<String, INode>(), node, lineOffset);
				}
				
				CmlProofObligationList pol = cmlProj.getModel().getAttribute(RefConstants.RPOL_ID, CmlProofObligationList.class);
				
				if (ref != null) {
				
					doc.replace(selection.getOffset(), selection.getLength(), ref.getResult());
				
					
					
					
					pol.addAll(ref.getProvisos());				
					pt.setDataList(cmlProj, pol);
					
					pt.refreshList();
					
					
				}
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
