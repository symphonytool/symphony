package eu.compassresearch.core.typechecker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.typechecker.Environment;
import org.overture.typechecker.PublicClassEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.analysis.CollectGlobalStateClass;
import eu.compassresearch.core.typechecker.analysis.CollectGlobalStateClass.GlobalDefinitions;
import eu.compassresearch.core.typechecker.api.ITypeComparator;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.version2.CmlVdmTypeCheckVisitor;
import eu.compassresearch.core.typechecker.weeding.SetLocationVisitor;
import eu.compassresearch.core.typechecker.weeding.Weeding1;
import eu.compassresearch.core.typechecker.weeding.Weeding2;
import eu.compassresearch.core.typechecker.weeding.Weeding3UnfoldSingleDeclIdentifiers;
import eu.compassresearch.core.typechecker.weeding.Weeding5RemoveInitialDefinitions;
import eu.compassresearch.core.typechecker.weeding.WeedingSkipActionToStmCleaner;
import eu.compassresearch.core.typechecker.weeding.WeedingStmCleaner;
import eu.compassresearch.core.typechecker.weeding.WeedingUnresolvedPathReplacement;

class VanillaCmlTypeChecker extends AbstractTypeChecker
{

	// ---------------------------------------------
	// -- Type Checker State
	// ---------------------------------------------m
	private List<PDefinition> globalDefinitions;

	// private ICmlRootVisitor rootVisitor;

	/**
	 * Simple assigning constructor with a bit of logic in initialize.
	 * 
	 * @param cmlSource
	 * @param typeComparator
	 * @param issueHandler
	 */
	public VanillaCmlTypeChecker(Collection<PSource> cmlSource,
			ITypeComparator typeComparator, ITypeIssueHandler issueHandler)
	{
		this.sourceForest = new LinkedList<PSource>();
		sourceForest.addAll(cmlSource);
		initialize(issueHandler, typeComparator);
	}

	private void initialize(ITypeIssueHandler issueHandler,
			ITypeComparator comparator)
	{
		if (issueHandler != null)
			this.issueHandler = issueHandler;
		else
			this.issueHandler = new CollectingIssueHandler();
		// rootVisitor = new CmlRootVisitor(issueHandler, comparator);

		Settings.release = Release.VDM_10;
		Settings.dialect = Dialect.VDM_PP;

		TypeChecker.addStatusListner(this.issueHandler);
	}

	// ---------------------------------------------
	// -- Public API to CML Type Checker
	// ---------------------------------------------
	/**
	 * This method is invoked by the command line tool when pretty printing the analysis name.
	 * 
	 * @return Pretty short name for this analysis.
	 */
	@Override
	public String getAnalysisName()
	{
		return "The CML Type Checker";
	}

	@Override
	void clear()
	{
		sourceForest = null;
	}

	@Override
	public boolean typeCheck()
	{
		// Top type checking

		// [1] Collect all static entities in order:
		// - Channels
		// - Channel Sets
		// - Types (including classes)
		// - Values
		// - Implicit Global Functions
		// - Explicit Global Functions
		// This constitudes the global environment

		// [2] Type Check Global Entities in order
		// - Types
		// - Values
		// - Implicit Global Functions
		// - Explicit Global Functions
		// - Classes
		// - Processes
		// In the global environment

		try
		{
			// Update LexLocation "file" entity on all nodes
			SetLocationVisitor.updateLocations(sourceForest);

			// Transform the AST before analysis
			// W: Stage 1 remove intermediate product types in functions
			Weeding1.apply(sourceForest);
			// W: Stage 2 remove all bracket types
			Weeding2.apply(sourceForest);
			// W: Stage 5 remove any initial definitions
			Weeding5RemoveInitialDefinitions.apply(sourceForest);
			// W: Stage 2 unfold identifiers in action definitions, parameter decl single type identifiers
			Weeding3UnfoldSingleDeclIdentifiers.apply(sourceForest);

			WeedingSkipActionToStmCleaner.apply(sourceForest);
			WeedingStmCleaner.apply(sourceForest);

			// TODO we may have to unfold state in processes to instance variables here

			// Moved to parser Weeding4FixOperationTypes.apply(sourceForest);

			// Collect all Top-level entities
			GlobalDefinitions globalDefs = CollectGlobalStateClass.getGlobalRoot(sourceForest, issueHandler);
			this.globalDefinitions = new LinkedList<PDefinition>(globalDefs.definitions);

			// Create top-level CML-environment
			// TypeCheckInfo cmlTopEnv = CmlTypeCheckInfo.getNewTopLevelInstance(new CmlTypeCheckerAssistantFactory(),
			// issueHandler, globalDefinitions, new LinkedList<PDefinition>(globalDefs.channels));

			// Resolve everything before hand (Overture does this)
			// PDefinitionListAssistantTC.typeResolve(this.globalDefinitions, (QuestionAnswerAdaptor<TypeCheckInfo,
			// PType>) rootVisitor, cmlTopEnv);

			overtureClassTc(sourceForest);

			// Type check
			// for (PSource src : sourceForest)
			// {
			// PType srcType = src.apply(rootVisitor, cmlTopEnv);
			// if (!CmlTCUtil.successfulType(srcType))
			// return false;
			// }

		} catch (AnalysisException ae)
		{
			// An expected anomaly was found
			ae.printStackTrace();
			throw new InternalException(0, ae.getMessage());
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new InternalException(0, e.getMessage());
		}

		return !issueHandler.hasErrors();
	}

	private void overtureClassTc(Collection<PSource> sourceForest)
	{
		final List<SClassDefinition> classes = exstractClasses(sourceForest);

		final List<PDefinition> globalDefs = filterCSP(globalDefinitions);

		ILexLocation location = null;
		AClassClassDefinition globalClass = AstFactory.newAClassClassDefinition(new LexNameToken("$global", new LexIdentifierToken("$global", false, location)), new LexNameList(), globalDefs);

		// insert global class first, it must be checked first do to the environment linking
		classes.add(0, globalClass);

		WeedingUnresolvedPathReplacement.apply(sourceForest, classes);
		// FIXME: check that operation bodies only contain the allowed subset

		// DotUtil.dot(classes);

		Settings.dialect = Dialect.VDM_PP;
		TypeChecker typeChecker = new org.overture.typechecker.ClassTypeChecker(classes)
		{
			@Override
			protected QuestionAnswerAdaptor<TypeCheckInfo, PType> getTypeCheckVisitor()
			{
				return new CmlVdmTypeCheckVisitor();
			}

			@Override
			protected Environment getAllClassesEnvronment()
			{
				Environment globalEnv = new FlatCheckedGlobalEnvironment(assistantFactory, globalDefs, NameScope.NAMESANDSTATE, null);
				Environment allClasses = new PublicClassEnvironment(assistantFactory, classes, globalEnv, null);
				return allClasses;
			}
		};
		typeChecker.typeCheck();
		// PrintWriter out = new PrintWriter(System.out);
		// TypeChecker.printErrors(out);
		// TypeChecker.printWarnings(out);
		// out.flush();
		TypeChecker.clearErrors();

	}

	public static List<PDefinition> filterCSP(List<PDefinition> definitions)
	{
		final List<PDefinition> globalDefs = new Vector<PDefinition>();
		for (PDefinition def : definitions)
		{
			if (def instanceof SFunctionDefinition
					|| def instanceof AInstanceVariableDefinition
					|| def instanceof AValueDefinition
					|| def instanceof ATypeDefinition)
			{
				globalDefs.add(def);
			} else if (def instanceof SClassDefinition)
			{
				// globalClasses.add((SClassDefinition) def);
			}
		}

		return globalDefs;
	}

	public List<SClassDefinition> exstractClasses(
			Collection<PSource> sourceForest)
	{
		final List<SClassDefinition> classes = new Vector<SClassDefinition>();
		for (PSource pSource : sourceForest)
		{
			try
			{
				pSource.apply(new DepthFirstAnalysisCMLAdaptor()
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void caseAClassClassDefinition(
							AClassClassDefinition node)
							throws AnalysisException
					{
						classes.add(node);
					}
				});
			} catch (AnalysisException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return classes;
	}

	/**
	 * Get warnings that occurred while type checking. The type check method will return true even though this returns
	 * an non-empty list.
	 * 
	 * @return list of CMLTypeWarnings
	 */

	public boolean hasErrors()
	{
		return issueHandler.hasErrors();
	}

	public boolean hasWarnings()
	{
		return issueHandler.hasWarnings();
	}

	public boolean hasIssues()
	{
		return issueHandler.hasIssues();
	}

}
