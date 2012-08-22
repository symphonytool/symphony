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

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.overture.ide.ui.editor.core.VdmEditor;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;

import eu.compassresearch.core.lexer.ParserError;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit.CmlSourceChangedListener;
import eu.compassresearch.ide.cml.ui.editor.syntax.CmlContentPageOutliner;

public class CmlEditor extends VdmEditor
  {
    
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
      }
    
    @Override
    public VdmSourceViewerConfiguration getVdmSourceViewerConfiguration()
      {
        return new CmlSourceViewerConfiguration();
      }
    
  }
