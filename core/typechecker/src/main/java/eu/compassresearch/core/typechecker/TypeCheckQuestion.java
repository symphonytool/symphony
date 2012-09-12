package eu.compassresearch.core.typechecker;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
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
     * @return
     */
    public abstract TypeCheckQuestion newScope();
    
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
    
    /**
     * Given two types, first and second, determine whether or not first <:
     * second.
     * 
     * This method determines the sub type relation between two types. It is
     * denoted: "A <: B" and we say A is a sub type of B.
     * 
     * The Sub type relation defines a partial ordering on the set of types:
     * 
     * The Sub type relation is reflexive meaning A <: A
     * 
     * The Sub type relation is transitive meaning A <: B /\ B <: C => A <: C
     * 
     * The Sub type relation is antisymmetric meaning A <: B /\ B <: A <=> A ==
     * B
     * 
     * @return true <=> A <: B, false <=> not A <: B
     * 
     * 
     * @param first
     * @param second
     * @return
     */
    public abstract boolean isFirstSubTypeOfSecond(PType first, PType second);
  }
