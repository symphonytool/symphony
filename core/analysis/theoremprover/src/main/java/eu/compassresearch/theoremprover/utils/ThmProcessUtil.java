package eu.compassresearch.theoremprover.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AForIndexStatementAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AForSetStatementAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AMultipleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.ANotYetSpecifiedStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.ASpecificationStatementAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
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
	
	/***
	 * Method to retrieve all state variable names from a collection of state defintions
	 * @param statements - the collection of state variables
	 * @return a list of names (as ILexNameTokens)
	 */
	public static LinkedList<ILexNameToken> getStateNames(LinkedList<AStateDefinition> statements)
	{
		//first we need to get all the state identifier names so expressions use correct
		//reference
		LinkedList<ILexNameToken> statenames = new LinkedList<ILexNameToken>();
		//for each state definiton
		for (AStateDefinition pdef : statements)
		{
			//for each state def
			for (PDefinition sdef : pdef.getStateDefs())
			{
				//if the state defintion is an assignment defn
				if (sdef instanceof AAssignmentDefinition)
				{
					AAssignmentDefinition st = (AAssignmentDefinition) sdef;
	
					//get the name and add it to the list
					statenames.add(st.getName());
				}
			}
		}
		return statenames;
	}

	/**
	 * Method to retrieve all state variable names from a collection of operations definitions
	 * @param operations - the collection of operations
	 * @return a list of names (as ILexNameTokens)
	 */
	public static LinkedList<ILexNameToken> getOperationNames(
			LinkedList<SCmlOperationDefinition> operations) {
		
		LinkedList<ILexNameToken> opNames = new LinkedList<ILexNameToken>();
		//for each operation
		for(SCmlOperationDefinition op : operations)
			//get the name and add it to the list
			opNames.add(op.getName());
		
		return opNames;
	}

	/**
	 * Method to retrieve all state variable names from a collection of actions definitions
	 * @param actions - the collection of actions
	 * @return a list of names (as ILexNameTokens)
	 */
	public static LinkedList<ILexNameToken> getActionNames(
			LinkedList<AActionDefinition> actions) {

		LinkedList<ILexNameToken> actNames = new LinkedList<ILexNameToken>();
		//for each operation
		for(AActionDefinition a : actions)
			//get the name and add it to the list
			actNames.add(a.getName());
		return actNames;
	}

	/***
	 * Method to remove a list of dependancies from a list of dependancies
	 //TODO: MOVE SOMEWHERE ELSE?
	 * @param nodeDeps
	 * @param procNames
	 * @return
	 */
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
	
	
	/**
	 * Method to restrict node dependancy list to only those in a second set 
	 * @param nodeDeps
	 * @param procNames
	 * @return
	 */
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
			
			String body = ThmProcessUtil.getIsabelleActionString(exOp.getBody(), svars, bvars);
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

	/****
	 * Operation to get the thm nodes for a list of actions
	 * @param actions - list of Action Defintion nodes
	 * @param svars - state variable names
	 * @param bvars - bound variable names
	 * @return the list of thm nodes generated
	 */
	public static LinkedList<ThmNode> getIsabelleActions(
			LinkedList<AActionDefinition> actions,
			LinkedList<ILexNameToken> svars,
			LinkedList<ILexNameToken> bvars) {
		LinkedList<ThmNode> tnl = new LinkedList<ThmNode>();
			
		//for each Action Definiton node
		for(AActionDefinition act : actions)
			tnl.add(ThmProcessUtil.getIsabelleAction(act, svars, bvars));
		
		return tnl;
	}

	/***
	 * Operation to provide a theorem node for a CML action.
	 * 
	 * @param act - the action definition
	 * @param svars - process state variable names
	 * @param bvars - bound variable names
	 * @return the theorem node prduced
	 */
	private static ThmNode getIsabelleAction(AActionDefinition act, LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars)
	{
		ThmNode tn = null;
		//get the action name
		ILexNameToken actName = act.getName();
		//get the Isabelle string for the action node's action.
		String actString = ThmProcessUtil.getIsabelleActionString(act.getAction(), svars, bvars);
		//obtain the action dependancies
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		//create the theorem node.
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
			return ThmProcessUtil.wait + ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getExpression());
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
					if(patt instanceof AIdentifierPattern)
					{
						AIdentifierPattern ip = (AIdentifierPattern) patt;
						params.append("?");
						params.append(ip.getName().toString());
						bvars.add(ip.getName());
					}
					patt.getDefinitions();
				}
				else if (p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
					params.append("!");
					params.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, cp.getExpression()));
					
				}else
				{
					params.append(".");
					params.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, p.getExpression()));
				}
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
			StringBuilder idStr = new StringBuilder();
			for (Iterator<ILexIdentifierToken> itr = a.getIdentifiers().listIterator(); itr.hasNext(); ) {
				ILexIdentifierToken id = itr.next();
				
				idStr.append(id.getName().toString());
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					idStr.append(" ");
				}
			}
			
			StringBuilder actStr = new StringBuilder();
			for (PAction pa : a.getActions())
			{
				actStr.append(ThmProcessUtil.getIsabelleActionString(pa, svars, bvars));
			}
			return "\\<mu>" + idStr + "." + actStr;
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
					
					argStr.append(ThmExprUtil.getIsabelleExprStr(svars, bvars,e));
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
		return "(*unknown action not handled*)";	
	}

	
	
	private static String getIsabelleReplicatedActionStr(PAction act,
			LinkedList<ILexNameToken> svars, LinkedList<ILexNameToken> bvars) {
		//TODO: Handle replicated actions
		if(act instanceof ASequentialCompositionReplicatedAction)
		{
//			ASequentialCompositionReplicatedAction a = (ASequentialCompositionReplicatedAction) act;
//			a.getReplicatedAction();
//			a.getReplicationDeclaration();
		}
		else if(act instanceof AExternalChoiceReplicatedAction)
		{
//			AExternalChoiceReplicatedAction a = (AExternalChoiceReplicatedAction) act;
		}
		else if(act instanceof AInternalChoiceReplicatedAction)
		{
//			AInternalChoiceReplicatedAction a = (AInternalChoiceReplicatedAction) act;
		}
		else if(act instanceof ACommonInterleavingReplicatedAction)
		{
//			ACommonInterleavingReplicatedAction a = (ACommonInterleavingReplicatedAction) act;
//          [namesetExpression]:VarsetExpression
		}
		else if(act instanceof AInterleavingReplicatedAction)
		{
//			AInterleavingReplicatedAction a = (AInterleavingReplicatedAction) act;
//          [namesetExpression]:VarsetExpression
		}
		else if(act instanceof AGeneralisedParallelismReplicatedAction)
		{
//			AGeneralisedParallelismReplicatedAction a = (AGeneralisedParallelismReplicatedAction) act;
//          [chansetExpression]:VarsetExpression
//          [namesetExpression]:VarsetExpression
		}
		else if(act instanceof AAlphabetisedParallelismReplicatedAction)
		{
//			AAlphabetisedParallelismReplicatedAction a = (AAlphabetisedParallelismReplicatedAction) act;
//          [namesetExpression]:VarsetExpression
//          [chansetExpression]:VarsetExpression
		}
		else if(act instanceof ASynchronousParallelismReplicatedAction)
		{
//			ASynchronousParallelismReplicatedAction a = (ASynchronousParallelismReplicatedAction) act;
//          [namesetExpression]:VarsetExpression
		}
		return "(*replicated actions not handled*)";
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
			AGeneralisedParallelismParallelAction a = (AGeneralisedParallelismParallelAction) act;
			String left = ThmProcessUtil.getIsabelleActionString(a.getLeftAction(), svars, bvars);
			String right = ThmProcessUtil.getIsabelleActionString(a.getRightAction(), svars, bvars);
			String chExp = ThmExprUtil.getIsabelleVarsetExpr(a.getChansetExpression());
			
			return left + "[|" + chExp +"|]" + right;
		}
		else if(act instanceof AAlphabetisedParallelismParallelAction)
		{
			AAlphabetisedParallelismParallelAction a = (AAlphabetisedParallelismParallelAction) act;
			String left = ThmProcessUtil.getIsabelleActionString(a.getLeftAction(), svars, bvars);
			String right = ThmProcessUtil.getIsabelleActionString(a.getRightAction(), svars, bvars);
			String leftChExp = ThmExprUtil.getIsabelleVarsetExpr(a.getLeftChansetExpression());
			String rightChExp = ThmExprUtil.getIsabelleVarsetExpr(a.getRightChansetExpression());
			
			return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
		}
		return "(*unknown parallel action not handled*)";
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
				
				args.append(ThmExprUtil.getIsabelleExprStr(svars, bvars,e));
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
			LinkedList<String> varsStr = new LinkedList<String>();
			StringBuilder assignStr = new StringBuilder();
			LinkedList<PDefinition> assigns = new LinkedList<PDefinition>();

			String blockStr = ThmProcessUtil.getIsabelleActionString(a.getAction(), svars, bvars);
			if(a.getDeclareStatement() != null) 
			{
				assigns = a.getDeclareStatement().getAssignmentDefs();
				
				for (PDefinition pdef : assigns)
				{
					AAssignmentDefinition aDef = (AAssignmentDefinition) pdef;
					varsStr.add(aDef.getName().toString());
					assignStr.append(aDef.getName().toString() + ":=" + ThmExprUtil.getIsabelleExprStr(svars, bvars, aDef.getExpression()));
				}

				blockStr = assignStr.toString() + blockStr;
				for(String as : varsStr)
				{
					blockStr = "var " + as + ";" + blockStr + "; end " + as; 
				}
			}
			
			return blockStr; 
		}
		else if(stmt instanceof ALetStatementAction)
		{
//			ALetStatementAction a = (ALetStatementAction) stmt;
			//[action]:action [localDefinitions]:definition*
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof ANonDeterministicIfStatementAction)
		{
			ANonDeterministicIfStatementAction a = (ANonDeterministicIfStatementAction) stmt;
//           [alternatives]:action.#Statement.nonDeterministicAlt*

		}
		else if(stmt instanceof ANonDeterministicAltStatementAction)
		{
//			ANonDeterministicAltStatementAction a = (ANonDeterministicAltStatementAction) stmt;
//           [guard]:exp
//           [action]:action
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof AIfStatementAction)
		{
			AIfStatementAction a = (AIfStatementAction) stmt;
//           [ifExp]:exp
//           [thenStm]:action
//           [elseIf]:action.#Statement.elseIf*
//           [elseStm]:action
		}
		else if(stmt instanceof AElseIfStatementAction)
		{
			AElseIfStatementAction a = (AElseIfStatementAction) stmt;
//           [elseIf]:exp
//           [thenStm]:action
		}
		else if(stmt instanceof ACasesStatementAction)
		{
//			ACasesStatementAction a = (ACasesStatementAction) stmt;
//           [exp]:exp
//           [cases]:alternativeAction.case*
//           [others]:action
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof AMultipleGeneralAssignmentStatementAction)
		{
//			AMultipleGeneralAssignmentStatementAction a = (AMultipleGeneralAssignmentStatementAction) stmt;
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof ASingleGeneralAssignmentStatementAction)
		{
			ASingleGeneralAssignmentStatementAction a = (ASingleGeneralAssignmentStatementAction) stmt;
		}
		else if(stmt instanceof ASpecificationStatementAction)
		{
//			ASpecificationStatementAction a = (ASpecificationStatementAction) stmt;
//           [externals]:clause.external*
//           [precondition]:exp
//           [postcondition]:exp
			//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof AAssignmentCallStatementAction)
		{
			 AAssignmentCallStatementAction a = (AAssignmentCallStatementAction) stmt;
//           [designator]:exp
//           [call]:action.#Statement.call
		}
		else if(stmt instanceof ANotYetSpecifiedStatementAction)
		{
			 ANotYetSpecifiedStatementAction a = (ANotYetSpecifiedStatementAction) stmt;
//           [opname]:LexNameToken
//           [args]:exp*
		}
		else if(stmt instanceof AReturnStatementAction)
		{
//			 AReturnStatementAction a = (AReturnStatementAction) stmt;
//           [exp]:exp
				//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof ANewStatementAction)
		{
//			ANewStatementAction a = (ANewStatementAction) stmt;
//	        [destination]:exp
//	        [className]:LexNameToken
//	        [args]:exp*
//	        (classdef):definition.#class
//	        (ctorDefinition):definition
			//TODO: NOT YET HANDLED
		}
		else  if(stmt instanceof ANonDeterministicDoStatementAction)
		{
			ANonDeterministicDoStatementAction a = (ANonDeterministicDoStatementAction) stmt;
//	        [alternatives]:action.#Statement.nonDeterministicAlt*
		}
		else if(stmt instanceof AForSetStatementAction)
		{
//			AForSetStatementAction a = (AForSetStatementAction) stmt;
//	        [pattern]:pattern
//	        [set]:exp
//	        [action]:action
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof AForIndexStatementAction)
		{
//			AForIndexStatementAction a = (AForIndexStatementAction) stmt;
//	        [var]:LexNameToken
//	        [from]:exp
//	        [to]:exp
//	        [by]:exp
//	        [action]:action
			//TODO: NOT YET HANDLED

		}
		else if(stmt instanceof AForSequenceStatementAction)
		{
//			AForSequenceStatementAction a = (AForSequenceStatementAction) stmt;
//	        [patternBind]:patternBind.def
//	        [exp]:exp
//	        [action]:action
//	        (seqType):type.#seq
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof AWhileStatementAction)
		{
			AWhileStatementAction a = (AWhileStatementAction) stmt;
//	        [condition]:exp
//	        [action]:action
//	        | {declare}  [assignmentDefs]:definition*
		}
		return "(*statement not handled*)";
		
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
