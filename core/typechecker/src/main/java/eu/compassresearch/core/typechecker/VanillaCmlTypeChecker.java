package eu.compassresearch.core.typechecker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.Dialect;
import org.overture.ast.types.PType;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.analysis.CollectGlobalStateClass;
import eu.compassresearch.core.typechecker.analysis.CollectGlobalStateClass.GlobalDefinitions;
import eu.compassresearch.core.typechecker.api.ICmlRootVisitor;
import eu.compassresearch.core.typechecker.api.ITypeComparator;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.util.CmlTCUtil;
import eu.compassresearch.core.typechecker.weeding.SetLocationVisitor;
import eu.compassresearch.core.typechecker.weeding.Weeding1;
import eu.compassresearch.core.typechecker.weeding.Weeding2;
import eu.compassresearch.core.typechecker.weeding.Weeding3UnfoldSingleDeclIdentifiers;

class VanillaCmlTypeChecker extends AbstractTypeChecker
{

	// ---------------------------------------------
	// -- Type Checker State
	// ---------------------------------------------m
	private List<PDefinition> globalDefinitions;
	private ICmlRootVisitor rootVisitor;

	private void initialize(ITypeIssueHandler issueHandler,
			ITypeComparator comparator)
	{
		if (issueHandler != null)
			this.issueHandler = issueHandler;
		else
			this.issueHandler = new CollectingIssueHandler();
		rootVisitor = new CmlRootVisitor(issueHandler, comparator);

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

	@SuppressWarnings("unchecked")
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
			// W: Stage 2 unfold identifiers in action definitions, parameter decl single type identifiers
			Weeding3UnfoldSingleDeclIdentifiers.apply(sourceForest);

			// Moved to parser Weeding4FixOperationTypes.apply(sourceForest);

			// Collect all Top-level entities
			GlobalDefinitions globalDefs = CollectGlobalStateClass.getGlobalRoot(sourceForest, issueHandler);
			this.globalDefinitions = new LinkedList<PDefinition>(globalDefs.definitions);

			// Create top-level CML-environment
			TypeCheckInfo cmlTopEnv = CmlTypeCheckInfo.getNewTopLevelInstance(new CmlTypeCheckerAssistantFactory(), issueHandler, globalDefinitions, new LinkedList<PDefinition>(globalDefs.channels));

			// Resolve everything before hand (Overture does this)
			PDefinitionListAssistantTC.typeResolve(this.globalDefinitions, (QuestionAnswerAdaptor<TypeCheckInfo, PType>) rootVisitor, cmlTopEnv);

			// Type check
			for (PSource src : sourceForest)
			{
				PType srcType = src.apply(rootVisitor, cmlTopEnv);
				if (!CmlTCUtil.successfulType(srcType))
					return false;
			}

		} catch (TypeCheckException tce)
		{
			// Overture Type Checker complaining
			issueHandler.addTypeError(tce.node, tce.location, tce.getMessage());
			return false;
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
