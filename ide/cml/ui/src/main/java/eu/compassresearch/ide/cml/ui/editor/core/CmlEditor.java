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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.overture.ast.node.INode;
import org.overture.ide.ui.IVdmUiConstants;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;

import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit.CmlSourceChangedListener;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlContentPageOutliner;

public class CmlEditor extends TextEditor
  {
    
    private AbstractSelectionChangedListener selectionChangeListener;
    
    private class CmlSelectionChangeListener extends
        AbstractSelectionChangedListener implements ISelectionChangedListener
      {
        @Override
        public void selectionChanged(SelectionChangedEvent arg0)
          {
            CmlEditor.this.selectionChanged();
          }
        
      }
    
    protected INode computeHighlightRangeSourceReference()
      {
        ISourceViewer sourceViewer = getSourceViewer();
        if (sourceViewer == null)
          return null;
        
        StyledText styledText = sourceViewer.getTextWidget();
        if (styledText == null)
          return null;
        
        int caret = 0;
        if (sourceViewer instanceof ITextViewerExtension5)
          {
            ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
            caret = extension.widgetOffset2ModelOffset(styledText
                .getCaretOffset());
          } else
          {
            int offset = sourceViewer.getVisibleRegion().getOffset();
            caret = offset + styledText.getCaretOffset();
          }
        INode element = getElementAt(caret, false);
        
        return element;
      }
    
    private INode getElementAt(int caret, boolean b)
      {
        return null;
      }
    
    protected void selectionChanged()
      {
        if (getSelectionProvider() == null)
          return;
        INode element = computeHighlightRangeSourceReference();
        // if
        // (getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE))
        synchronizeOutlinePage(element);
        // if (fIsBreadcrumbVisible && fBreadcrumb != null &&
        // !fBreadcrumb.isActive())
        // setBreadcrumbInput(element);
        setSelection(element, false);
        // if (!fSelectionChangedViaGotoAnnotation)
        // updateStatusLine();
        // fSelectionChangedViaGotoAnnotation= false;
        
      }
    
    private void setSelection(INode element, boolean b)
      {
        // TODO Auto-generated method stub
        System.out.println("TODO: We need to add selection of the outline.");
      }
    
    private void synchronizeOutlinePage(INode element)
      {
        // TODO Auto-generated method stub
        System.out.println("TODO: Out line synchronization is missing");
      }
    
    @Override
    public void createPartControl(Composite parent)
      {
        super.createPartControl(parent);
        selectionChangeListener = new CmlSelectionChangeListener();
        selectionChangeListener.install(this.getSelectionProvider());
        
        IEditorInput input = getEditorInput();
        IDocumentProvider documentProvider = getDocumentProvider();
        IDocument doc = documentProvider.getDocument(input);
        if (doc instanceof CmlDocument)
          {
            System.out.println("CML Document in the house.");
          }
      }
    
    @Override
    protected void initializeEditor()
      {
        super.initializeEditor();
        VdmSourceViewerConfiguration fVdmSourceViewer = getVdmSourceViewerConfiguration();
        setSourceViewerConfiguration(fVdmSourceViewer);
        setRulerContextMenuId(IVdmUiConstants.RULERBAR_ID);
        
      }
    
    private IContentOutlinePage cmlOutLiner;
    
    @Override
    public Object getAdapter(Class required)
      {
        
        if (IContentOutlinePage.class.equals(required))
          {
            if (cmlOutLiner == null)
              cmlOutLiner = createCmlOutliner();
            return cmlOutLiner;
          }
        
        return super.getAdapter(required);
        
      }
    
    private IContentOutlinePage createCmlOutliner()
      {
        
        final CmlContentPageOutliner cmlOutliner = new CmlContentPageOutliner();
        if (getEditorInput() instanceof FileEditorInput)
          {
            FileEditorInput fei = (FileEditorInput) getEditorInput();
            CmlSourceUnit csu = CmlSourceUnit
                .getFromFileResource(fei.getFile());
            cmlOutliner.setInput(csu);
            csu.addChangeListener(new CmlSourceChangedListener()
              {
                
                @Override
                public void sourceChanged(CmlSourceUnit csu)
                  {
                    cmlOutliner.refresh();
                  }
                
              });
            csu.addChangeListener(new CmlSourceChangedListener()
              {
                
                public void sourceChanged(CmlSourceUnit csu)
                  {
                    for (final ParserError pe : csu.getErrors())
                      {
                        Display.getDefault().asyncExec(new Runnable()
                          {
                            public void run()
                              {
                                CmlEditor.this.setHighlightRange(pe.offset,
                                    pe.otext.length(), true);
                                CmlEditor.this.getSourceViewer()
                                    .setSelectedRange(pe.offset,
                                        pe.otext.length());
                                CmlEditor.this.getSourceViewer()
                                    .showAnnotations(true);
                                
                              }
                          });
                      }
                  }
              });
          }
        return cmlOutliner;
      }
    
    public CmlEditor()
      {
        super();
        setDocumentProvider(new CmlDocumentProvider());
      }
    
    public VdmSourceViewerConfiguration getVdmSourceViewerConfiguration()
      {
        return new CmlSourceViewerConfiguration();
      }
    
  }
