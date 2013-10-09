package eu.compassresearch.core.typechecker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.core.typechecker.CollectGlobalStateClass.GlobalDefinitions;
import eu.compassresearch.core.typechecker.api.CmlRootVisitor;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.weeding.Weeding1;
import eu.compassresearch.core.typechecker.weeding.Weeding2;

class VanillaCmlTypeChecker extends AbstractTypeChecker
{

	// ---------------------------------------------
	// -- Type Checker State
	// ---------------------------------------------m
	private List<PDefinition> globalDefinitions;
	private CmlRootVisitor rootVisitor;

	private void initialize(TypeIssueHandler issueHandler,
			TypeComparator comparator)
	{
		if (issueHandler != null)
			this.issueHandler = issueHandler;
		else
			this.issueHandler = new CollectingIssueHandler(RegistryFactory.getInstance().getRegistry());
		rootVisitor = new eu.compassresearch.core.typechecker.CmlRootVisitor(issueHandler, comparator);
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
			TypeComparator typeComparator, TypeIssueHandler issueHandler)
	{
		this.sourceForest = new LinkedList<PSource>();
		sourceForest.addAll(cmlSource);
		initialize(issueHandler, typeComparator);
	}

	/**
	 * Run the type checker. This will update the source(s) this type checker instance was constructed with.
	 * 
	 * @return - Returns true if the entire tree could be type checked without errors.
	 * @Override public boolean typeCheck() { // Top type checking // [1] Collect all static entities in order: // -
	 *           Channels // - Channel Sets // - Types (including classes) // - Values // - Implicit Global Functions //
	 *           - Explicit Global Functions // This constitudes the global environment // [2] Type Check Global
	 *           Entities in order // - Types // - Values // - Implicit Global Functions // - Explicit Global Functions
	 *           // - Classes // - Processes // In the global environment
	 *           org.overture.typechecker.TypeCheckInfo.clearContext(); // update locations
	 *           SetLocationVisitor.updateLocations(sourceForest); try {
	 *           eu.compassresearch.core.typechecker.CmlTypeCheckInfo info =
	 *           eu.compassresearch.core.typechecker.CmlTypeCheckInfo .getNewTopLevelInstance(this.issueHandler,
	 *           globalRoot); try { // Collect global values, global types and global functions globalRoot =
	 *           CollectGlobalStateClass.getGlobalRoot( this.sourceForest, issueHandler, info);
	 *           info.env.setEnclosingDefinition(globalRoot); info.scope = NameScope.GLOBAL; PType globalRootType =
	 *           ((TCDeclAndDefVisitor) rootDefPhaseVisitor) .typeCheckOvertureClass(globalRoot, info); if
	 *           (!TCDeclAndDefVisitor.successfulType(globalRootType)) { issueHandler.addTypeError(globalRoot,
	 *           TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS .customizeMessage("Global Definitions")); return false; }
	 *           // Add all global definitions to the environment for (PDefinition def : globalRoot.getDefinitions()) {
	 *           List<PDefinition> l = TCDeclAndDefVisitor .handleDefinitionsForOverture(def); if (l != null) for
	 *           (PDefinition dd : l) { if (dd instanceof ATypeDefinition) info.addType(dd.getName(), dd); else { if (dd
	 *           instanceof AValueDefinition) { AValueDefinition vdd = (AValueDefinition) dd; List<PDefinition> list =
	 *           PPatternAssistantTC .getDefinitions(vdd.getPattern(), dd.getType(), NameScope.LOCAL); for (PDefinition
	 *           d : list) info.addVariable(d.getName(), d); } else info.addVariable(dd.getName(), dd); } } } } catch
	 *           (AnalysisException e) { e.printStackTrace(); } // Check the channels for (PSource s : sourceForest) {
	 *           for (PDefinition paragraph : s.getParagraphs()) { if (paragraph instanceof AChannelsDefinition ||
	 *           paragraph instanceof AChansetsDefinition) { try { PType type = paragraph.apply(rootDefPhaseVisitor,
	 *           info); if (!TCDeclAndDefVisitor.successfulType(type)) paragraph .setType(issueHandler .addTypeError(
	 *           paragraph, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE .customizeMessage(paragraph + ""))); } catch
	 *           (AnalysisException e) { issueHandler .addTypeError(paragraph, e.getMessage()); } } } } if
	 *           (issueHandler.hasErrors()) return false; // add classes and processes beforehand to environment for
	 *           (PSource s : sourceForest) { for (PDefinition def : s.getParagraphs()) { if (def instanceof
	 *           AClassDefinition) info.addType(def.getName(), def); if (def instanceof AProcessDefinition)
	 *           info.addVariable(def.getName(), def); } } // for each source type check classes and processes in depth
	 *           for (PSource s : sourceForest) { try { boolean allParagraphsOk = true; for (PDefinition paragraph :
	 *           s.getParagraphs()) { if (paragraph instanceof AClassDefinition || paragraph instanceof
	 *           AProcessDefinition) try { PType topType = paragraph.apply( rootDefPhaseVisitor, info); if
	 *           (!TCDeclAndDefVisitor .successfulType(topType)) { paragraph .setType(issueHandler .addTypeError(
	 *           paragraph, TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS .customizeMessage(paragraph .getName()
	 *           .toString()))); allParagraphsOk = false; } } catch (AnalysisException ae) { ByteArrayOutputStream baos
	 *           = new ByteArrayOutputStream(); ae.printStackTrace(new PrintStream(baos)); paragraph
	 *           .setType(issueHandler .addTypeError( s,
	 *           "The COMPASS Type checker failed on this cml-source. Please submit it for investigation to rala@iha.dk.\n"
	 *           + new String( baos.toByteArray()))); // This means we have a bug in the type checker return false; }
	 *           catch (ClassCastException e) { ByteArrayOutputStream baos = new ByteArrayOutputStream(); PrintWriter
	 *           out = new PrintWriter(baos); e.printStackTrace(out); out.flush(); paragraph .setType(issueHandler
	 *           .addTypeError( paragraph,
	 *           "Ill defined ast definition. Check that the implied AST-node is not defined in both cml.ast and in overtureII.astv2. Naturally, if this is the case the visitor has an ambigouos choice.\n"
	 *           + e.getMessage() + "\n" + new String( baos.toByteArray()))); } } if (allParagraphsOk) s.setType(new
	 *           ASourceType()); else s.setType(new AErrorType()); } catch (RuntimeException e) {
	 *           issueHandler.addTypeError(s, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE .customizeMessage(CmlTCUtil
	 *           .getErrorMessages(e))); } } return !super.issueHandler.hasErrors(); } catch (RuntimeException e) {
	 *           PSource first = null; if (!sourceForest.isEmpty()) { first = sourceForest.iterator().next();
	 *           issueHandler .addTypeError(first, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE
	 *           .customizeMessage(CmlTCUtil .getErrorMessages(e))); } return false; } }
	 */
	/**
	 * Get errors that occurred while type checking.
	 * 
	 * @return list of CMLTypeErrors
	 */

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
