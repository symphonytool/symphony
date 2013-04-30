package eu.compassresearch.core.typechecker.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

/**
 * The CML environment.
 * 
 * 
 * @author rwl
 * 
 */
public interface TypeCheckQuestion {

	/**
	 * Given a name lookup the corresponding type in the type environment
	 * 
	 * @param ident
	 * @return
	 */
	public abstract PType lookupType(ILexIdentifierToken ident);

	/**
	 * Add a defined type to the type environment.
	 * 
	 * @param ident
	 * @param type
	 */
	public abstract void addType(ILexIdentifierToken ident, PDefinition type);

	/**
	 * Given a name lookup the corresponding type in the channel environment.
	 * 
	 * @param ident
	 * @return
	 */
	public abstract PDefinition lookupChannel(ILexIdentifierToken ident);

	/**
	 * Add a mapping from the given name and channel declaration to the channel
	 * environment.
	 * 
	 * @param name
	 * @param channel
	 */
	public abstract void addChannel(ILexIdentifierToken name,
			PDefinition channel);

	/**
	 * Given a name lookup the corresponding variable definition for that name.
	 * 
	 * @param name
	 * @return
	 */
	public abstract PDefinition lookupVariable(ILexIdentifierToken name);

	/**
	 * Add mapping from the given name to the given variable definition to the
	 * variable environment.
	 * 
	 * @param name
	 * @param variable
	 */
	public abstract void addVariable(ILexIdentifierToken name,
			PDefinition variable);

	/**
	 * Create a new scope. This only affects the variables evironment types and
	 * channels are global.
	 * 
	 * @param surroundingDefinition
	 *            - The definition that defines and encapsulates this scope
	 * @return
	 */
	public abstract TypeCheckQuestion newScope(
			SClassDefinition surroundingDefinition);

	/**
	 * Takes an INode and set the module parameter to the current scope. The
	 * top-level scope for CML should be hardcoded to "Default" and a special
	 * scope named CML should be enforced for language specific constructs like
	 * basic types. For example basic types exists in the CML namespace thus we
	 * have CML`int, CML`bool etc...
	 * 
	 * @param treenode
	 */
	public abstract void updateContextNameToCurrentScope(INode treenode);

	/**
	 * Translate this environment to an appropriate Overture environment for
	 * type checking expressions in the current context.
	 * 
	 * @return Overture Environment
	 * @throws AnalysisException
	 *             - if the variable and type environment contains overlapping
	 *             names this function may throw an analysis exception
	 */
	public abstract org.overture.typechecker.Environment getOvertureEnvironment()
			throws AnalysisException;

	/**
	 * Get the global definitions currently in the environment.
	 * 
	 * TODO: Why?
	 * 
	 * @return
	 */
	public abstract PDefinition getGlobalClassDefinitions();

	/**
	 * Set the current global top-level class.
	 * 
	 * TODO: Would you ever replace the entire root environment ? This looks
	 * like an implementation specific thing shining through the interface !
	 * 
	 * @param globalRoot
	 */
	public abstract void setGlobalClassDefinitions(SClassDefinition globalRoot);
}
