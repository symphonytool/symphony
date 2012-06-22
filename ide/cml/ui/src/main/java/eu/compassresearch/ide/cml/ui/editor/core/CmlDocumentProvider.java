package eu.compassresearch.ide.cml.ui.editor.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.overture.ide.ui.editor.core.VdmDocumentProvider;

public class CmlDocumentProvider extends VdmDocumentProvider {

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		return super.createDocument(element);
	}

	@Override
	protected IDocument createEmptyDocument() {
		return new CmlDocument();
	}

	
	
}
