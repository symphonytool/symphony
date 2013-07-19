package eu.compassresearch.ide.core.resources;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.core.IVdmElement;

import eu.compassresearch.ast.program.PSource;

public interface ICmlSourceUnit extends IVdmElement
{
	public static final int CML_SPEC = 4;

//	@Deprecated
//	PSource getSourceAst();

	public IFile getFile();

	public File getSystemFile();

	public List<INode> getParseList();
	
	@Deprecated
	public PSource getParseNode();
	
	public List<PDefinition> getParseListDefinitions();

	public boolean exists();

	public void clean();

	public abstract ICmlProject getProject();

	public abstract boolean hasParseTree();

	public abstract boolean hasParseErrors();

}
