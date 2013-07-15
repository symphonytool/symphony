package eu.compassresearch.ide.ui.editor.core;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

//import org.overture.ide.core.parser.SourceParserManager;

public class CmlReconcilingStrategy implements IReconcilingStrategy
{
	CmlDocument doc;

	public void setDocument(IDocument document)
	{
		if (document instanceof CmlDocument)
			this.doc = CmlDocument.class.cast(document);
	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion)
	{
	}

	public void reconcile(IRegion partition)
	{
		// SourceParserManager.getInstance().getSourceParser(project)
	}
}
