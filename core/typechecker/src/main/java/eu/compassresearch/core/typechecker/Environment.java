package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.Map;

import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.PType;

public class Environment {

	/**
	 * The enclosing Environment to search if a symbol is not found in this 
	 */
	protected final Environment outer;

	// private state
	private Map<PDeclaration,PType> map;
	private PDefinition enclosingDefinition;

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
		this.map = new HashMap<PDeclaration, PType>();
	}
	
	/**
	 * Create an enclosed environment linking to the enclosing definition.
	 * 
	 * @param outer - The surrounding environment
	 * @param decl  - The enclosing definition (class, function or operation)
	 */
	public Environment(Environment outer, PDefinition decl)
	{
		this(outer);
		enclosingDefinition = decl;
	}
	
	/**
	 * Associate a declaration with a type in this environment.
	 *
	 * 
	 * @param delc - Declaration to add type for
	 * @param type - The type for decl in this environment.
	 */
	public void put(PDeclaration decl, PType  type)
	{
		map.put(decl, type);
	}
}
