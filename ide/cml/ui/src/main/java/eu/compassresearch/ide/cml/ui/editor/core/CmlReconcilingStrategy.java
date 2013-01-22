package eu.compassresearch.ide.cml.ui.editor.core;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
//import org.overture.ide.core.parser.SourceParserManager;

import eu.compassresearch.core.parser.ParserError;


public class CmlReconcilingStrategy implements IReconcilingStrategy {

	CmlDocument doc;
	
	public List<ParserError> errors;
	
	public void setDocument(IDocument document) {
		if (document instanceof CmlDocument)
			this.doc = CmlDocument.class.cast(document);
	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
	
		
		
	}

	public void reconcile(IRegion partition) {
		
//		SourceParserManager.getInstance().getSourceParser(project)
		
	}

}
