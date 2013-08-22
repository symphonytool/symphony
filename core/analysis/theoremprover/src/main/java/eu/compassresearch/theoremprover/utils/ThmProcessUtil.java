package eu.compassresearch.theoremprover.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.theoremprover.thms.ThmAction;
import eu.compassresearch.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmImplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmNode;


public class ThmProcessUtil {
	
	public static String isaProc = "definition";
	public static String isaAct = "definition";
	public static String procDelimLeft = "\\<parallel>";
	public static String procDelimRight = "\\<parallel>";
	public static String isaActProc = "locale";
	public static String isaProcBegin = "begin";
	public static String isaProcEnd = "end";
	public static String isaOp = "definition";
	public static String opExpLeft = "\\<lparr>";
	public static String opExpRight = "\\<rparr>";
	public static String opTurn = "\\<turnstile>";
	public static String isaMainAction = "MainAction";
	

	private static String skip = "Skip";
	private static String stop = "Stop";
	private static String chaos = "Chaos";
	private static String div = "Div";
	private static String wait = "Wait ";
	private static String comm = " -> ";
	private static String guardLeftBrack = "[";
	private static String guardRightBrack = "]";
	private static String guardSep = " & ";
	private static String seqComp = " ; ";
	private static String extChoice = " \\<box> ";
	private static String intChoice = " |-| ";
	private static String interrupt = " /\\ ";
	private static String timeIntLeft = "/(";
	private static String timeIntRight = ")\\";
	private static String timeout = "[>";
	private static String timeoutLeft = "[(";
	private static String timeoutRight = ")>";
	private static String hiding = " \\\\ ";
	private static String startsby = " startby ";
	private static String endsby = " endby ";
	private static String mu = "mu ";
	private static String interleave = " ||| ";
	private static String syncParallel = " || ";
	
//	public  static ThmNode getIsabelleActionProcess(ILexNameToken name, AActionProcess proc)
//	{	
//		ThmNode tn = null;
//		
//		LinkedList<PDefinition> pdef = proc.getDefinitionParagraphs();
//		PAction action = proc.getAction();
//	
//		for (PDefinition def : pdef)
//		{
//		}
//	
//
//		return tn;
//	}
	
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
	

	public static LinkedList<ThmNode> restrictExtOperationsDeps(
			LinkedList<ThmNode> opNodes, LinkedList<ILexNameToken> procNodeNames) {

		for(ThmNode o: opNodes)
		{
			o.restrictDeps(procNodeNames);
		}		
		return opNodes;
	}

	

	public static LinkedList<ThmNode> getIsabelleOperations(
			LinkedList<SCmlOperationDefinition> operations, LinkedList<ILexNameToken> svars) {
		LinkedList<ThmNode> tnl = new LinkedList<ThmNode>();
		
		for(SCmlOperationDefinition op : operations)
			tnl.add(ThmProcessUtil.getIsabelleOperation(op, svars));
		
		return tnl;
	}
	
	
	public static LinkedList<ILexNameToken> getIsabelleOperationsDeps(
			LinkedList<ThmNode> operations) 
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		for(ThmNode op : operations)
			nodeDeps.addAll(op.getDepIds());
		return nodeDeps;
	}

	private static ThmNode getIsabelleOperation(SCmlOperationDefinition op, LinkedList<ILexNameToken> svars) {
		ThmNode tn = null;
		if (op instanceof AImplicitCmlOperationDefinition)
		{
			AImplicitCmlOperationDefinition imOp = (AImplicitCmlOperationDefinition) op;
			LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

			String pre = null;
			String post = null;
			LinkedList<APatternListTypePair> params = imOp.getParameterPatterns();			
			//Deal with the parameters
			//Find bound values to exclude from dependancy list and add node dependancies
			LinkedList<ILexNameToken> bvars = new LinkedList<ILexNameToken>();
			for(APatternListTypePair p : params)
			{
				for(PPattern pat : p.getPatterns())
				{
					bvars.add(((AIdentifierPattern) pat).getName());
				}
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(p.getType()));
			}	
			//Add return type(s) to dependancy list and list of bound values
			for(APatternTypePair p : imOp.getResult())
			{
				bvars.add(((AIdentifierPattern) p.getPattern()).getName());
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(p.getType()));
			}		
			
			if (imOp.getPrecondition() != null)
			{
				pre = ThmExprUtil.getIsabelleExprStr(svars, bvars, imOp.getPrecondition());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, imOp.getPrecondition()));
			}
			if (imOp.getPostcondition() != null)
			{
				post = ThmExprUtil.getIsabelleExprStr(svars, bvars, imOp.getPostcondition());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, imOp.getPostcondition()));

			}
			tn = new ThmNode(imOp.getName(), nodeDeps, new ThmImplicitOperation(imOp.getName().toString(), params, pre, post));
		}
		else if (op instanceof AExplicitCmlOperationDefinition)
		{
			AExplicitCmlOperationDefinition exOp = (AExplicitCmlOperationDefinition) op;
			LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
			LinkedList<PPattern> params = exOp.getParameterPatterns();
			//Need parameters for local bound vars
			LinkedList<ILexNameToken> bvars = new LinkedList<ILexNameToken>();
			for(PPattern p : params)
			{
				bvars.add(((AIdentifierPattern) p).getName());
			}
			//Deal with the parameters
			//add the parameter types as dependancies
			AOperationType opType = (AOperationType) exOp.getType();
			for(PType pType : opType.getParameters())
			{
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(pType));
			}
			//Add result type to dependancy list
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(opType.getResult()));
			
			
			//TODO: Don't think it's handling the body, or params quite right (op1 in TPTest)
			String body = ThmProcessUtil.getIsabelleStatementStr(exOp.getBody(), svars, bvars);
			String pre = null;
			String post = null;
			if (exOp.getPrecondition() != null)
			{
				pre = ThmExprUtil.getIsabelleExprStr(svars, bvars, exOp.getPrecondition());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, exOp.getPrecondition()));
			}
			if (exOp.getPostcondition() != null)
			{
				post = ThmExprUtil.getIsabelleExprStr(svars, bvars, exOp.getPostcondition());
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, exOp.getPostcondition()));

			}
			
			tn = new ThmNode(exOp.getName(), nodeDeps, new ThmExplicitOperation(exOp.getName().toString(), params, pre, post, body.toString()));
		}
		return tn;
	}

	public static LinkedList<ThmNode> getIsabelleActions(
			LinkedList<AActionDefinition> actions,
			LinkedList<ILexNameToken> svars,
			LinkedList<ILexNameToken> bvars) {
		LinkedList<ThmNode> tnl = new LinkedList<ThmNode>();
			
		for(AActionDefinition act : actions)
			tnl.add(ThmProcessUtil.getIsabelleAction(act, svars, bvars));
		
		return tnl;
	}

	public static ThmNode getIsabelleAction(AActionDefinition act, LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars)
	{
		ThmNode tn = null;
		
		ILexNameToken actName = act.getName();
		String actString = ThmProcessUtil.getIsabelleActionString(act.getAction(), svars, bvars);
		
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		tn = new ThmNode(actName, nodeDeps, new ThmAction(actName.toString(), actString));

		return tn;
	}

	public static String getIsabelleActionString(PAction act, LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars)
	{
		
		if(act instanceof ASkipAction)
		{
			return ThmProcessUtil.skip;
		}
		else if(act instanceof AStopAction)
		{
			return ThmProcessUtil.stop;
		}
		else if(act instanceof AChaosAction)
		{
			return ThmProcessUtil.chaos;
		}
		else if(act instanceof ADivAction)
		{
			return ThmProcessUtil.div;
		}
		else if(act instanceof AWaitAction)
		{
			AWaitAction a = (AWaitAction) act;
			return ThmProcessUtil.wait + ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), a.getExpression());
		}
		else if(act instanceof ACommunicationAction)
		{
			ACommunicationAction a = (ACommunicationAction) act;
			
			
			StringBuilder params = new StringBuilder();
			for( PCommunicationParameter p: a.getCommunicationParameters())
			{
				if(p instanceof AReadCommunicationParameter)
				{
					AReadCommunicationParameter cp = (AReadCommunicationParameter) p;
					PPattern patt = cp.getPattern();
					patt.getDefinitions();
					//TODO: Handle read comms
				}
				else if (p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
					params.append("!");
					params.append(ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), cp.getExpression()));
					
				}else
				{
					params.append(".");
					params.append(ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), p.getExpression()));
				}
				//		bvars needed?
			}
			
			return (a.getIdentifier().toString() + params.toString() + ThmProcessUtil.comm + ThmProcessUtil.getIsabelleActionString(a.getAction(), svars, bvars));
		}
		else if(act instanceof AGuardedAction)
		{
			AGuardedAction a = (AGuardedAction) act;
			
			return ThmProcessUtil.guardLeftBrack + ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), a.getExpression()) + ThmProcessUtil.guardRightBrack + ThmProcessUtil.guardSep + ThmProcessUtil.getIsabelleActionString(a.getAction(), svars, bvars);
		}
		else if(act instanceof ASequentialCompositionAction)
		{
			ASequentialCompositionAction a = (ASequentialCompositionAction) act;
			
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.seqComp + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof AExternalChoiceAction)
		{
			AExternalChoiceAction a = (AExternalChoiceAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.extChoice + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof AInternalChoiceAction)
		{
			AInternalChoiceAction a = (AInternalChoiceAction) act;
			
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.intChoice + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof AInterruptAction)
		{
			AInterruptAction a = (AInterruptAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.interrupt + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof ATimedInterruptAction)
		{
			ATimedInterruptAction a = (ATimedInterruptAction) act;
			
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.timeIntLeft +  ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), a.getTimeExpression()) + ThmProcessUtil.timeIntRight + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof AUntimedTimeoutAction)
		{
			AUntimedTimeoutAction a = (AUntimedTimeoutAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.timeout + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof ATimeoutAction)
		{
			ATimeoutAction a = (ATimeoutAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.timeoutLeft +  ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), a.getTimeoutExpression()) + ThmProcessUtil.timeoutRight + ThmProcessUtil.getIsabelleActionString(a.getRight(), svars, bvars);
		}
		else if(act instanceof AHidingAction)
		{
			AHidingAction a = (AHidingAction) act;
			String actStr = ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars);
			String chanStr = ThmExprUtil.getIsabelleVarsetExpr(a.getChansetExpression());
			return actStr + ThmProcessUtil.hiding + chanStr;
		}
		else if(act instanceof AStartDeadlineAction)
		{
			AStartDeadlineAction a = (AStartDeadlineAction) act;

			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.startsby + ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getExpression());
		}
		else if(act instanceof AEndDeadlineAction)
		{
			AEndDeadlineAction a = (AEndDeadlineAction) act;	
			return ThmProcessUtil.getIsabelleActionString(a.getLeft(), svars, bvars) + ThmProcessUtil.endsby + ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getExpression());	
		}
		else if(act instanceof AChannelRenamingAction)
		{
			AChannelRenamingAction a = (AChannelRenamingAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getAction(), svars, bvars) + ThmExprUtil.getIsabelleRenamingExpr(a.getRenameExpression());
		}
		else if(act instanceof AMuAction)
		{
			AMuAction a = (AMuAction) act;
			a.getIdentifiers();
			a.getActions();
			//TODO: handle mu
		}
		if(act instanceof AReferenceAction)
		{
			AReferenceAction a = (AReferenceAction) act;
			StringBuilder argStr = new StringBuilder();
			LinkedList<PExp> args = a.getArgs();
			if (args.size() != 0)
			{
				argStr.append("(");
				for (Iterator<PExp> itr = a.getArgs().listIterator(); itr.hasNext(); ) {
					PExp e = itr.next();
					
					argStr.append(ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(),e));
					//If there are remaining expressions, add a ","
					if(itr.hasNext()){	
						argStr.append(", ");
					}
				}
				argStr.append(")");
			}
			
			return a.getName().toString() + argStr.toString();
		}
		else if (act instanceof SParallelAction)
		{
			return ThmProcessUtil.getIsabelleParallelActionStr(act, svars, bvars);
		}
		else if (act instanceof SReplicatedAction)
		{
			return ThmProcessUtil.getIsabelleReplicatedActionStr(act, svars, bvars);
		}
		else if(act instanceof SStatementAction)
		{
			return ThmProcessUtil.getIsabelleStatementStr(act, svars, bvars);
		}
		return "action not handled";	
	}

	
	
	private static String getIsabelleReplicatedActionStr(PAction act,
			LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars) {
		//TODO: Handle replicated actions
		
//		{sequentialComposition}
//        | {externalChoice}
//        | {internalChoice}
//        | {commonInterleaving}
//                [namesetExpression]:VarsetExpression
//        | {interleaving}
//                [namesetExpression]:VarsetExpression
//        | {generalisedParallelism}
//                [chansetExpression]:VarsetExpression
//                [namesetExpression]:VarsetExpression
//        | {alphabetisedParallelism}
//                [namesetExpression]:VarsetExpression
//                [chansetExpression]:VarsetExpression
//        | {synchronousParallelism}
//                [namesetExpression]:VarsetExpression
		
		return "replicated actions not handled";
	}

	private static String getIsabelleParallelActionStr(PAction act,
			LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars) {

		if(act instanceof AInterleavingParallelAction)
		{
			AInterleavingParallelAction a = (AInterleavingParallelAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeftAction(), svars, bvars) + ThmProcessUtil.interleave  + ThmProcessUtil.getIsabelleActionString(a.getRightAction(), svars, bvars);
		}
		else if(act instanceof ASynchronousParallelismParallelAction)
		{
			ASynchronousParallelismParallelAction a = (ASynchronousParallelismParallelAction) act;
			return ThmProcessUtil.getIsabelleActionString(a.getLeftAction(), svars, bvars) + ThmProcessUtil.syncParallel  + ThmProcessUtil.getIsabelleActionString(a.getRightAction(), svars, bvars);
		}
		else if(act instanceof AGeneralisedParallelismParallelAction)
		{
//			AGeneralisedParallelismParallelAction a = (AGeneralisedParallelismParallelAction) act;
//	        | {generalisedParallelism}
//	                [chansetExpression]:VarsetExpression

			//TODO: Handle generalised parallel
		}
		else if(act instanceof AAlphabetisedParallelismParallelAction)
		{
//			AAlphabetisedParallelismParallelAction a = (AAlphabetisedParallelismParallelAction) act;
//          [leftChansetExpression]:VarsetExpression
//          [rightChansetExpression]:VarsetExpression
			//TODO: Handle alpha parallel
		}
		return "parallel actions not handled";
	}

	private static String getIsabelleStatementStr(PAction stmt,
			LinkedList<ILexNameToken> svars, 
			LinkedList<ILexNameToken> bvars) {
		if(stmt instanceof ACallStatementAction)
		{
			ACallStatementAction a = (ACallStatementAction) stmt;

			StringBuilder args = new StringBuilder();
			
			for (Iterator<PExp> itr = a.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				args.append(ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(),e));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					args.append(", ");
				}
			}
			return a.getName().toString() + "(" + args.toString() + ")";		
			
		}
		else if (stmt instanceof ABlockStatementAction)
		{
			ABlockStatementAction a = (ABlockStatementAction) stmt;
			StringBuilder assignStr = new StringBuilder();
			if(a.getDeclareStatement() != null) 
			{
				LinkedList<PDefinition> assigns = a.getDeclareStatement().getAssignmentDefs();
			}
			//TODO: Handle local defs
			
			String actStr = ThmProcessUtil.getIsabelleStatementStr(a.getAction(), svars, bvars);
			return actStr; //TODO: include local defs.
		}
		return "statement not handled";
		
//        = {let} [action]:action [localDefinitions]:definition*
//        | {nonDeterministicIf}
//                [alternatives]:action.#Statement.nonDeterministicAlt*
//        | {nonDeterministicAlt}
//                [guard]:exp
//                [action]:action
//        | {if}
//                [ifExp]:exp
//                [thenStm]:action
//                [elseIf]:action.#Statement.elseIf*
//                [elseStm]:action
//        | {elseIf}
//                [elseIf]:exp
//                [thenStm]:action
//        | {cases}
//                [exp]:exp
//                [cases]:alternativeAction.case*
//                [others]:action
//        | #GeneralAssignment
//        | {specification}
//                [externals]:clause.external*
//                [precondition]:exp
//                [postcondition]:exp
//        | {assignmentCall}
//                [designator]:exp
//                [call]:action.#Statement.call
//        | {return}
//                [exp]:exp
//    | {notYetSpecified}
//                [opname]:LexNameToken
//                [args]:exp*
//    | {new}
//        [destination]:exp
//        [className]:LexNameToken
//        [args]:exp*
//        (classdef):definition.#class
//        (ctorDefinition):definition
//    | {nonDeterministicDo}
//        [alternatives]:action.#Statement.nonDeterministicAlt*
//    | {forSet}
//        [pattern]:pattern
//        [set]:exp
//        [action]:action
//    | {forIndex}
//        [var]:LexNameToken
//        [from]:exp
//        [to]:exp
//        [by]:exp
//        [action]:action
//    | {forSequence}
//        [patternBind]:patternBind.def
//        [exp]:exp
//        [action]:action
//        (seqType):type.#seq
//    | {while}
//        [condition]:exp
//        [action]:action
//        | {declare}  [assignmentDefs]:definition*
//        ;
		
		
	}
	
	public static String getIsabelleStateInv(AStateDefinition node)
	{
		//TODO: Need correcting for STATE.
		String inv = "";
		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			LinkedList<ILexNameToken> svars = new LinkedList<ILexNameToken>();
			LinkedList<ILexNameToken> evars = new LinkedList<ILexNameToken>();
			evars.add(((AIdentifierPattern) invPatt).getName());
			inv = (" " + ThmTypeUtil.isaInv  + " " + invPatt.toString() + " == " + ThmExprUtil.getIsabelleExprStr(svars, evars, invExp));
		}
		
		return inv;
	}
	
	
	public static LinkedList<ILexNameToken> getIsabelleStateInvDeps(AStateDefinition node)
	{
		//TODO: Need correcting for STATE.
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		PExp invExp = node.getInvExpression();
		PPattern invPatt = node.getInvPattern();
		if(invExp != null && invPatt != null){
			LinkedList<ILexNameToken> evars = new LinkedList<ILexNameToken>();
			evars.add(((AIdentifierPattern) invPatt).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(evars, invExp));
		}
		return nodeDeps;
	}


			
}
