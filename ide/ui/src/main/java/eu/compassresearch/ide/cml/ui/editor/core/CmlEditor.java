/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package eu.compassresearch.ide.cml.ui.editor.core;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.part.FileEditorInput;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ide.ui.editor.core.VdmEditor;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;
import org.overture.ide.ui.outline.VdmContentOutlinePage;
import org.overture.ide.ui.utility.ast.AstLocationSearcher;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlTreeContentProvider;
import eu.compassresearch.ide.cml.ui.editor.syntax.INodeFromCaret;
import eu.compassresearch.ide.cml.ui.editor.syntax.OutlineLabelProvider;

public class CmlEditor extends VdmEditor {

	@Override
	protected VdmSourceViewerConfiguration getVdmSourceViewerConfiguration(IPreferenceStore fPreferenceStore) {
		return new CmlSourceViewerConfiguration();
	}
	
	protected VdmContentOutlinePage createOutlinePage()
	{
		VdmContentOutlinePage page = super.createOutlinePage();
		
//		page.addSelectionChangedListener(new ISelectionChangedListener()
//		{
//
//			public void selectionChanged(SelectionChangedEvent event)
//			{
//				ISelection s = event.getSelection();
//				if (s instanceof IStructuredSelection)
//				{
//					IStructuredSelection ss = (IStructuredSelection) s;
//					@SuppressWarnings("rawtypes")
//					List elements = ss.toList();
//					if (!elements.isEmpty())
//					{
//						if (elements.get(0) instanceof INode)
//						{
//							INode node = (INode) elements.get(0);
//							int[] offsetLength = AstLocationSearcher.getNodeOffset(node);
//							selectAndReveal(offsetLength[0], offsetLength[1]);
//						}
//					}
//				}
//			}
//		});
		
//		OutlineLabelProvider
//		CmlTreeContentProvider
		
		page.configure(new CmlTreeContentProvider(null),new OutlineLabelProvider());
		
		return page;
	}

	
//    private AbstractSelectionChangedListener selectionChangeListener;
//
//    public VdmSourceViewerConfiguration getVdmSourceViewerConfiguration() {
//	return new CmlSourceViewerConfiguration();
//    }
//    
//    @Override
//    protected void doSetInput(IEditorInput input) throws CoreException {
//	super.doSetInput(input);
//	ICommandService commandService = (ICommandService) PlatformUI
//		.getWorkbench().getService(ICommandService.class);
//	try {
//	    Command cmd = commandService
//		    .getCommand("org.eclipse.ui.project.build");
//	    cmd.executeWithChecks(new ExecutionEvent());
//	} catch (Exception exception) {
//	}
//
//    }

//    private class CmlSelectionChangeListener extends
//	    AbstractSelectionChangedListener implements
//	    ISelectionChangedListener {
//	public void selectionChanged(SelectionChangedEvent arg0) {
//	    CmlEditor.this.selectionChanged();
//	}
//
//    }
//
//    protected void selectionChanged() {
//	if (getSelectionProvider() == null)
//	    return;
//	INode element = computeHighlightRangeSourceReference();
//
//	if (element != null)
//	    cmlOutLiner.setTreeSelection(element);
//
//    }
//
//    @Override
//    public void createPartControl(Composite parent) {
//	super.createPartControl(parent);
//	selectionChangeListener = new CmlSelectionChangeListener();
//	selectionChangeListener.install(this.getSelectionProvider());
//
//	IEditorInput input = getEditorInput();
//	IDocumentProvider documentProvider = getDocumentProvider();
//	IDocument doc = documentProvider.getDocument(input);
//	if (doc instanceof CmlDocument) {
//	    System.out.println("CML Document in the house.");
//	}
//    }
//
//    @Override
//    protected void initializeEditor() {
//	super.initializeEditor();
//	VdmSourceViewerConfiguration fVdmSourceViewer = getVdmSourceViewerConfiguration();
//	setSourceViewerConfiguration(fVdmSourceViewer);
//	setRulerContextMenuId(IVdmUiConstants.RULERBAR_ID);
//
//    }
//
//    @Override
//    protected void configureSourceViewerDecorationSupport(
//	    SourceViewerDecorationSupport support) {
//	super.configureSourceViewerDecorationSupport(support);
//
//	final String EDITOR_MATCHING_BRACKETS = "matchingBrackets";
//	final String EDITOR_MATCHING_BRACKETS_COLOR = "matchingBracketsColor";
//
//	char[] matchChars = { '(', ')', '[', ']' }; // which brackets to match
//	ICharacterPairMatcher matcher = new DefaultCharacterPairMatcher(
//		matchChars, IDocumentExtension3.DEFAULT_PARTITIONING);
//	support.setCharacterPairMatcher(matcher);
//	support.setMatchingCharacterPainterPreferenceKeys(
//		EDITOR_MATCHING_BRACKETS, EDITOR_MATCHING_BRACKETS_COLOR);
//
//	// Enable bracket highlighting in the preference store
//	IPreferenceStore store = getPreferenceStore();
//	store.setDefault(EDITOR_MATCHING_BRACKETS, true);
//	store.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, "128,128,128");
//    }
//
//    private CmlContentPageOutliner cmlOutLiner;
//
//    @Override
//    public Object getAdapter(Class required) {
//
//	if (IContentOutlinePage.class.equals(required)) {
//	    if (cmlOutLiner == null)
//		cmlOutLiner = createCmlOutliner();
//	    return cmlOutLiner;
//	}
//
//	return super.getAdapter(required);
//
//    }
//
//    private CmlContentPageOutliner createCmlOutliner() {
//
//	final CmlContentPageOutliner cmlOutliner = new CmlContentPageOutliner(
//		this);
//	if (getEditorInput() instanceof FileEditorInput) {
//	    FileEditorInput fei = (FileEditorInput) getEditorInput();
//	    CmlSourceUnit csu = CmlSourceUnit
//		    .getFromFileResource(fei.getFile());
//	    
//			// // FIXME if there is no AST, build it. This is a hack
////			 if (csu.getSourceAst() == null){
////			 try {
////			 ResourcesPlugin.getWorkspace().build(CmlIncrementalBuilder.FULL_BUILD,
////			new NullProgressMonitor());
////			 } catch (CoreException e) {
////			 // TODO Auto-generated catch block
////			 e.printStackTrace();
////			 }
////			 }
//			
//	    cmlOutliner.setInput(csu);
//	    csu.addChangeListener(new CmlSourceChangedListener() {
//
//		public void sourceChanged(CmlSourceUnit csu) {
//
//		    final Display curDisp = Display.getDefault();
//		    if (curDisp != null)
//			curDisp.syncExec(new Runnable() {
//			    public void run() {
//
//				cmlOutliner.refresh();
//
//			    }
//			});
//
//		}
//
//	    });
//	   
//	}
//	return cmlOutliner;
//    }
//
//    public CmlEditor() {
//	super();
//	setDocumentProvider(new CmlDocumentProvider());
//
//    }
//
//
//
//    protected INode computeHighlightRangeSourceReference() {
//
//	// FIXME if the AST is just the source node return null
//	ISourceViewer sourceViewer = getSourceViewer();
//	if (sourceViewer == null)
//	    return null;
//
//	StyledText styledText = sourceViewer.getTextWidget();
//	if (styledText == null)
//	    return null;
//
//	int caret = 0;
//	if (sourceViewer instanceof ITextViewerExtension5) {
//	    ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
//	    caret = extension.widgetOffset2ModelOffset(styledText
//		    .getCaretOffset());
//	} else {
//	    int offset = sourceViewer.getVisibleRegion().getOffset();
//	    caret = offset + styledText.getCaretOffset();
//	}
//	INode element = getElementAt(caret, false);
//
//	return element;
//    }
//
    protected INode getElementAt(int caret, boolean b) {
	FileEditorInput fei = (FileEditorInput) getEditorInput();
	INode r = null;
	CmlSourceUnit csu = CmlSourceUnit.getFromFileResource(fei.getFile());
	PSource ast = csu.getSourceAst();

	if (!astOk(ast))
	    return null;

	INodeFromCaret visitor = new INodeFromCaret(caret, ast);
	try {
	    ast.apply(visitor);
	    return visitor.getBestCandidate();
	} catch (AnalysisException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return r;
    }

    private boolean astOk(PSource ast) {
	if (null == ast)
	    return false;

	if (ast.getParagraphs().isEmpty())
	    return false;

	if (!CmlTypeChecker.Utils.isWellType(ast)){
		return false;
	}
	
	return true;
    }

}
