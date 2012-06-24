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

import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.overture.ide.ui.editor.core.VdmReconcilingStrategy;
import org.overture.ide.ui.editor.core.VdmSourceViewerConfiguration;
import org.overture.ide.ui.editor.syntax.VdmColorProvider;

import eu.compassresearch.ide.cml.ui.editor.syntax.CmlCodeScanner;



public class CmlSourceViewerConfiguration extends
		VdmSourceViewerConfiguration {

	
	
	@Override
	protected ITokenScanner getVdmCodeScanner() {
		return new CmlCodeScanner(new VdmColorProvider());
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {		
//		ContentAssistant  assistant = new VdmPpContentAssistant();
//		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
//		return assistant;
		return null;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sv) {
		MonoReconciler mr = new MonoReconciler(new CmlReconcilingStrategy(), false);
		//MonoReconciler mr = new MonoReconciler(new VdmReconcilingStrategy()/*CmlReconcilingStrategy()*/, false);
		mr.setDelay(1000);
		mr.install(sv);
		return mr;
	}
	
	
	
	
	
}
