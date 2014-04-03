package eu.compassresearch.ide.core.resources;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ide.core.IVdmElement;

import eu.compassresearch.ast.program.PSource;

/**
 * The ICmlSourceUnit interface represents a single file as source code for CML model. It exposes methods from the point
 * of view of a model and its related concepts (ASTs, type checks etc.) and has relatively few notions of files as
 * system resources.
 * 
 * @author ldc, kel
 */
public interface ICmlSourceUnit extends IVdmElement
{

	/** The Constant CML_SPEC. */
	public static final int CML_SPEC = 4;

	/**
	 * Gets the resource file resource corresponding to this source.
	 * 
	 * @return the {@link IFile} representing the file
	 */
	public IFile getFile();

	/**
	 * Gets the system resource file corresponding to this source.
	 * 
	 * @return the {@link File} representing the file
	 */
	public File getSystemFile();

	/**
	 * Gets the list of parsed definitions for this source unit.
	 * 
	 * @return the list of {@link INode} representing the parsed definitions.
	 */
	public List<INode> getParseList();

	/**
	 * Gets the source representing this parsed source.
	 * <p/>
	 * <b>Warning:</b> The use of {@link PSource} is discouraged. Use {@link #getParseList()} instead.
	 * 
	 * @return the {@link PSource} presenting this parsed source.
	 */
	@Deprecated
	public PSource getParseNode();

	/**
	 * Gets the list of parsed definitions for this source unit.
	 * 
	 * @return the list of {@link PDefinition} representing the parsed definitions.
	 */
	public List<PDefinition> getParseListDefinitions();

	/**
	 * Checks if this source unit exists, ie, it represents an actual file that exists.
	 * 
	 * @return true, if this source unit exists.
	 */
	public boolean exists();

	/**
	 * Clean this source by clearing its list of parsed definitions.
	 */
	public void clean();

	/**
	 * Gets the project this source unit belongs to.
	 * 
	 * @return the {@link ICmlProject} representing the project.
	 */
	public abstract ICmlProject getProject();

	/**
	 * Check if this source has a list of parsed definitions.
	 * 
	 * @return true, if the list of parsed definitions exists.
	 */
	public abstract boolean hasParseTree();

	/**
	 * Checks if this source has any parse errors.
	 * 
	 * @return true, if there are any parse errors in this source
	 */
	public abstract boolean hasParseErrors();

}
