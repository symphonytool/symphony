package eu.compassresearch.ide.ui.editor.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.part.FileEditorInput;
import org.overture.ide.ui.editor.core.VdmDocumentProvider;

import eu.compassresearch.ide.core.resources.CmlSourceUnit;

public class CmlDocumentProvider extends VdmDocumentProvider
{

	@Override
	protected IDocument createDocument(Object element) throws CoreException
	{

		IDocument document = super.createDocument(element);

		if (element instanceof FileEditorInput)
		{
			if (document instanceof CmlDocument)
			{
				FileEditorInput fei = (FileEditorInput) element;
				IFile file = fei.getFile();
				((CmlDocument) document).setCmlSourceUnit(CmlSourceUnit.getFromFileResource(file));
				return document;
			}
		}
		return document;
	}

	@Override
	protected IDocument createEmptyDocument()
	{
		return new CmlDocument();
	}

}
