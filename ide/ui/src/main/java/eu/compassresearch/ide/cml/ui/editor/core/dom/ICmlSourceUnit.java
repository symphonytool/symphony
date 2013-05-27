package eu.compassresearch.ide.cml.ui.editor.core.dom;

import org.eclipse.core.resources.IFile;
import org.overture.ide.core.IVdmElement;

import eu.compassresearch.ast.program.PSource;

public interface ICmlSourceUnit extends IVdmElement
{
	
	public static final int CML_SPEC = 4;

	PSource getSourceAst();
	
	IFile getFile();
	
}
