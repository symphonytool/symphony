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

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ide.ui.IVdmUiConstants;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.builder.CmlIncrementalBuilder;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit.CmlSourceChangedListener;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlContentPageOutliner;
import eu.compassresearch.ide.cml.ui.editor.syntax.INodeFromCaret;

public class CmlEditor extends TextEditor {

    private AbstractSelectionChangedListener selectionChangeListener;

    @Override
    public void dispose() {
	super.dispose();
    }

    @Override
    protected void doSetInput(IEditorInput input) throws CoreException {
	super.doSetInput(input);
	ICommandService commandService = (ICommandService) PlatformUI
		.getWorkbench().getService(ICommandService.class);
	try {
	    Command cmd = commandService
		    .getCommand("org.eclipse.ui.project.build");
	    cmd.executeWithChecks(new ExecutionEvent());
	} catch (Exception exception) {
	}

    }

    private class CmlSelectionChangeListener extends
	    AbstractSelectionChangedListener implements
	    ISelectionChangedListener {
	public void selectionChanged(SelectionChangedEvent arg0) {
	    CmlEditor.this.selectionChanged();
	}

    }

    protected void selectionChanged() {
	if (getSelectionProvider() == null)
	    return;
	INode element = computeHighlightRangeSourceReference();

	if (element != null)
	    cmlOutLiner.setTreeSelection(element);

    }

    @Override
    public void createPartControl(Composite parent) {
	super.createPartControl(parent);
	selectionChangeListener = new CmlSelectionChangeListener();
	selectionChangeListener.install(this.getSelectionProvider());

	IEditorInput input = getEditorInput();
	IDocumentProvider documentProvider = getDocumentProvider();
	IDocument doc = documentProvider.getDocument(input);
	if (doc instanceof CmlDocument) {
	    System.out.println("CML Document in the house.");
	}
    }

    @Override
    protected void initializeEditor() {
	super.initializeEditor();
	VdmSourceViewerConfiguration fVdmSourceViewer = getVdmSourceViewerConfiguration();
	setSourceViewerConfiguration(fVdmSourceViewer);
	setRulerContextMenuId(IVdmUiConstants.RULERBAR_ID);

    }

    @Override
    protected void configureSourceViewerDecorationSupport(
	    SourceViewerDecorationSupport support) {
	super.configureSourceViewerDecorationSupport(support);

	final String EDITOR_MATCHING_BRACKETS = "matchingBrackets";
	final String EDITOR_MATCHING_BRACKETS_COLOR = "matchingBracketsColor";

	char[] matchChars = { '(', ')', '[', ']' }; // which brackets to match
	ICharacterPairMatcher matcher = new DefaultCharacterPairMatcher(
		matchChars, IDocumentExtension3.DEFAULT_PARTITIONING);
	support.setCharacterPairMatcher(matcher);
	support.setMatchingCharacterPainterPreferenceKeys(
		EDITOR_MATCHING_BRACKETS, EDITOR_MATCHING_BRACKETS_COLOR);

	// Enable bracket highlighting in the preference store
	IPreferenceStore store = getPreferenceStore();
	store.setDefault(EDITOR_MATCHING_BRACKETS, true);
	store.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, "128,128,128");
    }

    private CmlContentPageOutliner cmlOutLiner;

    @Override
    public Object getAdapter(Class required) {

	if (IContentOutlinePage.class.equals(required)) {
	    if (cmlOutLiner == null)
		cmlOutLiner = createCmlOutliner();
	    return cmlOutLiner;
	}

	return super.getAdapter(required);

    }

    private CmlContentPageOutliner createCmlOutliner() {

	final CmlContentPageOutliner cmlOutliner = new CmlContentPageOutliner(
		this);
	if (getEditorInput() instanceof FileEditorInput) {
	    FileEditorInput fei = (FileEditorInput) getEditorInput();
	    CmlSourceUnit csu = CmlSourceUnit
		    .getFromFileResource(fei.getFile());
	    
	    // if there is no AST, build it. This is a hack
	    if (csu.getSourceAst() == null){
	    	try {
				ResourcesPlugin.getWorkspace().build(CmlIncrementalBuilder.FULL_BUILD, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    cmlOutliner.setInput(csu);
	    csu.addChangeListener(new CmlSourceChangedListener() {

		public void sourceChanged(CmlSourceUnit csu) {

		    final Display curDisp = Display.getDefault();
		    if (curDisp != null)
			curDisp.syncExec(new Runnable() {
			    public void run() {

				cmlOutliner.refresh();

			    }
			});

		}

	    });
	   
	}
	return cmlOutliner;
    }

    public CmlEditor() {
	super();
	setDocumentProvider(new CmlDocumentProvider());

    }

    public VdmSourceViewerConfiguration getVdmSourceViewerConfiguration() {
	return new CmlSourceViewerConfiguration();
    }

    protected INode computeHighlightRangeSourceReference() {

	// FIXME if the AST is just the source node return null
	ISourceViewer sourceViewer = getSourceViewer();
	if (sourceViewer == null)
	    return null;

	StyledText styledText = sourceViewer.getTextWidget();
	if (styledText == null)
	    return null;

	int caret = 0;
	if (sourceViewer instanceof ITextViewerExtension5) {
	    ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
	    caret = extension.widgetOffset2ModelOffset(styledText
		    .getCaretOffset());
	} else {
	    int offset = sourceViewer.getVisibleRegion().getOffset();
	    caret = offset + styledText.getCaretOffset();
	}
	INode element = getElementAt(caret, false);

	return element;
    }

    private INode getElementAt(int caret, boolean b) {
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
