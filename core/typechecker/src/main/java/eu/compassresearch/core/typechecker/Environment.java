package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.Map;

import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.types.PType;

public class Environment {

	/**
	 * The enclosing Environment to search if a symbol is not found in this 
	 */
	protected final Environment outer;

	// private state
	private Map<LexIdentifierToken,PDefinition> map;

	/**
	 * Create an Empty top level Environment. 
	 */
	public Environment() {    
		this(null);
	}
	
	/**
	 * Create an enclosed Environment.
	 * @param outer - The enclosing environment
	 */
	public Environment(Environment outer)
	{
		this.outer = outer;
		this.map = new HashMap<LexIdentifierToken, PDefinition>();
	}
	
	/**
	 * Lookup the given identifier/name to find its definition.
	 * 
	 * @param name - The name of the identifier for which we need its definition.
	 * 
	 * @return null if the name is unresolved.
	 */
	public PDefinition lookupName(LexIdentifierToken name)
	{
		PDefinition def = map.get(name);
		if (def == null) 
			if (outer != null) def = outer.lookupName(name);
		return def;
	}
	
	
	/**
	 * Associate a declaration with a type in this environment.
	 *
	 * 
	 * @param delc - Declaration to add type for
	 * @param type - The type for decl in this environment.
	 */
	public void put(LexIdentifierToken name, PDefinition type)
	{
		map.put(name, type);
	}
}
