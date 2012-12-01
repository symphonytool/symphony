package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;

import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class Environment<T> extends org.overture.typechecker.FlatEnvironment {

	/**
	 * The enclosing Environment to search if a symbol is not found in this
	 */
	protected final Environment<T> outer;

	// private state
	private Map<LexIdentifierToken, T> map;
	@SuppressWarnings("unused")
	private final TypeIssueHandler issueHandler;

	/**
	 * Create an Empty top level Environment.
	 */
	public Environment(TypeIssueHandler issueHandler) {
		this(null, issueHandler);

	}

	/**
	 * Create an enclosed Environment.
	 * 
	 * @param outer
	 *            - The enclosing environment
	 */
	public Environment(Environment<T> outer, TypeIssueHandler issueHandler) {
		super(new LinkedList<PDefinition>());
		this.outer = outer;
		this.map = new HashMap<LexIdentifierToken, T>();
		this.issueHandler = issueHandler;
	}

	/**
	 * Lookup the given identifier/name to find its definition.
	 * 
	 * @param name
	 *            - The name of the identifier for which we need its definition.
	 * 
	 * @return null if the name is unresolved.
	 */
	public T lookupName(LexIdentifierToken name) {
		T def = map.get(name);
		if (def == null)
			if (outer != null)
				def = outer.lookupName(name);
		return def;
	}

	/**
	 * Associate a declaration with a type in this environment.
	 * 
	 * 
	 * @param delc
	 *            - Declaration to add type for
	 * @param type
	 *            - The type for decl in this environment.
	 */
	public void put(LexIdentifierToken name, T type) {
		map.put(name, type);
	}
}
