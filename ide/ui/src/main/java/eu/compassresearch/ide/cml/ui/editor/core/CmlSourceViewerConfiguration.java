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
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;
import org.overture.ide.ui.editor.syntax.VdmColorProvider;

import eu.compassresearch.ide.cml.ui.editor.syntax.CmlCodeScanner;

public class CmlSourceViewerConfiguration extends VdmSourceViewerConfiguration
  {
    
    @Override
    protected ITokenScanner getVdmCodeScanner()
      {
        return new CmlCodeScanner(new VdmColorProvider());
      }
    
    private ContentAssistant assistant;
    
    @Override
    public synchronized IContentAssistant getContentAssistant(
        ISourceViewer sourceViewer)
      {
        if (assistant == null)
          {
            assistant = new ContentAssistant();
            assistant
                .setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
            assistant.setContentAssistProcessor(new CmlContentAssistProcessor(),
                IDocument.DEFAULT_CONTENT_TYPE);
            assistant.setAutoActivationDelay(0);
            assistant.enableAutoActivation(true);
            assistant
                .setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
            assistant
                .setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
          }
        return assistant;
      }
    
    @Override
    public IReconciler getReconciler(ISourceViewer sv)
      {
        MonoReconciler mr = new MonoReconciler(new CmlReconcilingStrategy(),
            false);
        mr.setDelay(1000);
        mr.install(sv);
        return mr;
      }
    
  }

