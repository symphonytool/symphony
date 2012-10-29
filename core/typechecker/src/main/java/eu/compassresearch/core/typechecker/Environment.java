package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.Map;

import org.overture.ast.lex.LexIdentifierToken;

import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class Environment<T> {

	/**
	 * The enclosing Environment to search if a symbol is not found in this
	 */
	protected final Environment<T> outer;

	// private state
	private Map<LexIdentifierToken, T> map;
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
	public Environment(Environment outer, TypeIssueHandler issueHandler) {
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

	// private static org.overture.ast.definitions.PDefinition
	// translateDefinition(
	// PDefinition cmlDef, TypeIssueHandler issueHandler)
	// {
	// CmlAstToOvertureAst transform = new CmlAstToOvertureAst(null,
	// issueHandler);
	// try
	// {
	// org.overture.ast.definitions.PDefinition ovtDef =
	// (org.overture.ast.definitions.PDefinition) ((Object) transform)
	// .defaultPDefinition(cmlDef);
	// return ovtDef;
	// } catch (org.overture.ast.analysis.AnalysisException ae)
	// {
	// ae.printStackTrace();
	// return null;
	// }
	// }
	//
	// public static LexLocation translateLexLocation(
	// org.overture.ast.lex.LexLocation ovtLocation)
	// {
	// return new LexLocation(ovtLocation.file, ovtLocation.module,
	// ovtLocation.startLine, ovtLocation.startPos, ovtLocation.endLine,
	// ovtLocation.endPos, ovtLocation.startOffset, ovtLocation.endOffset);
	// }
	//
	// private PDefinition lookupFromOvtName(LexNameToken name)
	// {
	// boolean old = false;
	// LexLocation location = translateLexLocation(name.location);
	// String stringName = name.getName();
	// LexIdentifierToken id = new LexIdentifierToken(stringName, old,
	// location);
	// // Cowboy code, it is typically a PDefinition ;)
	// PDefinition cmlDef = (PDefinition) lookupName(id);
	// return cmlDef;
	// }
	//
	// /**
	// * This method creates an Overture Typing environment backed by this Cml
	// * environment.
	// *
	// * Whenever a VDM/Overture name is to be resolved we find the symbol with
	// an
	// * identical name in this Cml Environment and translates the definition to
	// * Overture. The translated definition is returned.
	// *
	// * @return An Overture typing environment Back by this instance of a Cml
	// * environment.
	// */
	// public org.overture.typechecker.Environment getOvertureEnv()
	// {
	// final TypeIssueHandler theIssueHandler = this.issueHandler;
	// class CmlOvertureEnvironment extends
	// org.overture.typechecker.Environment
	// {
	//
	// private CmlOvertureEnvironment()
	// {
	// super(null);
	// }
	//
	// @Override
	// public org.overture.ast.definitions.PDefinition findName(
	// LexNameToken name, NameScope scope)
	// {
	// PDefinition cmlDef = lookupFromOvtName(name);
	//
	// if (cmlDef == null)
	// return null;
	//
	// return translateDefinition(cmlDef, theIssueHandler);
	// }
	//
	// @Override
	// public org.overture.ast.definitions.PDefinition findType(
	// LexNameToken name, String fromModule)
	// {
	// PDefinition cmlDef = lookupFromOvtName(name);
	//
	// if (cmlDef == null)
	// return null;
	//
	// return translateDefinition(cmlDef, theIssueHandler);
	// }
	//
	// @Override
	// public AStateDefinition findStateDefinition()
	// {
	// throw new UnsupportedOperationException("This we cannot do");
	// }
	//
	// @Override
	// public SClassDefinition findClassDefinition()
	// {
	// throw new UnsupportedOperationException("This we cannot do");
	// }
	//
	// @Override
	// public boolean isStatic()
	// {
	// return false;
	// }
	//
	// @Override
	// public void unusedCheck()
	// {
	//
	// }
	//
	// @Override
	// public boolean isVDMPP()
	// {
	// return true;
	// }
	//
	// @Override
	// public boolean isSystem()
	// {
	// return false;
	// }
	//
	// @Override
	// public Set<org.overture.ast.definitions.PDefinition> findMatches(
	// LexNameToken name)
	// {
	// Set<org.overture.ast.definitions.PDefinition> matchesFound = new
	// HashSet<org.overture.ast.definitions.PDefinition>();
	//
	// PDefinition cmlDef = lookupFromOvtName(name);
	// if (cmlDef != null)
	// {
	// matchesFound.add(translateDefinition(cmlDef,
	// theIssueHandler));
	// }
	//
	// return matchesFound;
	// }
	//
	// }
	// ;
	//
	// return new CmlOvertureEnvironment();
	// }
}
