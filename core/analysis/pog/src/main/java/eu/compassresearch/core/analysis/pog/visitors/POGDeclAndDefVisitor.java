package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.LinkedList;
import java.util.List;

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
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.obligation.StateInvariantObligation;
import org.overture.pog.obligation.SubTypeObligation;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.utility.POException;
import org.overture.pog.visitors.PogParamDefinitionVisitor;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
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
import eu.compassresearch.core.analysis.pog.obligations.CmlOperationDefinitionContext;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CmlOperationPostConditionObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlParameterPatternObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlSatisfiabilityObligation;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList>
{

	// Errors and other things are recorded on this guy
	final private QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> parentPOG;
	final private PogParamDefinitionVisitor<IPOContextStack, CmlProofObligationList> overtureVisitor;
	final private PogNameContextVisitor nameVisitor;

	public POGDeclAndDefVisitor(
			QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> parent)
	{
		this.parentPOG = parent;
		this.overtureVisitor = new PogParamDefinitionVisitor<IPOContextStack, CmlProofObligationList>(this, this);
		this.nameVisitor = new PogNameContextVisitor();

	}

	// handle CML Defns And Decls
	/**
	 * CML ELEMENT - Channels
	 */

	@Override
	public CmlProofObligationList caseAChannelsDefinition(
			AChannelsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAChannelNameDefinition(
			AChannelNameDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AChannelNameDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAChansetsDefinition(
			AChansetsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		LinkedList<AChansetDefinition> subNodes = node.getChansets();
		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAChansetDefinition(AChansetDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AChansetDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseACmlClassDefinition(ACmlClassDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAClassParagraphDefinition");

		CmlProofObligationList pol = new CmlProofObligationList();

		for (PDefinition def : node.getDefinitions())
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
	public CmlProofObligationList caseAProcessDefinition(AProcessDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AProcessDefinition");
		System.out.println(node.toString());

		CmlProofObligationList pol = new CmlProofObligationList();

		List<ATypeSingleDeclaration> lstate = node.getLocalState();

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
	public CmlProofObligationList caseAStateDefinition(AStateDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAStateParagraphDefinition");

		System.out.println("State: " + node.toString() + ", Type: "
				+ node.getType());

		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAActionsDefinition(AActionsDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");

		CmlProofObligationList pol = new CmlProofObligationList();

		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions)
		{
			// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
			pol.addAll(action.apply(parentPOG, question));
		}

		return pol;
	}

	@Override
	public CmlProofObligationList caseAActionDefinition(AActionDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached POGDeclAndDefVisitor - caseAActionDefinition");

		CmlProofObligationList pol = new CmlProofObligationList();

		PAction action = node.getAction();
		// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
		pol.addAll(action.apply(parentPOG, question));

		return pol;
	}

	// These will involve structural changes to the Overture AST in the
	// future but for now we hack past it.
	@Override
	public CmlProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
			IPOContextStack question) throws AnalysisException
	{
		return new CmlProofObligationList();
	}

	@Override
	public CmlProofObligationList caseAClassInvariantDefinition(
			AClassInvariantDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		try
		{
			CmlProofObligationList list = new CmlProofObligationList();

			if (node.getClassDefinition() != null)
			{

				if (!node.getClassDefinition().getHasContructors())
				{
					list.add(new StateInvariantObligation(node, question));
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
	public CmlProofObligationList defaultPSingleDeclaration(
			PSingleDeclaration node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(overtureVisitor, question));
		return pol;
	}

	@Override
	public CmlProofObligationList defaultPDefinition(PDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(overtureVisitor, question));
		return pol;
	}

	// Call the main pog when it's not a defintion/declaration
	@Override
	public CmlProofObligationList defaultINode(INode node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();
		pol.addAll(node.apply(parentPOG, question));
		return pol;
	}

	/**
	 * VDM ELEMENT - Types
	 */
	@Override
	public CmlProofObligationList caseATypesDefinition(ATypesDefinition node,
			IPOContextStack question) throws AnalysisException
	{
		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAValuesDefinition(AValuesDefinition node,
			IPOContextStack question) throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();
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
	public CmlProofObligationList caseAFunctionsDefinition(
			AFunctionsDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList obligations = new CmlProofObligationList();

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
	public CmlProofObligationList caseAOperationsDefinition(
			AOperationsDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

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
	public CmlProofObligationList caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AImplicitOperationDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		CmlProofObligationList pol = new CmlProofObligationList();

		// Taken from Overture - Needed?
		LexNameList pids = new LexNameList();

		for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

		// Taken from Overture - Needed?
		if (pids.hasDuplicates())
		{
			pol.add(new CmlParameterPatternObligation(node, question));
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
			pol.add(new CmlOperationPostConditionObligation(node, question));

			// COMMENTED AS CONTEXT GENERATES VARIOUS NULL OBJECTS, DUE TO NEW AST...
			// AS SUCH SAT OBLIG DOESN'T DO MUCH

			// FIXME Satisfiability PO clashing with implict operation
			// .getPostDef() is returning null

			question.push(new CmlOperationDefinitionContext(node, false, node.getStateDefinition()));
			pol.add(new CmlSatisfiabilityObligation(node, node.getStateDefinition(), question));
			question.pop();
		}

		return pol;
	}

	//
	@Override
	public CmlProofObligationList caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node, IPOContextStack question)
			throws AnalysisException
	{

		CmlProofObligationList pol = new CmlProofObligationList();

		LexNameList pids = new LexNameList();

		// add all defined names from the function parameter list
		for (PPattern p : node.getParameterPatterns())
			for (PDefinition def : p.getDefinitions())
				pids.add(def.getName());

		if (pids.hasDuplicates())
		{
			pol.add(new CmlParameterPatternObligation(node, question));
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
			pol.add(new CmlOperationPostConditionObligation(node, question));
		}

		// dispatch operation body for PO checking
		pol.addAll(node.getBody().apply(parentPOG, question));

		// FIXME Taken from Overture POG - not currently working
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
	public CmlProofObligationList caseAAssignmentDefinition(
			AAssignmentDefinition node, IPOContextStack question)
			throws AnalysisException
	{
		CmlProofObligationList obligations = new CmlProofObligationList();

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
