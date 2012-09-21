package eu.compassresearch.core.typechecker.api;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.node.INode;
import eu.compassresearch.ast.types.PType;

public interface TypeCheckQuestion
  {
    
    /**
     * Given a name lookup the corresponding type in the type environment
     * 
     * @param ident
     * @return
     */
    public abstract PType lookupType(LexIdentifierToken ident);
    
    /**
     * Add a defined type to the type environment.
     * 
     * @param ident
     * @param type
     */
    public abstract void addType(LexIdentifierToken ident, PType type);
    
    /**
     * Given a name lookup the corresponding type in the channel environment.
     * 
     * @param ident
     * @return
     */
    public abstract PDeclaration lookupChannel(LexIdentifierToken ident);
    
    /**
     * Add a mapping from the given name and channel declaration to the channel
     * environment.
     * 
     * @param name
     * @param channel
     */
    public abstract void addChannel(LexIdentifierToken name,
        PDeclaration channel);
    
    /**
     * Given a name lookup the corresponding variable definition for that name.
     * 
     * @param name
     * @return
     */
    public abstract PDefinition lookupVariable(LexIdentifierToken name);
    
    /**
     * Add mapping from the given name to the given variable definition to the
     * variable environment.
     * 
     * @param name
     * @param variable
     */
    public abstract void addVariable(LexIdentifierToken name,
        PDefinition variable);
    
    /**
     * Create a new scope. This only affects the variables evironment types and
     * channels are global.
     * 
     * @param surroundingDefinition
     *          - The definition that defines and encapsulates this scope
     * @return
     */
    public abstract TypeCheckQuestion newScope(PDefinition surroundingDefinition);
    
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
     *           - if the variable and type environment contains overlapping
     *           names this function may throw an analysis exception
     */
    public abstract org.overture.typechecker.Environment getOvertureEnvironment()
        throws AnalysisException;
    
  }
