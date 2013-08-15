package eu.compassresearch.theoremprover.visitors;

//POG-related imports
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
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
import eu.compassresearch.theoremprover.thms.ThmExpFunc;
import eu.compassresearch.theoremprover.thms.ThmImpFunc;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

@SuppressWarnings("serial")
public class ThmDeclAndDefVisitor  extends
		AnswerCMLAdaptor<ThmNodeList>
{

	// Errors and other things are recorded on this guy
	final private AnswerAdaptor<ThmNodeList> parentVisitor;

	public ThmDeclAndDefVisitor(AnswerCMLAdaptor<ThmNodeList> parent)
	{
		this.parentVisitor = parent;

	}

	// handle CML Defns And Decls
	/**
	 * CML ELEMENT - Channels
	 */

	@Override
	public ThmNodeList caseAChannelsDefinition(AChannelsDefinition node)
			throws AnalysisException
	{

		ThmNodeList tnl = new ThmNodeList();

		LinkedList<AChannelNameDefinition> cns = node.getChannelNameDeclarations();
		for (AChannelNameDefinition c : cns)
		{
			tnl.addAll(c.apply(parentVisitor));
		}

		return tnl;
	}

//	/**
//	 * CML channel definition CURRENTLY JUST PRINT TO SCREEN
//	 */
//	@Override
//	public ThmNodeList caseAChannelNameDefinition(AChannelNameDefinition node)
//			throws AnalysisException
//	{
//
//		System.out.println("----------***----------");
//		System.out.println("AChannelNameDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//
//		ThmNodeList tnl = new ThmNodeList();
//		
//		//TODO: Generate Channel syntax
//
//		return tnl;
//	}

	/**
	 * CML ELEMENT - Chansets
	 */
	@Override
	public ThmNodeList caseAChansetsDefinition(AChansetsDefinition node)
			throws AnalysisException
	{

		LinkedList<AChansetDefinition> subNodes = node.getChansets();
		ThmNodeList tnl = new ThmNodeList();

		for (AChansetDefinition d : subNodes)
		{
			tnl.addAll(d.apply(parentVisitor));
		}

		return tnl;
	}

//	/**
//	 * CML chanset definition CURRENTLY JUST PRINT TO SCREEN
//	 */
//	@Override
//	public ThmNodeList caseAChansetDefinition(AChansetDefinition node) throws AnalysisException
//	{
//
//		System.out.println("----------***----------");
//		System.out.println("AChansetDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//
//		ThmNodeList tnl = new ThmNodeList();
//
//		/*
//		 * Not clear what POs these may generate? May be useful for generating CMLPOContext
//		 */
//		// Commented out by RWL: Unused variables creates warnings.
//		// LexIdentifierToken id = node.getIdentifier();
//		// PVarsetExpression expr = node.getChansetExpression();
//
//		return tnl;
//	}

	/**
	 * CML ELEMENT - Classes
	 */
	@Override
	public ThmNodeList caseACmlClassDefinition(ACmlClassDefinition node) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached ThmDeclAndDefVisitor - caseAClassParagraphDefinition");

		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getDefinitions())
		{
		
			System.out.println("In defn Paragraph Loop: " + def.toString());
			tnl.addAll(def.apply(parentVisitor));
		}

		return tnl;
	}

	@Override
	public ThmNodeList caseAProcessDefinition(AProcessDefinition node) throws AnalysisException
	{

//		System.out.println("----------***----------");
//		System.out.println("AProcessDefinition");
//		System.out.println(node.toString());

		ThmNodeList tnl = new ThmNodeList();

		return tnl;
		
		//FIXME RIGHT NOW the pog does not visit processes.
//		
//		List<ATypeSingleDeclaration> lstate = node.getLocalState();
//
//		PProcess pdef = node.getProcess();
//		System.out.println("State :" + lstate.toString() + ", process :"
//				+ pdef.toString());
//
//		System.out.println("----------***----------");
//
//		// NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
//		// LinkedList<> lstate = node.getLocalState();
//		// for ( s : lstate)
//		// {
//		// 	System.out.println("----------***----------");
//		// 	ThmNodeList td = s.apply(this);
//		// 	System.out.println("----------***----------");
//		// }
//
//		// Dispatch local state?
//		// for (PDeclaration s : node.getLocalState())
//		// {
//		// 	tnl.addAll(s.apply(parentVisitor);
//		// }
//
//		// return pdef.apply(this);
//		tnl.addAll(pdef.apply(parentVisitor);
//		return tnl;
	}

	@Override
	public ThmNodeList caseAStateDefinition(AStateDefinition node) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached ThmDeclAndDefVisitor - caseAStateParagraphDefinition");

		System.out.println("State: " + node.toString() + ", Type: "
				+ node.getType());

		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getStateDefs())
		{
			System.out.println("In State Paragraph Loop");
			System.out.println("Def: " + def.toString() + ", Type: "
					+ def.getType());
			tnl.addAll(def.apply(parentVisitor));
		}

		return tnl;
	}

	/**
	 * CML ELEMENT - Actions
	 */
	@Override
	public ThmNodeList caseAActionsDefinition(AActionsDefinition node) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached ThmDeclAndDefVisitor - caseAActionParagraphDefinition");

		ThmNodeList tnl = new ThmNodeList();

		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions)
		{
			// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
			tnl.addAll(action.apply(parentVisitor));
		}

		return tnl;
	}

	@Override
	public ThmNodeList caseAActionDefinition(AActionDefinition node) throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached ThmDeclAndDefVisitor - caseAActionDefinition");

		ThmNodeList tnl = new ThmNodeList();

		PAction action = node.getAction();
		// System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
		tnl.addAll(action.apply(parentVisitor));

		return tnl;
	}

	// These will involve structural changes to the Overture AST in the
	// future but for now we hack past it.
	@Override
	public ThmNodeList caseAUnresolvedPathExp(AUnresolvedPathExp node) throws AnalysisException
	{
		return new ThmNodeList();
	}

	@Override
	public ThmNodeList caseAClassInvariantDefinition(AClassInvariantDefinition node)
			throws AnalysisException
	{
		System.out.println("------");
		System.out.println("Reached ThmDeclAndDefVisitor - caseAClassInvariantDefinition");

		ThmNodeList tnl = new ThmNodeList();

		//TODO: Class Inv needed

		return tnl;
	}

	// Call Overture for the other expressions
	@Override
	public ThmNodeList defaultPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(parentVisitor));
		return tnl;
	}

	@Override
	public ThmNodeList defaultPDefinition(PDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(parentVisitor));
		return tnl;
	}

	// Call the main pog when it's not a defintion/declaration
	@Override
	public ThmNodeList defaultINode(INode node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(parentVisitor));
		return tnl;
	}

	/**
	 * VDM ELEMENT - Types
	 */
	@Override
	public ThmNodeList caseATypesDefinition(ATypesDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getTypes())
		{
			tnl.addAll(def.apply(parentVisitor));
		}

		return tnl;
	}

	/**
	 * VDM ELEMENT - Values
	 */
	@Override
	public ThmNodeList caseAValuesDefinition(AValuesDefinition node) throws AnalysisException
	{

		ThmNodeList tnl = new ThmNodeList();
		LinkedList<PDefinition> list = node.getValueDefinitions();

		for (PDefinition def : list)
		{
			tnl.addAll(def.apply(parentVisitor));
		}

		return tnl;
	}

	/**
	 * VDM ELEMENT - Functions
	 */
	@Override
	public ThmNodeList caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getFunctionDefinitions())
		{
			tnl.addAll(def.apply(parentVisitor));
		}

		return tnl;
	}
	
	@Override
	public ThmNodeList caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		ILexNameToken name = node.getName();

		LinkedList<ILexNameToken> b = new LinkedList();
		LinkedList<ILexNameToken> s = new LinkedList();
		String exp = ThmExprUtil.getIsabelleExprStr(b, s, node.getBody());
		LinkedList<List<PPattern>> params = node.getParamPatternList();
		String pre = null;
		if (node.getPrecondition() != null)
			pre = ThmExprUtil.getIsabelleExprStr(b, s, node.getPrecondition());

		String post = null;
		if (node.getPostcondition() != null)
			post = ThmExprUtil.getIsabelleExprStr(b, s, node.getPostcondition());
		
		String resType = ThmTypeUtil.getIsabelleType(node.getExpectedResult());
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmExpFunc(name.getName(), exp, post, pre, params, resType));
		tnl.add(tn);
		
		return tnl;
	}
	
	public ThmNodeList caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		ILexNameToken name = node.getName();
		LinkedList<APatternListTypePair> params = node.getParamPatterns();
		
		LinkedList<ILexNameToken> b = new LinkedList();
		LinkedList<ILexNameToken> s = new LinkedList();
		String pre = null;
		if (node.getPrecondition() != null)
			pre = ThmExprUtil.getIsabelleExprStr(b, s, node.getPrecondition());

		String post = null;
		if (node.getPostcondition() != null)
			post = ThmExprUtil.getIsabelleExprStr(b, s, node.getPostcondition());
		
		APatternTypePair res = node.getResult();
		String resType = ThmTypeUtil.getIsabelleType(res.getType());
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmImpFunc(name.getName(), post, pre, params, res, resType));
		tnl.add(tn);
		
		return tnl;
	}
	

	/**
	 * VDM ELEMENT - Operations
	 */
	@Override
	public ThmNodeList caseAOperationsDefinition(
			AOperationsDefinition node)
			throws AnalysisException
	{

		ThmNodeList tnl = new ThmNodeList();

		for (SCmlOperationDefinition def : node.getOperations())
		{
			tnl.addAll(def.apply(parentVisitor));
		}
		return tnl;
	}

	/**
	 * Implicit operations - CML does not reuse Overture operations
	 */
	@Override
	public ThmNodeList caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node)
			throws AnalysisException
	{

		System.out.println("----------***----------");
		System.out.println("AImplicitOperationDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");

		ThmNodeList tnl = new ThmNodeList();

		// Taken from Overture - Needed?
		LexNameList pids = new LexNameList();

		for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

		
		// if implicit operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null)
		{
			//TODO:Gen Node For preConditionExpression
			//tnl.addAll(node.getPrecondition().apply(parentVisitor));
		}
		

		// if implicit operation has a precondition, dispatch for PO checking
		// and generate OperationPostConditionObligation
		if (node.getPostcondition() != null)
		{

			//TODO:Gen Node For preConditionExpression
			//tnl.addAll(node.getPostcondition().apply(parentVisitor));
		}

		return tnl;
	}

	//
	@Override
	public ThmNodeList caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node)
			throws AnalysisException
	{

		ThmNodeList tnl = new ThmNodeList();

		LexNameList pids = new LexNameList();

		// add all defined names from the function parameter list
		for (PPattern p : node.getParameterPatterns())
			for (PDefinition def : p.getDefinitions())
				pids.add(def.getName());


		// if operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null)
		{
			//TODO:Gen Node For preConditionExpression
			//tnl.addAll(node.getPrecondition().apply(parentVisitor));
		}
		

		// if implicit operation has a precondition, dispatch for PO checking
		// and generate OperationPostConditionObligation
		if (node.getPostcondition() != null)
		{

			//TODO:Gen Node For preConditionExpression
			//tnl.addAll(node.getPostcondition().apply(parentVisitor));
		}

		// dispatch operation body for PO checking
		//TODO:Gen Node For body
		//tnl.addAll(node.getBody().apply(parentVisitor);

		return tnl;
	}

	// >----------------------------------------------------------
	// >------------OVERTURE Overrides --------------------------
	// >--------------------------------------------------------

	@Override
	public ThmNodeList caseAAssignmentDefinition(
			AAssignmentDefinition node)
			throws AnalysisException
	{
		ThmNodeList obligations = new ThmNodeList();

		if (node.getExpression() == null)
			return obligations;
		PExp expression = node.getExpression();
		PType type = node.getType();
		PType expType = node.getExpType();

		//TODO: Assignment Node needed
		//obligations.addAll(expression.apply(parentVisitor));


		return obligations;
	}

}
