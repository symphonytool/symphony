package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.typechecker.FlatEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.util.HelpLexNameToken;

import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * TypeCheckInfo tracks the three scopes we care about in CML. These are
 * Variables - mapping from variable name to their Definition (and type) Types -
 * mapping from type-name to the actual PType Channels - mapping from
 * channel-name to its declaration (and carried type of any)
 * 
 * Creating a new scope is the responsibility of this class.
 * 
 * @author rwl
 * 
 */
class CmlTypeCheckInfo extends TypeCheckInfo implements
TypeCheckQuestion {

	private final Environment<PDefinition> channels;

	private SClassDefinition globalClassDefinition;

	public final String CML_SCOPE = "CML";
	public final String DEFAULT_SCOPE = "Default";

	private final TypeIssueHandler issueHandler;


	/**
	 * Create a new Type Check Info at top level
	 * 
	 * 
	 * @param issueHandler
	 *            -- Where to report issues
	 * @return a fresh Type Check Info instance at top level
	 */
	public static CmlTypeCheckInfo getNewTopLevelInstance(
			TypeIssueHandler issueHandler, AClassClassDefinition globalClassDef) {
		return new CmlTypeCheckInfo(issueHandler, globalClassDef);

	}

	private CmlTypeCheckInfo(TypeIssueHandler issueHandler,
			SClassDefinition globalDefs) {
		super(new FlatEnvironment(new LinkedList<PDefinition>()));
		this.issueHandler = issueHandler;
		this.channels = new Environment<PDefinition>(issueHandler);
		this.globalClassDefinition = globalDefs;
	}

	private CmlTypeCheckInfo(Environment<PDefinition> channelSurounding,
			org.overture.typechecker.Environment suroundingEnv,
			TypeIssueHandler issueHandler, SClassDefinition globalDefs) {
		super(new FlatEnvironment(new LinkedList<PDefinition>(), suroundingEnv));
		this.channels = channelSurounding;
		this.issueHandler = issueHandler;
		this.globalClassDefinition = globalDefs;
		
	}

	@Override
	public PType lookupType(LexIdentifierToken ident) {
		if (ident instanceof LexNameToken) {
			PDefinition typeDef = env.findType((LexNameToken) ident,
					DEFAULT_SCOPE);
			if (typeDef != null)
				return typeDef.getType();
		}
		return null;
	}



	public PDefinition lookup(LexIdentifierToken name, Class<?> astClass)
	{
		org.overture.typechecker.Environment cur = env;

		while(cur != null)
		{
			List<PDefinition> defs = cur.getDefinitions();
			for(PDefinition d :defs)
				
				if (astClass.isInstance(d) && HelpLexNameToken.isEqual(name, d.getName()))
					return d;
			cur = cur.getOuter();
		}
		
		return null;
	}

	@Override
	public void addType(LexIdentifierToken ident, PDefinition typeDef) {
		if (ident == null) throw new NullPointerException("Cannot add a type with null name");
		if (env instanceof FlatEnvironment) {
			FlatEnvironment fenv = (FlatEnvironment) env;
			fenv.add(typeDef);
		}
	}

	@Override
	public PDefinition lookupChannel(LexIdentifierToken ident) {
		return channels.lookupName(ident);
	}

	@Override
	public void addChannel(LexIdentifierToken ident, PDefinition channel) {
		channels.put(ident, channel);
	}

	@Override
	public PDefinition lookupVariable(LexIdentifierToken ident) {
		if (ident instanceof LexNameToken) {

			return env.findName((LexNameToken) ident, NameScope.GLOBAL);
		}
		return null;
	}

	@Override
	public void addVariable(LexIdentifierToken ident, PDefinition variable) {
		if (env instanceof FlatEnvironment) {
			FlatEnvironment fenv = (FlatEnvironment) env;
			fenv.add(variable);
		}
	}


	public List<PDefinition> getDefinitions()
	{
		return env.getDefinitions();
	}


	/**
	 * Create an environment with this environment being the outer environment 
	 * and the given definition being the surrounding definition.
	 */
	@Override
	public CmlTypeCheckInfo newScope(SClassDefinition def) {
		// Variables are scoped, types and channels are global (for now at
		// least)
		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.channels, super.env,
				issueHandler, globalClassDefinition);
		res.env.setEnclosingDefinition(def);
		return res;
	}

	/**
	 * Create a new environment with this environment being its outer.
	 * @return
	 */
	public CmlTypeCheckInfo newScope()
	{
		CmlTypeCheckInfo res = new CmlTypeCheckInfo(channels,env,issueHandler,globalClassDefinition);
		return res;
	}

	static final Map<org.overture.ast.typechecker.NameScope, eu.compassresearch.ast.typechecker.NameScope> scopeMap;
	static {
		scopeMap = new HashMap<org.overture.ast.typechecker.NameScope, eu.compassresearch.ast.typechecker.NameScope>();
		scopeMap.put(NameScope.CLASSNAME,
				eu.compassresearch.ast.typechecker.NameScope.CLASSNAME);
		scopeMap.put(NameScope.GLOBAL,
				eu.compassresearch.ast.typechecker.NameScope.GLOBAL);
		scopeMap.put(NameScope.LOCAL,
				eu.compassresearch.ast.typechecker.NameScope.LOCAL);
		scopeMap.put(NameScope.NAMES,
				eu.compassresearch.ast.typechecker.NameScope.NAMES);
		scopeMap.put(NameScope.NAMESANDANYSTATE,
				eu.compassresearch.ast.typechecker.NameScope.NAMESANDANYSTATE);
		scopeMap.put(NameScope.NAMESANDSTATE,
				eu.compassresearch.ast.typechecker.NameScope.NAMESANDSTATE);
		scopeMap.put(NameScope.OLDSTATE,
				eu.compassresearch.ast.typechecker.NameScope.OLDSTATE);
		scopeMap.put(NameScope.PROCESSNAME,
				eu.compassresearch.ast.typechecker.NameScope.PROCESSNAME);
		scopeMap.put(NameScope.STATE,
				eu.compassresearch.ast.typechecker.NameScope.STATE);
		scopeMap.put(NameScope.TYPENAME,
				eu.compassresearch.ast.typechecker.NameScope.TYPENAME);
	}

	@Override
	public org.overture.typechecker.Environment getOvertureEnvironment()

	{
		return env;
	}

	@Override
	public void updateContextNameToCurrentScope(INode n) {
		// TODO: Set the current scope on the node n.
	}

	@Override
	public SClassDefinition getGlobalClassDefinitions() {
		return this.globalClassDefinition;
	}

	@Override
	public void setGlobalClassDefinitions(SClassDefinition globalRoot) {
		this.globalClassDefinition = globalRoot;
	}

	public TypeCheckQuestion newScope(
			org.overture.typechecker.TypeCheckInfo current, PDefinition def) {
		CmlTypeCheckInfo res = new CmlTypeCheckInfo(channels,env,issueHandler, this.globalClassDefinition);
		res.env.setEnclosingDefinition(def);
		return res;
	}

	public TypeCheckQuestion newScope(
			org.overture.typechecker.Environment env, PDefinition def) {
		CmlTypeCheckInfo res = new CmlTypeCheckInfo(channels,env,issueHandler, this.globalClassDefinition);
		res.env.setEnclosingDefinition(def);

		return res;
	}

}
