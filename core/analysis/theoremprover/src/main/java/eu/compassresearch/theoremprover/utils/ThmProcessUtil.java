package eu.compassresearch.theoremprover.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmImplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;


public class ThmProcessUtil {
	
	public static String isaProc = "defintion??";
	public static String procDelimLeft = "\\<parallel>";
	public static String procDelimRight = "\\<parallel>";
	public static String isaActProc = "locale";
	public static String isaProcBegin = "begin";
	public static String isaProcEnd = "end";
	public static String isaOp = "definition";
	public static String opExpLeft = "\\<lparr>";
	public static String opExpRight = "\\<rparr>";
	public static String opTurn = "\\<turnstile>";

	public  static ThmNode getIsabelleActionProcess(ILexNameToken name, AActionProcess proc)
	{	
		ThmNode tn = null;
		
		LinkedList<PDefinition> pdef = proc.getDefinitionParagraphs();
		PAction action = proc.getAction();
	
		for (PDefinition def : pdef)
		{
			//TODO: Handle defs.
		}
	

		return tn;
	}
	
	public static LinkedList<ILexNameToken> getStateNames(LinkedList<AStateDefinition> statements)
	
	{
		//first we need to get all the state identifier names so expressions use correct
		//reference
		LinkedList<ILexNameToken> statenames = new LinkedList<ILexNameToken>();
		for (AStateDefinition pdef : statements)
		{
			for (PDefinition sdef : pdef.getStateDefs())
			{
				if (sdef instanceof AAssignmentDefinition)
				{
					AAssignmentDefinition st = (AAssignmentDefinition) sdef;
	
					ILexNameToken name = st.getName();
					statenames.add(name);
				}
			}
		}
		return statenames;
	}

	public static LinkedList<ILexNameToken> getOperationNames(
			LinkedList<SCmlOperationDefinition> operations) {
		
		LinkedList<ILexNameToken> opNames = new LinkedList<ILexNameToken>();
		
		for(SCmlOperationDefinition op : operations)
			opNames.add(op.getName());
		
		return opNames;
	}

	public static LinkedList<ILexNameToken> getActionNames(
			LinkedList<AActionDefinition> actions) {

		LinkedList<ILexNameToken> actNames = new LinkedList<ILexNameToken>();
		for(AActionDefinition a : actions)
			actNames.add(a.getName());
		return actNames;
	}

	public static LinkedList<ILexNameToken> removeProcessDeps(
			LinkedList<ILexNameToken> nodeDeps,
			LinkedList<ILexNameToken> procNames) {
		
		//Need to add remove all inter-process dependancies 
		//For each name used within a process		
		for(ILexNameToken pn :procNames)
		{
			//for each dependancy 
			for(Iterator<ILexNameToken> itr = nodeDeps.listIterator(); itr.hasNext(); )
			{
				ILexNameToken nd = itr.next();
				//if the dependancy is a process name, remove it.
				if(pn.toString().equals(nd.toString()))
				{
					itr.remove();
				}
			}
		}
		return nodeDeps;
	}
	
	

	public static LinkedList<ILexNameToken> removeExtDeps(
			LinkedList<ILexNameToken> nodeDeps,
			LinkedList<ILexNameToken> procNames) {
		
		LinkedList<ILexNameToken> nodeNames = new LinkedList<ILexNameToken>();
		
		//Need to add remove all extra-process dependancies 
		//For each node dependancy
		for(ILexNameToken nd : nodeDeps)
		{
			//For each name used within a process		
			for(ILexNameToken pn :procNames)
			{
				//if the depandancy is a process name, then add to the new list
				if(pn.toString().equals(nd.toString()))
				{
					nodeNames.add(nd);
				}
			}
		}
		
		//return only the node dependancies that are intra-process names 
		return nodeNames;
	}

	public static LinkedList<ThmNode> getIsabelleOperations(
			LinkedList<SCmlOperationDefinition> operations, LinkedList<ILexNameToken> svars) {
		LinkedList<ThmNode> tnl = new LinkedList<ThmNode>();
		
		for(SCmlOperationDefinition op : operations)
			tnl.add(ThmProcessUtil.getIsabelleOperation(op, svars));
		
		return tnl;
	}

	private static ThmNode getIsabelleOperation(SCmlOperationDefinition op, LinkedList<ILexNameToken> svars) {
		ThmNode tn = null;
		if (op instanceof AImplicitCmlOperationDefinition)
		{
			AImplicitCmlOperationDefinition exOp = (AImplicitCmlOperationDefinition) op;
			LinkedList<ILexNameToken> nodeDeps = new LinkedList();
			String pre = null;
			String post = null;
			if (exOp.getPrecondition() != null)
				pre = ThmExprUtil.getIsabelleExprStr(svars, new LinkedList(), exOp.getPrecondition());
			if (exOp.getPostcondition() != null)
				post = ThmExprUtil.getIsabelleExprStr(svars, new LinkedList(), exOp.getPostcondition());
				
			tn = new ThmNode(exOp.getName(), nodeDeps, new ThmImplicitOperation(exOp.getName().toString(), pre, post));

		}
		else if (op instanceof AExplicitCmlOperationDefinition)
		{
			AExplicitCmlOperationDefinition exOp = (AExplicitCmlOperationDefinition) op;
			LinkedList<ILexNameToken> nodeDeps = new LinkedList();
			String body = "";//exOp.getBody();
			String pre = null;
			String post = null;
			if (exOp.getPrecondition() != null)
				pre = ThmExprUtil.getIsabelleExprStr(svars, new LinkedList(), exOp.getPrecondition());
			if (exOp.getPostcondition() != null)
				post = ThmExprUtil.getIsabelleExprStr(svars, new LinkedList(), exOp.getPostcondition());
				
			tn = new ThmNode(exOp.getName(), nodeDeps, new ThmExplicitOperation(exOp.getName().toString(), pre, post, body.toString()));

		}
		return tn;
	}
	
	
	
	
//	/**
//	 * Implicit operations - CML does not reuse Overture operations
//	 */
//	@Override
//	public ThmNodeList caseAImplicitCmlOperationDefinition(
//			AImplicitCmlOperationDefinition node)
//			throws AnalysisException
//	{
//
//		System.out.println("----------***----------");
//		System.out.println("AImplicitOperationDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//
//		ThmNodeList tnl = new ThmNodeList();
//
//		// Taken from Overture - Needed?
//		LexNameList pids = new LexNameList();
//
//		for (APatternListTypePair tp : node.getParameterPatterns())
//			for (PPattern p : tp.getPatterns())
//				for (PDefinition def : p.getDefinitions())
//					pids.add(def.getName());
//
//		
//		// if implicit operation has a precondition, dispatch for PO checking
//		if (node.getPrecondition() != null)
//		{
//			//TODO:Gen Node For preConditionExpression
//			//tnl.addAll(node.getPrecondition().apply(parentVisitor));
//		}
//		
//
//		// if implicit operation has a precondition, dispatch for PO checking
//		// and generate OperationPostConditionObligation
//		if (node.getPostcondition() != null)
//		{
//
//			//TODO:Gen Node For preConditionExpression
//			//tnl.addAll(node.getPostcondition().apply(parentVisitor));
//		}
//
//		return tnl;
//	}
//
//	//
//	@Override
//	public ThmNodeList caseAExplicitCmlOperationDefinition(
//			AExplicitCmlOperationDefinition node)
//			throws AnalysisException
//	{
//
//		ThmNodeList tnl = new ThmNodeList();
//
//		LexNameList pids = new LexNameList();
//
//		// add all defined names from the function parameter list
//		for (PPattern p : node.getParameterPatterns())
//			for (PDefinition def : p.getDefinitions())
//				pids.add(def.getName());
//
//
//		// if operation has a precondition, dispatch for PO checking
//		if (node.getPrecondition() != null)
//		{
//			//TODO:Gen Node For preConditionExpression
//			//tnl.addAll(node.getPrecondition().apply(parentVisitor));
//		}
//		
//
//		// if implicit operation has a precondition, dispatch for PO checking
//		// and generate OperationPostConditionObligation
//		if (node.getPostcondition() != null)
//		{
//
//			//TODO:Gen Node For preConditionExpression
//			//tnl.addAll(node.getPostcondition().apply(parentVisitor));
//		}
//
//		// dispatch operation body for PO checking
//		//TODO:Gen Node For body
//		//tnl.addAll(node.getBody().apply(parentVisitor);
//
//		return tnl;
//	}
	

}


//@Override
//public CmlProofObligationList defaultPProcess(PProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("PProcess: " + node.toString());
//	return new CmlProofObligationList();
//}
//
//@Override
//public CmlProofObligationList caseAActionProcess(AActionProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//	System.out.println("A AActionProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	LinkedList<PDefinition> pdef = node.getDefinitionParagraphs();
//	PAction action = node.getAction();
//
//	// Print the separate parts to screen - TESTING
//	System.out.println("A StateProcess: " + node.toString());
//	System.out.println("A StateProcess defintion paragraphs: " + pdef);
//	System.out.println("A StateProcess action: " + action);
//
//	for (PDefinition def : pdef)
//	{
//		System.out.println(def.toString());
//		PONameContext name = def.apply(new PogNameContextVisitor());
//		if (name != null)
//		{
//			question.push(def.apply(new PogNameContextVisitor()));
//			pol.addAll(def.apply(parentPOG, question));
//			question.pop();
//		} else
//		{
//			pol.addAll(def.apply(parentPOG, question));
//		}
//		// FIXME possible names woes here
//	}
//
//	// TODO: Consider any AActionProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInternalChoiceProcess(
//		AInternalChoiceProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AInternalChoiceProcess: " + node.toString());
//
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider any AInternalChoiceProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAUntimedTimeoutProcess(
//		AUntimedTimeoutProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AUntimedTimeoutProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider any AUntimedTimeoutProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseATimeoutProcess(ATimeoutProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A ATimeoutProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PExp timedExp = node.getTimeoutExpression();
//	PProcess right = node.getRight();
//	PProcess left = node.getLeft();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(timedExp.apply(parentPOG, question));
//	// check for Non-Zero time obligation and dispatch exp for POG checking
//	pol.add(new CmlNonZeroTimeObligation(timedExp, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider any ATimeoutProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseASynchronousParallelismReplicatedProcess(
//		ASynchronousParallelismReplicatedProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A ASynchronousParallelismReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//
//	// TODO: Consider any ASynchronousParallelismReplicatedProcess POs
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseASequentialCompositionReplicatedProcess(
//		ASequentialCompositionReplicatedProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A ASequentialCompositionReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//
//	// TODO: Consider any ASequentialCompositionReplicatedProcess POs
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInternalChoiceReplicatedProcess(
//		AInternalChoiceReplicatedProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AInternalChoiceReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//
//	// TODO: Consider any AInternalChoiceReplicatedProcess POs
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAGeneralisedParallelismReplicatedProcess(
//		AGeneralisedParallelismReplicatedProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A AGeneralisedParallelismReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PVarsetExpression csExp = node.getChansetExpression();
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	pol.addAll(csExp.apply(parentPOG, question));
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//
//	// TODO: Consider any AExternalChoiceReplicatedProcess POs
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAExternalChoiceReplicatedProcess(
//		AExternalChoiceReplicatedProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AExternalChoiceReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//
//	// TODO: Consider any AExternalChoiceReplicatedProcess POs
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAAlphabetisedParallelismReplicatedProcess(
//		AAlphabetisedParallelismReplicatedProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A AAlphabetisedParallelismReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PVarsetExpression csExp = node.getChansetExpression();
//	PProcess proc = node.getReplicatedProcess();
//	LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
//
//	pol.addAll(csExp.apply(parentPOG, question));
//	for (PSingleDeclaration decl : repdecl)
//	{
//		// TODO: Ensure this it the correct way to handle declaration POs
//		pol.addAll(decl.apply(parentPOG, question));
//	}
//	// TODO: Consider any AAlphabetisedParallelismReplicatedProcess POs
//
//	pol.addAll(proc.apply(parentPOG, question));
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInterruptProcess(AInterruptProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A AInterruptProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider interrupt POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInterleavingProcess(
//		AInterleavingProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AInterleavingProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider AInterleavingProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInstantiationProcess(
//		AInstantiationProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AInstantiationProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	LinkedList<PExp> args = node.getArgs();
//	PProcess proc = node.getProcess();
//	LinkedList<PParametrisation> paras = node.getParametrisations();
//
//	// TODO: Consider instantiation POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAHidingProcess(AHidingProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A AHidingProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PVarsetExpression csExp = node.getChansetExpression();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(csExp.apply(parentPOG, question));
//
//	// TODO: Consider AHidingProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAGeneralisedParallelismProcess(
//		AGeneralisedParallelismProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AGeneralisedParallelismProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//	PVarsetExpression csExp = node.getChansetExpression();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(csExp.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider AGeneralisedParallelismProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAExternalChoiceProcess(
//		AExternalChoiceProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AExternalChoiceProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//
//	// TODO: Consider AExternalChoiceProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAChannelRenamingProcess(
//		AChannelRenamingProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AChannelRenamingProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess process = node.getProcess();
//	SRenameChannelExp renameExp = node.getRenameExpression();
//
//	pol.addAll(process.apply(parentPOG, question));
//	pol.addAll(renameExp.apply(parentPOG, question));
//	// TODO: Consider AChannelRenamingProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAAlphabetisedParallelismProcess(
//		AAlphabetisedParallelismProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AAlphabetisedParallelismProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//	PVarsetExpression leftChanSet = node.getLeftChansetExpression();
//	PVarsetExpression rightChanSet = node.getRightChansetExpression();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(right.apply(parentPOG, question));
//	pol.addAll(leftChanSet.apply(parentPOG, question));
//	pol.addAll(rightChanSet.apply(parentPOG, question));
//
//	// TODO: Consider AAlphabetisedParallelismProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAStartDeadlineProcess(
//		AStartDeadlineProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AStartDeadlineProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PExp timeExp = node.getExpression();
//
//	pol.addAll(left.apply(parentPOG, question));
//	pol.addAll(timeExp.apply(parentPOG, question));
//
//	// TODO: Consider AStartDeadlineProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAEndDeadlineProcess(
//		AEndDeadlineProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AEndDeadlineProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PExp exp = node.getExpression();
//	PProcess left = node.getLeft();
//
//	// TODO: consider AEndDeadlineProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAInterleavingReplicatedProcess(
//		AInterleavingReplicatedProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A AInterleavingReplicatedProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	LinkedList<PSingleDeclaration> declarations = node.getReplicationDeclaration();
//	PProcess replicatedProcess = node.getReplicatedProcess();
//
//	for (PSingleDeclaration singleDecl : declarations)
//	{
//		pol.addAll(singleDecl.apply(parentPOG, question));
//	}
//
//	pol.addAll(replicatedProcess.apply(parentPOG, question));
//
//	// TODO: consider AInterleavingReplicatedProcess POs
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseASynchronousParallelismProcess(
//		ASynchronousParallelismProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A ASynchronousParallelismProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	pol.addAll(node.getLeft().apply(parentPOG, question));
//	pol.addAll(node.getRight().apply(parentPOG, question));
//
//	// TODO: consider ASynchronousParallelismProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseASequentialCompositionProcess(
//		ASequentialCompositionProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//
//	System.out.println("A ASequentialCompositionProcess: "
//			+ node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	pol.addAll(node.getLeft().apply(parentPOG, question));
//	pol.addAll(node.getRight().apply(parentPOG, question));
//
//	// TODO: consider ASequentialCompositionProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseAReferenceProcess(AReferenceProcess node,
//		IPOContextStack question) throws AnalysisException
//{
//
//	System.out.println("A AReferenceProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	LinkedList<PExp> args = node.getArgs();
//	AProcessDefinition defn = node.getProcessDefinition();
//	ILexNameToken name = node.getProcessName();
//
//	// TODO: consider AReferenceProcess POs
//
//	return pol;
//}
//
//@Override
//public CmlProofObligationList caseATimedInterruptProcess(
//		ATimedInterruptProcess node, IPOContextStack question)
//		throws AnalysisException
//{
//	System.out.println("A ATimedInterruptProcess: " + node.toString());
//	CmlProofObligationList pol = new CmlProofObligationList();
//
//	// Get subparts
//	PProcess left = node.getLeft();
//	PProcess right = node.getRight();
//	PExp timeExp = node.getTimeExpression();
//
//	// Send left-hand side
//	pol.addAll(left.apply(parentPOG, question));
//	// check for Non-Zero time obligation and dispatch exp for POG checking
//	pol.add(new CmlNonZeroTimeObligation(timeExp, question));
//	// TODO: any other ATimedInterruptProcess POs?
//	pol.addAll(timeExp.apply(parentPOG, question));
//	// Send right-hand side
//	pol.addAll(right.apply(parentPOG, question));
//
//	return pol;
//}