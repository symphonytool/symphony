//package eu.compassresearch.core.typechecker;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.overture.ast.definitions.AExplicitOperationDefinition;
//import org.overture.ast.definitions.PDefinition;
//import org.overture.ast.definitions.SClassDefinition;
//import org.overture.ast.intf.lex.ILexIdentifierToken;
//import org.overture.ast.intf.lex.ILexNameToken;
//import org.overture.ast.node.INode;
//import org.overture.ast.typechecker.NameScope;
//import org.overture.ast.types.PType;
//import org.overture.typechecker.Environment;
//import org.overture.typechecker.EnvironmentSearchStrategy;
//import org.overture.typechecker.FlatEnvironment;
//import org.overture.typechecker.TypeCheckInfo;
//import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
//import org.overture.typechecker.util.HelpLexNameToken;
//
//import eu.compassresearch.ast.definitions.AChannelDefinition;
//import eu.compassresearch.ast.definitions.AChansetDefinition;
//import eu.compassresearch.ast.lex.CmlLexNameToken;
//import eu.compassresearch.core.typechecker.api.ITypeCheckQuestion;
//import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
//import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
//
///**
// * TypeCheckInfo tracks the three scopes we care about in CML. These are Variables - mapping from variable name to their
// * Definition (and type) Types - mapping from type-name to the actual PType Channels - mapping from channel-name to its
// * declaration (and carried type of any) Creating a new scope is the responsibility of this class.
// * 
// * @author rwl
// */
//public class CmlTypeCheckInfo extends TypeCheckInfo implements
//		ITypeCheckQuestion
//{
//
//	private final FlatEnvironment channels;
//
//	private final List<PDefinition> globalDefinitions;
//
//	public final String CML_SCOPE = "CML";
//	public final String DEFAULT_SCOPE = "";
//
//	private final ITypeIssueHandler issueHandler;
//
//	/**
//	 * Create a new Type Check Info at top level
//	 * 
//	 * @param issueHandler
//	 *            -- Where to report issues
//	 * @return a fresh Type Check Info instance at top level
//	 */
//	public static CmlTypeCheckInfo getNewTopLevelInstance(
//			ITypeCheckerAssistantFactory assistantFactory,
//			ITypeIssueHandler issueHandler, List<PDefinition> globalDefs,
//			List<PDefinition> channels)
//	{
//		return new CmlTypeCheckInfo(assistantFactory, issueHandler, globalDefs, channels);
//
//	}
//
//	private static class CmlEnvironmentSearchStrategy implements
//			EnvironmentSearchStrategy
//	{
//
//		@Override
//		public PDefinition findType(ILexNameToken name, String fromModule,
//				PDefinition enclosingDefinition, Environment outer,
//				Collection<PDefinition> currentDefinitions)
//		{
//			for (PDefinition d : currentDefinitions)
//			{
//				if (testEq(name, d, PDefinition.class))
//					return d;
//			}
//
//			while (outer != null)
//			{
//				currentDefinitions = outer.getDefinitions();
//				for (PDefinition d : currentDefinitions)
//				{
//					if (testEq(name, d, PDefinition.class))
//						return d;
//				}
//				outer = outer.getOuter();
//			}
//			return null;
//		}
//
//		@Override
//		public PDefinition findName(ILexNameToken name, NameScope scope,
//				PDefinition enclosingDefinition, Environment outer,
//				Collection<PDefinition> currentDefinitions)
//		{
//
//			for (PDefinition d : currentDefinitions)
//			{
//				if (testEq(name, d, PDefinition.class))
//					return d;
//			}
//
//			while (outer != null)
//			{
//				currentDefinitions = outer.getDefinitions();
//				for (PDefinition d : currentDefinitions)
//				{
//					if (testEq(name, d, PDefinition.class))
//						return d;
//				}
//				outer = outer.getOuter();
//			}
//			return null;
//		}
//
//		@Override
//		public PDefinition find(ILexIdentifierToken name,
//				PDefinition enclosingDefinition, Environment outer,
//				Collection<PDefinition> currentDefinitions)
//		{
//
//			for (PDefinition d : currentDefinitions)
//			{
//				if (testEq(name, d, PDefinition.class))
//					return d;
//			}
//
//			while (outer != null)
//			{
//				currentDefinitions = outer.getDefinitions();
//				for (PDefinition d : currentDefinitions)
//				{
//					if (testEq(name, d, PDefinition.class))
//						return d;
//				}
//				outer = outer.getOuter();
//			}
//			return null;
//		}
//
//	}
//
//	private CmlTypeCheckInfo(ITypeCheckerAssistantFactory assistantFactory,
//			ITypeIssueHandler issueHandler, List<PDefinition> globalDefs,
//			List<PDefinition> channels)
//	{
//		super(assistantFactory, new FlatEnvironment(assistantFactory, globalDefs, new CmlEnvironmentSearchStrategy()));
//		this.issueHandler = issueHandler;
//		this.channels = new FlatEnvironment(assistantFactory, channels, new CmlEnvironmentSearchStrategy());
//		this.globalDefinitions = globalDefs;
//	}
//
//	private CmlTypeCheckInfo(ITypeCheckerAssistantFactory assistantFactory,
//			FlatEnvironment channelSurounding,
//			org.overture.typechecker.Environment suroundingEnv,
//			ITypeIssueHandler issueHandler, List<PDefinition> globalDefs)
//	{
//		super(assistantFactory, new FlatEnvironment(assistantFactory, new LinkedList<PDefinition>(), suroundingEnv));
//		this.channels = channelSurounding;
//		this.issueHandler = issueHandler;
//		this.globalDefinitions = globalDefs;
//
//	}
//
//	@Override
//	public PType lookupType(ILexIdentifierToken ident)
//	{
//		ILexNameToken name = null;
//		if (!(ident instanceof ILexNameToken))
//			name = new CmlLexNameToken("", ident);
//		else
//			name = (ILexNameToken) ident;
//		PType res = null;
//		PDefinition def = lookup(name, PDefinition.class);
//		if (def == null)
//			return null;
//		res = def.getType();
//		return res;
//	}
//
//	private static boolean testEq(ILexIdentifierToken name, PDefinition d,
//			Class<?> astClass)
//	{
//		boolean isInstance = astClass.isInstance(d);
//		boolean sameName = HelpLexNameToken.isEqual(name, d.getName());
//		return (isInstance && sameName);
//	}
//
//	public PDefinition lookup(ILexIdentifierToken name, Class<?> astClass)
//	{
//		org.overture.typechecker.Environment cur = env;
//
//		while (cur != null)
//		{
//			List<PDefinition> defs = cur.getDefinitions();
//			for (PDefinition d : defs)
//			{
//				if (testEq(name, d, astClass))
//					return d;
//			}
//			cur = cur.getOuter();
//		}
//
//		return findGlobal(name, astClass);
//	}
//
//	private PDefinition findGlobal(ILexIdentifierToken name, Class<?> astClass)
//	{
//		for (PDefinition d : globalDefinitions)
//		{
//			if (testEq(name, d, astClass))
//				return d;
//		}
//		return null;
//	}
//
//	@Override
//	public void addType(ILexIdentifierToken ident, PDefinition typeDef)
//	{
//		if (ident == null)
//			throw new NullPointerException("Cannot add a type with null name");
//
//		PDefinition existingDef = lookup(ident, PDefinition.class);
//		boolean notSame = typeDef != existingDef;
//		if (existingDef != null && notSame)
//		{
//			typeDef.setType(issueHandler.addTypeError(typeDef, TypeErrorMessages.DUPLICATE_DEFINITION.customizeMessage(""
//					+ typeDef.getName(), "" + existingDef.getLocation())));
//			return;
//		}
//
//		if (env instanceof FlatEnvironment)
//		{
//			FlatEnvironment fenv = (FlatEnvironment) env;
//			fenv.add(typeDef);
//		}
//	}
//
//	@Override
//	public PDefinition lookupChannel(ILexIdentifierToken ident)
//	{
//		PDefinition res = null;
//		Environment chn = this.channels;
//		do
//		{
//			for (PDefinition def : chn.getDefinitions())
//			{
//				if (def instanceof AChannelDefinition)
//				{
//					// AChannelDefinition chanDef = (AChannelDefinition) def;
//
//					// for (ILexIdentifierToken id : chanDef.getSingleType().getIdentifiers())
//					// {
//					// CmlLexNameToken idName = new CmlLexNameToken("", id);
//					// CmlLexNameToken identName = new CmlLexNameToken("", ident);
//					// if (HelpLexNameToken.isEqual(idName, identName))
//					// return def;
//					// }
//				}
//
//				if (def instanceof AChansetDefinition)
//				{
//					CmlLexNameToken identName = new CmlLexNameToken("", ident);
//					AChansetDefinition chanset = (AChansetDefinition) def;
//					CmlLexNameToken chanName = new CmlLexNameToken("", chanset.getIdentifier());
//					if (HelpLexNameToken.isEqual(identName, chanName))
//						return def;
//
//				}
//
//			}
//			chn = chn.getOuter();
//		} while (chn != null);
//
//		return res;
//	}
//
//	@Override
//	public void addChannel(ILexIdentifierToken ident, PDefinition channel)
//	{
//		PDefinition chanDef = lookupChannel(ident);
//		if (chanDef != null)
//		{
//			channel.setType(issueHandler.addTypeError(channel, TypeErrorMessages.DUPLICATE_DEFINITION.customizeMessage(""
//					+ ident, "" + chanDef.getLocation())));
//			return;
//		}
//		channels.add(channel);
//	}
//
//	@Override
//	public PDefinition lookupVariable(ILexIdentifierToken ident)
//	{
//		if (ident instanceof ILexNameToken)
//		{
//			return env.findName((ILexNameToken) ident, NameScope.GLOBAL);
//		}
//		return null;
//	}
//
//	public PDefinition lookupCurrentScope(ILexIdentifierToken ident)
//	{
//		for (PDefinition d : env.getDefinitions())
//		{
//			if (HelpLexNameToken.isEqual(ident, d.getName()))
//				return d;
//		}
//		return null;
//	}
//
//	@Override
//	public void addVariable(ILexIdentifierToken ident, PDefinition variable)
//	{
//		if (ident == null)
//			return;
//		boolean isConstructor = false;
//		if (env instanceof FlatEnvironment)
//		{
//			FlatEnvironment fenv = (FlatEnvironment) env;
//			PDefinition d = lookupCurrentScope(ident);
//
//			if (variable instanceof AExplicitOperationDefinition)
//			{
//				isConstructor = ((AExplicitOperationDefinition) variable).getIsConstructor();
//			}
//
//			if (d != null && !isConstructor
//					&& d.getName().getOld() == variable.getName().getOld())
//			{
//				variable.setType(issueHandler.addTypeError(variable, TypeErrorMessages.DUPLICATE_DEFINITION.customizeMessage(variable
//						+ " " + variable.getLocation(), d + " "
//						+ d.getLocation())));
//				return;
//			}
//			fenv.add(variable);
//		}
//
//	}
//
//	public List<PDefinition> getDefinitions()
//	{
//		return env.getDefinitions();
//	}
//
//	public boolean hasChannelDuplicates(AChannelDefinition newChannel)
//	{
//
//		boolean hasDuplicates = false;
//		// run through all identifiers of new channel
//		// for (ILexIdentifierToken newChannelIdent : newChannel.getSingleType().getIdentifiers())
//		// {
//		// int count = 0;
//		// CmlLexNameToken newChannelIdentName = new CmlLexNameToken("", newChannelIdent);
//		//
//		// // run through each existing channels
//		// for (PDefinition def : channels.getDefinitions())
//		// {
//		//
//		// if (def instanceof AChannelDefinition)
//		// {
//		// AChannelDefinition existingChanDef = (AChannelDefinition) def;
//		// // compare identifiers
//		// // for (ILexIdentifierToken id : existingChanDef.getSingleType().getIdentifiers())
//		// // {
//		// // CmlLexNameToken existingChannelName = new CmlLexNameToken("", id);
//		// //
//		// // if (HelpLexNameToken.isEqual(existingChannelName, newChannelIdentName))
//		// // {
//		// // ++count;
//		// // if (count > 1)
//		// // {
//		// // newChannel.setType(issueHandler.addTypeError(newChannel.getType(),
//		// TypeErrorMessages.DUPLICATE_DEFINITION.customizeMessage(newChannelIdentName
//		// // + " "
//		// // + newChannelIdentName.getLocation(), existingChannelName
//		// // + "  "
//		// // + existingChannelName.getLocation())));
//		// // hasDuplicates = true;
//		// // }
//		// // }
//		// // }
//		// }
//		// }
//		// }
//		return hasDuplicates;
//	}
//
//	/**
//	 * Create an environment with this environment being the outer environment and the given definition being the
//	 * surrounding definition.
//	 */
//	@Override
//	public CmlTypeCheckInfo newScope(SClassDefinition def)
//	{
//		// Variables are scoped, types and channels are global (for now at
//		// least)
//		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.assistantFactory, this.channels, super.env, issueHandler, globalDefinitions);
//		res.env.setEnclosingDefinition(def);
//		return res;
//	}
//
//	/**
//	 * Create a new environment with this environment being its outer.
//	 * 
//	 * @return
//	 */
//	public CmlTypeCheckInfo newScope()
//	{
//		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.assistantFactory, channels, env, issueHandler, globalDefinitions);
//		res.scope = this.scope;
//		res.env.setEnclosingDefinition(env.getEnclosingDefinition());
//		return res;
//	}
//
//	static final Map<org.overture.ast.typechecker.NameScope, eu.compassresearch.ast.typechecker.NameScope> scopeMap;
//	static
//	{
//		scopeMap = new HashMap<org.overture.ast.typechecker.NameScope, eu.compassresearch.ast.typechecker.NameScope>();
//		scopeMap.put(NameScope.CLASSNAME, eu.compassresearch.ast.typechecker.NameScope.CLASSNAME);
//		scopeMap.put(NameScope.GLOBAL, eu.compassresearch.ast.typechecker.NameScope.GLOBAL);
//		scopeMap.put(NameScope.LOCAL, eu.compassresearch.ast.typechecker.NameScope.LOCAL);
//		scopeMap.put(NameScope.NAMES, eu.compassresearch.ast.typechecker.NameScope.NAMES);
//		scopeMap.put(NameScope.NAMESANDANYSTATE, eu.compassresearch.ast.typechecker.NameScope.NAMESANDANYSTATE);
//		scopeMap.put(NameScope.NAMESANDSTATE, eu.compassresearch.ast.typechecker.NameScope.NAMESANDSTATE);
//		scopeMap.put(NameScope.OLDSTATE, eu.compassresearch.ast.typechecker.NameScope.OLDSTATE);
//		scopeMap.put(NameScope.PROCESSNAME, eu.compassresearch.ast.typechecker.NameScope.PROCESSNAME);
//		scopeMap.put(NameScope.STATE, eu.compassresearch.ast.typechecker.NameScope.STATE);
//		scopeMap.put(NameScope.TYPENAME, eu.compassresearch.ast.typechecker.NameScope.TYPENAME);
//	}
//
//	@Override
//	public org.overture.typechecker.Environment getOvertureEnvironment()
//
//	{
//		return env;
//	}
//
//	@Override
//	public void updateContextNameToCurrentScope(INode n)
//	{
//		// TODO: Set the current scope on the node n.
//	}
//
//	public ITypeCheckQuestion newScope(
//			org.overture.typechecker.TypeCheckInfo current, PDefinition def)
//	{
//		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.assistantFactory, channels, env, issueHandler, this.globalDefinitions);
//		res.env.setEnclosingDefinition(def);
//		res.scope = this.scope;
//		return res;
//	}
//
//	public ITypeCheckQuestion newScope(
//			org.overture.typechecker.Environment env, PDefinition def)
//	{
//		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.assistantFactory, channels, env, issueHandler, this.globalDefinitions);
//		res.env.setEnclosingDefinition(def);
//
//		return res;
//	}
//
//	public CmlTypeCheckInfo emptyScope()
//	{
//		org.overture.typechecker.Environment newenv = new FlatEnvironment(assistantFactory, new LinkedList<PDefinition>());
//		FlatEnvironment newchannels = new FlatEnvironment(assistantFactory, new LinkedList<PDefinition>());
//		CmlTypeCheckInfo res = new CmlTypeCheckInfo(this.assistantFactory, newchannels, newenv, issueHandler, this.globalDefinitions);
//		return res;
//	}
//
// }
