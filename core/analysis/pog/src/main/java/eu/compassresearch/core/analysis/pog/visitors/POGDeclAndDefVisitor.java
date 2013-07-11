package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.pog.IPOContextStack;
import org.overture.pog.POException;
import org.overture.pog.PONameContext;
import org.overture.pog.PogParamDefinitionVisitor;
import org.overture.pog.SubTypeObligation;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOOperationDefinitionContext;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLStateInvariantObligation;
import eu.compassresearch.core.analysis.pog.obligations.OperationPostConditionObligation;
import eu.compassresearch.core.analysis.pog.obligations.ParameterPatternObligation;
import eu.compassresearch.core.analysis.pog.obligations.SatisfiabilityObligation;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CMLProofObligationList>
{

	// Errors and other things are recorded on this guy
	final private QuestionAnswerAdaptor<IPOContextStack, CMLProofObligationList> parentPOG;
	final private PogParamDefinitionVisitor<IPOContextStack, CMLProofObligationList> overtureVisitor;
	final private PogNameContextVisitor nameVisitor;

	public POGDeclAndDefVisitor(
			QuestionAnswerAdaptor<IPOContextStack, CMLProofObligationList> parent)
	{
		this.parentPOG = parent;
		this.overtureVisitor = new PogParamDefinitionVisitor<IPOContextStack, CMLProofObligationList>(this, this);
		this.nameVisitor = new PogNameContextVisitor();

	}

	// handle CML Defns And Decls
	/**
	 * CML ELEMENT - Channels
	 */

	@Override
	public CMLProofObligationList caseAChannelsDefinition(
			AChannelsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CMLProofObligationList pol = new CMLProofObligationList();

		LinkedList<AChannelNameDefinition> cns = node.getChannelNameDeclarations();
		for (AChannelNameDefinition c : cns)
		{
			pol.addAll(c.apply(this, question));
		}

		return pol;
	}

	/**
	 * CML channel definition CURRENTLY JUST PRINT TO SCREEN
	 */
	@Override
	public CMLProofObligationList caseAChannelNameDefinition(
			AChannelNameDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AChannelNameDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CMLProofObligationList pol = new CMLProofObligationList();

		/*
		 * Not clear what POs these may generate? May be useful for generating CMLPOContext
		 */
		// Commented out by RWL, unused variables creates warnings
		// LinkedList ids = node.getSingleType().getIdentifiers();
		// PType type = node.getSingleType().getType();

		return pol;
	}

	/**
	 * CML ELEMENT - Chansets
	 */
	@Override
	public CMLProofObligationList caseAChansetsDefinition(
			AChansetsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		LinkedList<AChansetDefinition> subNodes = node.getChansets();
		CMLProofObligationList pol = new CMLProofObligationList();

		for (AChansetDefinition d : subNodes)
		{
			pol.addAll(d.apply(this, question));
		}

		return pol;
	}

	/**
	 * CML chanset definition CURRENTLY JUST PRINT TO SCREEN
	 */
	@Override
	public CMLProofObligationList caseAChansetDefinition(AChansetDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AChansetDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CMLProofObligationList pol = new CMLProofObligationList();

		/*
		 * Not clear what POs these may generate? May be useful for generating CMLPOContext
		 */
		// Commented out by RWL: Unused variables creates warnings.
		// LexIdentifierToken id = node.getIdentifier();
		// PVarsetExpression expr = node.getChansetExpression();

		return pol;
	}

	/**
	 * CML ELEMENT - Classes
	 */
	@Override
	public CMLProofObligationList caseAClassDefinition(AClassDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAClassParagraphDefinition");

		CMLProofObligationList pol = new CMLProofObligationList();

		for (PDefinition def : node.getBody())
		{
			PONameContext name = def.apply(new PogNameContextVisitor());
			if (name != null)
			{
				question.push(def.apply(new PogNameContextVisitor()));
				System.out.println("In defn Paragraph Loop: " + def.toString());
				pol.addAll(def.apply(parentPOG, question));
				question.pop();
			} else
			{
				pol.addAll(def.apply(parentPOG, question));
				System.out.println("In defn Paragraph Loop: " + def.toString());
			}

		}

		return pol;
	}

	@Override
	public CMLProofObligationList caseAProcessDefinition(AProcessDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AProcessDefinition");
		System.out.println(node.toString());

		CMLProofObligationList pol = new CMLProofObligationList();

		LinkedList<PSingleDeclaration> lstate = node.getLocalState();

		PProcess pdef = node.getProcess();
		System.out.println("State :" + lstate.toString() + ", process :"
				+ pdef.toString());

		System.out.println("----------***----------");

		// NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
		// LinkedList<> lstate = node.getLocalState();
		// for ( s : lstate)
		// {
		// System.out.println("----------***----------");
		// CMLProofObligationList td = s.apply(this, question);
		// System.out.println("----------***----------");
		// }

		// Dispatch local state?
		// for (PDeclaration s : node.getLocalState())
		// {
		// pol.addAll(s.apply(parentPOG, question));
		// }

		// return pdef.apply(this, question);
		pol.addAll(pdef.apply(parentPOG, question));
		return pol;
	}

	@Override
	public CMLProofObligationList caseAStateDefinition(AStateDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAStateParagraphDefinition");

		System.out.println("State: " + node.toString() + ", Type: "
				+ node.getType());

		CMLProofObligationList pol = new CMLProofObligationList();

		for (PDefinition def : node.getStateDefs())
		{
			System.out.println("In State Paragraph Loop");
			System.out.println("Def: " + def.toString() + ", Type: "
					+ def.getType());
			pol.addAll(def.apply(parentPOG, question));
		}

		return pol;
	}

	/**
	 * CML ELEMENT - Actions
	 */
	@Override
	public CMLProofObligationList caseAActionsDefinition(AActionsDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");

		CMLProofObligationList pol = new CMLProofObligationList();

		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions)
		{
			// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
			pol.addAll(action.apply(parentPOG, question));
		}

		return pol;
	}

	@Override
	public CMLProofObligationList caseAActionDefinition(AActionDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAActionDefinition");

		CMLProofObligationList pol = new CMLProofObligationList();

		PAction action = node.getAction();
		// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
		pol.addAll(action.apply(parentPOG, question));

		return pol;
	}

	// These will involve structural changes to the Overture AST in the
	// future but for now we hack past it.
	@Override
	public CMLProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
			IPOContextStack question) throws AnalysisException
	{
		return new CMLProofObligationList();
	}

	@Override
	public CMLProofObligationList caseAClassInvariantDefinition(
			AClassInvariantDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CMLProofObligationList list = new CMLProofObligationList();

			if (node.getClassDefinition() != null)
			{

				if (!node.getClassDefinition().getHasContructors())
				{
					list.add(new CMLStateInvariantObligation(node, question));
				}
			}

			// TODO we need to process state invariants

			return list;
		} catch (Exception e)
		{
			throw new POException(node, e.getMessage());
		}
	}

	// Call Overture for the other expressions
	@Override
	public CMLProofObligationList defaultPSingleDeclaration(
			PSingleDeclaration node, IPOContextStack question)
			throws AnalysisException
	{
		CMLProofObligationList pol = new CMLProofObligationList();
		pol.addAll(node.apply(overtureVisitor, question));
		return pol;
	}

	@Override
	public CMLProofObligationList defaultPDefinition(PDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CMLProofObligationList pol = new CMLProofObligationList();
		pol.addAll(node.apply(overtureVisitor, question));
		return pol;
	}

	// Call the main pog when it's not a defintion/declaration
	@Override
	public CMLProofObligationList defaultINode(INode node, IPOContextStack question)
			throws AnalysisException
	{
		CMLProofObligationList pol = new CMLProofObligationList();
		pol.addAll(node.apply(parentPOG, question));
		return pol;
	}

	/**
	 * VDM ELEMENT - Types
	 */
	@Override
	public CMLProofObligationList caseATypesDefinition(ATypesDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CMLProofObligationList pol = new CMLProofObligationList();

		for (PDefinition def : node.getTypes())
		{
			pol.addAll(def.apply(parentPOG, question));
		}

		return pol;
	}

	/**
	 * Invariant definition CURRENTLY PRINT TO SCREEN
	 */

	// DOES NOT EXIST ANYMORE
	// @Override
	// public CMLProofObligationList caseAInvariantDefinition(
	// AInvariantDefinition node, POContextStack question)
	// throws AnalysisException {
	//
	// CMLProofObligationList pol = new CMLProofObligationList();
	//
	// System.out.println("----------***----------");
	// System.out.println("AInvariantDefinition");
	// System.out.println(node.toString());
	// System.out.println(node.getPattern());
	// System.out.println(node.getExpression());
	// System.out.println("----------***----------");
	//
	// return pol;
	// }

	/**
	 * VDM ELEMENT - Values
	 */
	@Override
	public CMLProofObligationList caseAValuesDefinition(AValuesDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		CMLProofObligationList pol = new CMLProofObligationList();
		LinkedList<PDefinition> list = node.getValueDefinitions();

		for (PDefinition def : list)
		{
			PONameContext name = def.apply(nameVisitor);
			if (name != null)
			{
				question.push(name);
				pol.addAll(def.apply(parentPOG, question));
				question.pop();
			} else
			{
				pol.addAll(def.apply(parentPOG, question));
			}
		}

		return pol;
	}

	/**
	 * VDM ELEMENT - Functions
	 */
	@Override
	public CMLProofObligationList caseAFunctionsDefinition(
			AFunctionsDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CMLProofObligationList obligations = new CMLProofObligationList();

		// add the name stuff HERE

		for (PDefinition def : node.getFunctionDefinitions())
		{
			PONameContext name = def.apply(nameVisitor);
			if (name != null)
			{
				question.push(name);
				obligations.addAll(def.apply(parentPOG, question));
				question.pop();
			} else
			{
				obligations.addAll(def.apply(parentPOG, question));
			}
		}

		return obligations;
	}

	/**
	 * VDM ELEMENT - Operations
	 */
	@Override
	public CMLProofObligationList caseAOperationsDefinition(
			AOperationsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CMLProofObligationList pol = new CMLProofObligationList();

		for (SCmlOperationDefinition def : node.getOperations())
		{
			PONameContext name = def.apply(nameVisitor);
			if (name != null)
			{
				question.push(name);
				pol.addAll(def.apply(parentPOG, question));
				question.pop();
			} else
			{
				pol.addAll(def.apply(parentPOG, question));
			}
		}
		return pol;
	}

	/**
	 * Implicit operations - CML does not reuse Overture operations
	 */
	@Override
	public CMLProofObligationList caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AImplicitOperationDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CMLProofObligationList pol = new CMLProofObligationList();

		// Taken from Overture - Needed?
		LexNameList pids = new LexNameList();

		for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

		// Taken from Overture - Needed?
		if (pids.hasDuplicates())
		{
			pol.add(new ParameterPatternObligation(node, question));
		}

		// if implicit operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null)
		{
			pol.addAll(node.getPrecondition().apply(parentPOG, question));
		}

		// if implicit operation has a precondition, dispatch for PO checking
		// and generate OperationPostConditionObligation
		if (node.getPostcondition() != null)
		{
			pol.addAll(node.getPostcondition().apply(parentPOG, question));
			pol.add(new OperationPostConditionObligation(node, question));

			// COMMENTED AS CONTEXT GENERATES VARIOUS NULL OBJECTS, DUE TO NEW AST...
			// AS SUCH SAT OBLIG DOESN'T DO MUCH

			// FIXME Satisfiability PO clashing with implict operation
			// .getPostDef() is returning null

			question.push(new CMLPOOperationDefinitionContext(node, false, node.getStateDefinition()));
			pol.add(new SatisfiabilityObligation(node, node.getStateDefinition(), question));
			question.pop();
		}

		return pol;
	}

	//
	@Override
	public CMLProofObligationList caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CMLProofObligationList pol = new CMLProofObligationList();

		LexNameList pids = new LexNameList();

		// add all defined names from the function parameter list
		for (PPattern p : node.getParameterPatterns())
			for (PDefinition def : p.getDefinitions())
				pids.add(def.getName());

		if (pids.hasDuplicates())
		{
			pol.add(new ParameterPatternObligation(node, question));
		}

		// if operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null)
		{
			pol.addAll(node.getPrecondition().apply(parentPOG, question));
		}
		// if operation has a precondition, dispatch for PO checking
		// and generate OperationPostConditionObligation
		if (node.getPostcondition() != null)
		{
			pol.addAll(node.getPostcondition().apply(parentPOG, question));
			pol.add(new OperationPostConditionObligation(node, question));
		}

		// dispatch operation body for PO checking
		pol.addAll(node.getBody().apply(parentPOG, question));

		// /*
		// * Taken from Overture POG - not currently working
		// */
		//
		// if (node.getIsConstructor() && node.getClassDefinition() != null
		// && node.getClassDefinition().getInvariant() != null){
		// pol.add(new CMLStateInvariantObligation(node, question));
		// }
		//
		// if (!node.getIsConstructor()
		// && !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
		// pol.add(new SubTypeObligation(node, node.getActualResult(), question));
		// }

		return pol;
	}

	// >----------------------------------------------------------
	// >------------OVERTURE Overrides --------------------------
	// >--------------------------------------------------------

	@Override
	public CMLProofObligationList caseAAssignmentDefinition(
			AAssignmentDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CMLProofObligationList obligations = new CMLProofObligationList();

		if (node.getExpression() == null)
			return obligations;
		PExp expression = node.getExpression();
		PType type = node.getType();
		PType expType = node.getExpType();

		obligations.addAll(expression.apply(parentPOG, question));

		if (!TypeComparator.isSubType(question.checkType(expression, expType), type))
		{
			obligations.add(new SubTypeObligation(expression, type, expType, question));
		}

		return obligations;
	}

}
