package eu.compassresearch.theoremprover.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
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
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.theoremprover.thms.ThmAction;
import eu.compassresearch.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmImplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmProcAction;
import eu.compassresearch.theoremprover.thms.ThmState;
import eu.compassresearch.theoremprover.visitors.TPVisitor;


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
	
	private static String ifLeft = "\\<triangleleft>";
	private static String ifRight = "\\<triangleright>";
	private static String assign = " := ";
	private static String isaWhile = "while ";
	private static String isaDo = " do ";
	private static String isaOd = " od ";
	
		
	/***
	 * Method to retrieve all state variable names from a collection of state definitions
	 * @param statements - the collection of state variables
	 * @return a list of names (as ILexNameTokens)
	 */
	public static LinkedList<ILexNameToken> getStateNames(LinkedList<AStateDefinition> statements)
	{
		//first we need to get all the state identifier names so expressions use correct
		//reference
		LinkedList<ILexNameToken> statenames = new LinkedList<ILexNameToken>();
		//for each state definition
		for (AStateDefinition pdef : statements)
		{
			//for each state definition
			for (PDefinition sdef : pdef.getStateDefs())
			{
				//if the state definition is an assignment definition
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
	 * Method to remove a list of dependencies from a list of dependencies
	 //TODO: MOVE SOMEWHERE ELSE?
	 * @param nodeDeps
	 * @param procNames
	 * @return
	 */
	public static LinkedList<ILexNameToken> removeProcessDeps(
			LinkedList<ILexNameToken> nodeDeps,
			LinkedList<ILexNameToken> procNames) {
		
		//Need to add remove all inter-process dependencies 
		//For each name used within a process		
		for(ILexNameToken pn :procNames)
		{
			//for each dependency 
			for(Iterator<ILexNameToken> itr = nodeDeps.listIterator(); itr.hasNext(); )
			{
				ILexNameToken nd = itr.next();
				//if the dependency is a process name, remove it.
				if(pn.toString().equals(nd.toString()))
				{
					itr.remove();
				}
			}
		}
		return nodeDeps;
	}
	
	
	/**
	 * Method to restrict node dependency list to only those in a second set 
	 * @param nodeDeps
	 * @param procNames
	 * @return
	 */
	public static LinkedList<ILexNameToken> removeExtDeps(
			LinkedList<ILexNameToken> nodeDeps,
			LinkedList<ILexNameToken> procNames) {
		
		LinkedList<ILexNameToken> nodeNames = new LinkedList<ILexNameToken>();
		
		//Need to add remove all extra-process dependencies 
		//For each node dependency
		for(ILexNameToken nd : nodeDeps)
		{
			//For each name used within a process		
			for(ILexNameToken pn :procNames)
			{
				//if the depandency is a process name, then add to the new list
				if(pn.toString().equals(nd.toString()))
				{
					nodeNames.add(nd);
				}
			}
		}
		
		//return only the node dependencies that are intra-process names 
		return nodeNames;
	}
	

	
	/***
	 * Method to get the string of a process (not an Action Process)
	 * @param procProc
	 * @return
	 */
	public static String getIsabelleProcessString(PProcess procProc)
	{
		if (procProc instanceof ASequentialCompositionProcess)
		{
			ASequentialCompositionProcess p = (ASequentialCompositionProcess) procProc;
					
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.seqComp + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		else if(procProc instanceof AExternalChoiceProcess)
		{
			AExternalChoiceProcess p = (AExternalChoiceProcess) procProc;
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.extChoice + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		else if(procProc instanceof AInternalChoiceProcess)
		{
			AInternalChoiceProcess p = (AInternalChoiceProcess) procProc;
			
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.intChoice + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		if(procProc instanceof AInterleavingProcess)
		{
			AInterleavingProcess p = (AInterleavingProcess) procProc;
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.interleave  + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		else if(procProc instanceof ASynchronousParallelismProcess)
		{
			ASynchronousParallelismProcess p = (ASynchronousParallelismProcess) procProc;
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.syncParallel  + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		else if(procProc instanceof AGeneralisedParallelismProcess)
		{
			AGeneralisedParallelismProcess p = (AGeneralisedParallelismProcess) procProc;
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String right = ThmProcessUtil.getIsabelleProcessString(p.getRight());
			String chExp = ThmExprUtil.getIsabelleVarsetExpr(p.getChansetExpression());
			
			return left + "[|" + chExp +"|]" + right;
		}
		else if(procProc instanceof AAlphabetisedParallelismProcess)
		{
			AAlphabetisedParallelismProcess p = (AAlphabetisedParallelismProcess) procProc;
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String right = ThmProcessUtil.getIsabelleProcessString(p.getRight());
			String leftChExp = ThmExprUtil.getIsabelleVarsetExpr(p.getLeftChansetExpression());
			String rightChExp = ThmExprUtil.getIsabelleVarsetExpr(p.getRightChansetExpression());
			
			return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
		}
		else if(procProc instanceof AInterruptProcess)
		{
			AInterruptProcess p = (AInterruptProcess) procProc;
			return ThmProcessUtil.getIsabelleProcessString(p.getLeft()) + ThmProcessUtil.interrupt + ThmProcessUtil.getIsabelleProcessString(p.getRight());
		}
		else if(procProc instanceof ATimedInterruptProcess)
		{
			ATimedInterruptProcess p = (ATimedInterruptProcess) procProc;
			
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft()) ;
			String expr = ThmExprUtil.getIsabelleExprStr(new LinkedList<ILexNameToken>(), new LinkedList<ILexNameToken>(), p.getTimeExpression());
			String right = ThmProcessUtil.getIsabelleProcessString(p.getRight());

			return left + ThmProcessUtil.timeIntLeft + expr + ThmProcessUtil.timeIntRight + right;
		}
		else if(procProc instanceof AUntimedTimeoutProcess)
		{
			AUntimedTimeoutProcess p = (AUntimedTimeoutProcess) procProc;
			
			String left =  ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String right = ThmProcessUtil.getIsabelleProcessString(p.getRight());

			return left + ThmProcessUtil.timeout + right;
		}
		else if(procProc instanceof ATimeoutProcess)
		{
			ATimeoutProcess p = (ATimeoutProcess) procProc;
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String expr = ThmExprUtil.getIsabelleExprStr(new LinkedList<ILexNameToken>(), new LinkedList<ILexNameToken>(), p.getTimeoutExpression());
			String right = ThmProcessUtil.getIsabelleProcessString(p.getRight());

			return left + ThmProcessUtil.timeoutLeft +  expr + ThmProcessUtil.timeoutRight + right;
		}
		else if(procProc instanceof AHidingProcess)
		{
			AHidingProcess p = (AHidingProcess) procProc;
			String actStr = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String chanStr = ThmExprUtil.getIsabelleVarsetExpr(p.getChansetExpression());
			return actStr + ThmProcessUtil.hiding + chanStr;
		}
		else if(procProc instanceof AStartDeadlineProcess)
		{
			AStartDeadlineProcess p = (AStartDeadlineProcess) procProc;
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String expr = ThmExprUtil.getIsabelleExprStr(new LinkedList<ILexNameToken>(), new LinkedList<ILexNameToken>(), p.getExpression());		
			return left + ThmProcessUtil.startsby + expr;
		}
		else if(procProc instanceof AEndDeadlineProcess)
		{
			AEndDeadlineProcess p = (AEndDeadlineProcess) procProc;
			String left = ThmProcessUtil.getIsabelleProcessString(p.getLeft());
			String expr = ThmExprUtil.getIsabelleExprStr(new LinkedList<ILexNameToken>(), new LinkedList<ILexNameToken>(), p.getExpression());	
			return left + ThmProcessUtil.endsby + expr;	
		}		
		else if(procProc instanceof AReferenceProcess)
		{
			AReferenceProcess p = (AReferenceProcess) procProc;
			StringBuilder argStr = new StringBuilder();
			LinkedList<PExp> args = p.getArgs();
			if (args.size() != 0)
			{
				argStr.append("(");
				for (Iterator<PExp> itr = p.getArgs().listIterator(); itr.hasNext(); ) {
					PExp e = itr.next();
					
					argStr.append(ThmExprUtil.getIsabelleExprStr(new LinkedList<ILexNameToken>(), new LinkedList<ILexNameToken>(),e));
					//If there are remaining expressions, add a ","
					if(itr.hasNext()){	
						argStr.append(", ");
					}
				}
				argStr.append(")");
			}
			return p.getProcessName().toString() + argStr.toString();
		}
		return "(*process not handled*)";

	}
	/***
	 * Method to get the string of a process (not an Action Process)
	 * @param procProc
	 * @return
	 */
	public static LinkedList<ILexNameToken> getIsabelleProcessDeps(PProcess procProc)
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		if (procProc instanceof ASequentialCompositionProcess)
		{
			ASequentialCompositionProcess p = (ASequentialCompositionProcess) procProc;
					
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof AExternalChoiceProcess)
		{
			AExternalChoiceProcess p = (AExternalChoiceProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof AInternalChoiceProcess)
		{
			AInternalChoiceProcess p = (AInternalChoiceProcess) procProc;
			
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		if(procProc instanceof AInterleavingProcess)
		{
			AInterleavingProcess p = (AInterleavingProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof ASynchronousParallelismProcess)
		{
			ASynchronousParallelismProcess p = (ASynchronousParallelismProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof AGeneralisedParallelismProcess)
		{
			AGeneralisedParallelismProcess p = (AGeneralisedParallelismProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(p.getChansetExpression()));
		}
		else if(procProc instanceof AAlphabetisedParallelismProcess)
		{
			AAlphabetisedParallelismProcess p = (AAlphabetisedParallelismProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(p.getLeftChansetExpression()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(p.getRightChansetExpression()));
		}
		else if(procProc instanceof AInterruptProcess)
		{
			AInterruptProcess p = (AInterruptProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof ATimedInterruptProcess)
		{
			ATimedInterruptProcess p = (ATimedInterruptProcess) procProc;
			
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft())) ;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(), p.getTimeExpression()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof AUntimedTimeoutProcess)
		{
			AUntimedTimeoutProcess p = (AUntimedTimeoutProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof ATimeoutProcess)
		{
			ATimeoutProcess p = (ATimeoutProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(), p.getTimeoutExpression()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getRight()));
		}
		else if(procProc instanceof AHidingProcess)
		{
			AHidingProcess p = (AHidingProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(p.getChansetExpression()));
		}
		else if(procProc instanceof AStartDeadlineProcess)
		{
			AStartDeadlineProcess p = (AStartDeadlineProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(), p.getExpression()));		

		}
		else if(procProc instanceof AEndDeadlineProcess)
		{
			AEndDeadlineProcess p = (AEndDeadlineProcess) procProc;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleProcessDeps(p.getLeft()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(), p.getExpression()));
		}		
		else if(procProc instanceof AReferenceProcess)
		{
			AReferenceProcess p = (AReferenceProcess) procProc;
			LinkedList<PExp> args = p.getArgs();
			nodeDeps.add(p.getProcessName());
			if (args.size() != 0)
			{
				for (PExp e: p.getArgs()) 
				{
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(),e));				
				}
			}
		}
		return nodeDeps;
	}
	
	
	
	public static ThmNode getIsabelleActionProcess(ILexNameToken procName, AActionProcess act)
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();		
		
		//need to define a new collection of node lists for the definitions inside a 
		//process. This is because we need to limit scope and dependencies within a 
		//action process, and output it as a single (contained) node.
		ThmNodeList actTnl = new ThmNodeList();
		//Require a list of all names used within a process, so to ensure the dependency 
		//relationships within and outside the process can be dealt with.
		LinkedList<ILexNameToken> procNodeNames = new LinkedList<ILexNameToken>();

		//Collect all the statement/operation/action paragraphs and deal with them 
		//all together.
		LinkedList<AStateDefinition> statements = new LinkedList<AStateDefinition>();
		LinkedList<SCmlOperationDefinition> operations = new LinkedList<SCmlOperationDefinition>();
		LinkedList<AActionDefinition> actions = new LinkedList<AActionDefinition>();
		LinkedList<PDefinition> others = new LinkedList<PDefinition>();
		for (PDefinition pdef : act.getDefinitionParagraphs())
		{
			if (pdef instanceof AStateDefinition)
			{
				AStateDefinition sdef = (AStateDefinition) pdef;
				statements.add(sdef);
			}
			else if (pdef instanceof AOperationsDefinition)
			{
				AOperationsDefinition ops = (AOperationsDefinition) pdef;
				operations.addAll(ops.getOperations());
			}
			else if (pdef instanceof AActionsDefinition)
			{
				AActionsDefinition acts = (AActionsDefinition) pdef;
				actions.addAll(acts.getActions());
			}
			else
			{
				others.add(pdef);
			}
		}
		
		//first we need to get all the state identifier names so expressions use correct
		//reference
		LinkedList<ILexNameToken> svars = ThmProcessUtil.getStateNames(statements);
		//Add all state, operation and action names to list
		procNodeNames.addAll(ThmProcessUtil.getStateNames(statements));
		procNodeNames.addAll(ThmProcessUtil.getOperationNames(operations));
		procNodeNames.addAll(ThmProcessUtil.getActionNames(actions));
				
					
		//next generate nodes for the state variables, and add their initialised 
		//assignments to a collection for initialisation in main action
		//Also generate the invariant functions...
		LinkedList<String> initExprs = new LinkedList<String>();
		LinkedList<ILexNameToken> initExprNodeDeps = new LinkedList<ILexNameToken>();
		for (AStateDefinition pdef : statements)
		{
			for (PDefinition sdef : pdef.getStateDefs())
			{
				if (sdef instanceof AAssignmentDefinition)
				{
					AAssignmentDefinition st = (AAssignmentDefinition) sdef;

					//Get the state variable name
					ILexNameToken sName = st.getName();
					LinkedList<ILexNameToken> sNodeDeps = new LinkedList<ILexNameToken>();
					//if the variable is initialised straight away, add it to the initExprs string
					//and get the dependancies
					if (st.getExpression() != null)
					{
						initExprs.add(sName.toString() + ThmProcessUtil.assign + ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), st.getExpression()));
						initExprNodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList<ILexNameToken>(),  st.getExpression()));
						//Add all dependancies to the processes dependancies
						nodeDeps.addAll(initExprNodeDeps);
						//As we only care about the internal dependancies in initExprNodeDeps, remove
						//any dependancies to CML elements external to the process
						initExprNodeDeps = ThmProcessUtil.removeExtDeps(initExprNodeDeps,procNodeNames);
					}
					//if the variable is not initialised straight away, leave it as undefined.
					else
					{
						initExprs.add(sName.toString() + " := undefined");
					}
					//obtain the type of the state variable, and the type dependancies
					String type = ThmTypeUtil.getIsabelleType(st.getType());
					nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(st.getType()));
		
					ThmNode stn = new ThmNode(sName, sNodeDeps, new ThmState(sName.getName(), type));
					actTnl.add(stn);
				}
			}
			
			//TODO: Define state invariants
		}

		//Build the initialisation operation
		StringBuilder initExpStr = new StringBuilder();
		for (Iterator<String> itr = initExprs.listIterator(); itr.hasNext(); ) {
			String ep = itr.next();
					
			initExpStr.append(ep);
			//If there are remaining exprs, add a ","
			if(itr.hasNext()){	
				initExpStr.append("; ");
			}
		}
		//hack a name for the initialisation op
		LexNameToken initName = new LexNameToken("", "IsabelleStateInit", act.getLocation());
		ThmNode stn = new ThmNode(initName, initExprNodeDeps, new ThmExplicitOperation(initName.getName(), new LinkedList<PPattern>(), null, null, initExpStr.toString()));
		actTnl.add(stn);
		
		
		//Handle the operations
		ThmNodeList opNodes = ThmProcessUtil.getIsabelleOperations(operations, svars);
		//Add all operation dependencies to the list of process dependencies
		nodeDeps.addAll(opNodes.getAllNodeDeps());
		//restrict the operation dependencies to only those names used within the process
		opNodes = opNodes.restrictExtOperationsDeps(procNodeNames);
		actTnl.addAll(opNodes);
		
		//Handle the actions.
		//TODO:NEED TO CHECK EACH ACT FOR RECURSION (See notepad :))
		ThmNodeList actNodes = (ThmProcessUtil.getIsabelleActions(actions, svars, new LinkedList<ILexNameToken>()));
		//Add all action dependencies to the list of process dependencies
		nodeDeps.addAll(actNodes.getAllNodeDeps());
		//restrict the action dependencies to only those names used within the process
		actNodes = actNodes.restrictExtOperationsDeps(procNodeNames);
		actTnl.addAll(actNodes);
		
		//sort the state, operation and actions, so that they are in dependency order
		actTnl = TPVisitor.sortThmNodes(actTnl);
		//Remove all inner dependencies from the process dependency list. We only care about the 
		//things external to the process that we depend upon.
		nodeDeps = ThmProcessUtil.removeProcessDeps(nodeDeps,procNodeNames);

		//Obtain the main action string
		PAction mainAction = act.getAction();
		String mainStr = ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + " = IsabelleStateInit; " + ThmProcessUtil.getIsabelleActionString(mainAction, svars, new LinkedList<ILexNameToken>()) +  "\"";
		
		//Finally construct the node to represent the process
		return new ThmNode(procName, nodeDeps, new ThmProcAction(procName.toString(), actTnl.toString(), mainStr));
	}
	

	private static ThmNodeList getIsabelleOperations(
			LinkedList<SCmlOperationDefinition> operations, LinkedList<ILexNameToken> svars) {
		ThmNodeList tnl = new ThmNodeList();
		
		for(SCmlOperationDefinition op : operations)
			tnl.add(ThmProcessUtil.getIsabelleOperation(op, svars));
		
		return tnl;
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
	private static ThmNodeList getIsabelleActions(
			LinkedList<AActionDefinition> actions,
			LinkedList<ILexNameToken> svars,
			LinkedList<ILexNameToken> bvars) {
		ThmNodeList tnl = new ThmNodeList();
			
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
	private static ThmNode getIsabelleAction(
			AActionDefinition act, 
			LinkedList<ILexNameToken> svars, 
			LinkedList<ILexNameToken> bvars)
	{
		ThmNode tn = null;
		//get the action name
		ILexNameToken actName = act.getName();
		//get the Isabelle string for the action node's action.
		String actString = ThmProcessUtil.getIsabelleActionString(act.getAction(), svars, bvars);
		//obtain the action dependancies
		LinkedList<ILexNameToken> nodeDeps = ThmProcessUtil.getIsabelleActionDeps(act.getAction(), bvars);
		//create the theorem node.
		tn = new ThmNode(actName, nodeDeps, new ThmAction(actName.toString(), actString));

		return tn;
	}

	/***
	 * Method to get Isabelle string for a CML action 
	 * @param act - the CML action to process
	 * @param svars - the state variables of the CML process
	 * @param bvars - bound variables (basically the communication parameters)
	 * @return the Isabelle string
	 */
	private static String getIsabelleActionString(PAction act, 
			LinkedList<ILexNameToken> svars, 
			LinkedList<ILexNameToken> bvars)
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
			return ThmProcessUtil.mu + idStr + "." + actStr;
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

	/****
	 * Method to get the String for a CML statement 
	 * 
	 * NOTE: There are various unsupported statements, for these we return an Isabelle comment
	 * @param stmt - the statement to process
	 * @param svars - the state variables to bind to
	 * @param bvars - the bound variables (mainly operation parameters, block variables)
	 * @return the Isabelle string
	 */
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
		else if(stmt instanceof AIfStatementAction)
		{
			AIfStatementAction a = (AIfStatementAction) stmt;
			String ifStr = ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getIfExp());
			String thenStr = ThmProcessUtil.getIsabelleActionString(a.getThenStm(), svars, bvars);	
			String elseStr = "Skip";
			if(a.getElseStm() != null)
			{
				elseStr = ThmProcessUtil.getIsabelleActionString(a.getElseStm(), svars, bvars);	
			}
			String elseIfStr = "";
			String closingIf = "";
			LinkedList<AElseIfStatementAction> elseIf = a.getElseIf();
			for(AElseIfStatementAction e : elseIf)
			{
				elseIfStr = elseIfStr + "("+ ThmProcessUtil.getIsabelleActionString(e, svars, bvars);	
				closingIf = ")";
			}
			
			return thenStr + ThmProcessUtil.ifLeft + ifStr + ThmProcessUtil.ifRight + elseIfStr + elseStr + closingIf;
		}
		else if(stmt instanceof AElseIfStatementAction)
		{
			AElseIfStatementAction a = (AElseIfStatementAction) stmt;
			String elseIfStr = ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getElseIf());
			String thenStr = ThmProcessUtil.getIsabelleActionString(a.getThenStm(), svars, bvars);		

			return thenStr + ThmProcessUtil.ifLeft + elseIfStr+ ThmProcessUtil.ifRight;
		}
		else if(stmt instanceof ASingleGeneralAssignmentStatementAction)
		{
			ASingleGeneralAssignmentStatementAction a = (ASingleGeneralAssignmentStatementAction) stmt;
			String assExp = ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getStateDesignator());
			String exp = ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getExpression());
			
			return assExp + ThmProcessUtil.assign + exp;
		}
		else if(stmt instanceof AAssignmentCallStatementAction)
		{
			 AAssignmentCallStatementAction a = (AAssignmentCallStatementAction) stmt;
//           [designator]:exp
//           [call]:action.#Statement.call
		}
		else if(stmt instanceof AWhileStatementAction)
		{
			AWhileStatementAction a = (AWhileStatementAction) stmt;
			String cond = ThmExprUtil.getIsabelleExprStr(svars, bvars, a.getCondition());
			String actStr = ThmProcessUtil.getIsabelleActionString(a.getAction(), svars, bvars);
//	        | {declare}  [assignmentDefs]:definition*
			
			return ThmProcessUtil.isaWhile  + ThmProcessUtil.opExpLeft + cond + ThmProcessUtil.isaDo  + ThmProcessUtil.opExpRight + actStr + ThmProcessUtil.isaOd;
		}
		else  if(stmt instanceof ANonDeterministicDoStatementAction)
		{
//			ANonDeterministicDoStatementAction a = (ANonDeterministicDoStatementAction) stmt;
//	        [alternatives]:action.#Statement.nonDeterministicAlt*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ANonDeterministicIfStatementAction)
		{
//			ANonDeterministicIfStatementAction a = (ANonDeterministicIfStatementAction) stmt;
//           [alternatives]:action.#Statement.nonDeterministicAlt*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ANotYetSpecifiedStatementAction)
		{
//			 ANotYetSpecifiedStatementAction a = (ANotYetSpecifiedStatementAction) stmt;
//           [opname]:LexNameToken
//           [args]:exp*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ALetStatementAction)
		{
//			ALetStatementAction a = (ALetStatementAction) stmt;
			//[action]:action [localDefinitions]:definition*
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof ANonDeterministicAltStatementAction)
		{
//			ANonDeterministicAltStatementAction a = (ANonDeterministicAltStatementAction) stmt;
//           [guard]:exp
//           [action]:action
			//TODO: NOT YET HANDLED
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
		else if(stmt instanceof ASpecificationStatementAction)
		{
//			ASpecificationStatementAction a = (ASpecificationStatementAction) stmt;
//           [externals]:clause.external*
//           [precondition]:exp
//           [postcondition]:exp
			//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
			//TODO: NOT YET HANDLED
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
		return "(*statement not handled*)";
		
	}
	
	
	
	
	
	
	
	/***
	 * Method to get the dependencies for a CML action 
	 * @param act - the CML action to process
	 * @param bvars - bound variables (basically the communication parameters)
	 * @return the list of dependencies
	 */
	private static LinkedList<ILexNameToken> getIsabelleActionDeps(PAction act, 
			LinkedList<ILexNameToken> bvars)
	{
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		if(act instanceof ASkipAction)
		{
		}
		else if(act instanceof AStopAction)
		{
		}
		else if(act instanceof AChaosAction)
		{
		}
		else if(act instanceof ADivAction)
		{
		}
		else if(act instanceof AWaitAction)
		{
			AWaitAction a = (AWaitAction) act;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getExpression()));
		}
		else if(act instanceof ACommunicationAction)
		{
			ACommunicationAction a = (ACommunicationAction) act;
						
			for( PCommunicationParameter p: a.getCommunicationParameters())
			{
				if(p instanceof AReadCommunicationParameter)
				{
					AReadCommunicationParameter cp = (AReadCommunicationParameter) p;
					PPattern patt = cp.getPattern();
					if(patt instanceof AIdentifierPattern)
					{
						AIdentifierPattern ip = (AIdentifierPattern) patt;
						for (PDefinition def : ip.getDefinitions())
						{
							nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(def.getType()));
						}
						bvars.add(ip.getName());
					}
				}
				else if (p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, cp.getExpression()));
					
				}else
				{
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, p.getExpression()));
				}
			}
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
		}
		else if(act instanceof AGuardedAction)
		{
			AGuardedAction a = (AGuardedAction) act;
			
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getExpression()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
		}
		else if(act instanceof ASequentialCompositionAction)
		{
			ASequentialCompositionAction a = (ASequentialCompositionAction) act;
			
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof AExternalChoiceAction)
		{
			AExternalChoiceAction a = (AExternalChoiceAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof AInternalChoiceAction)
		{
			AInternalChoiceAction a = (AInternalChoiceAction) act;
			
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof AInterruptAction)
		{
			AInterruptAction a = (AInterruptAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof ATimedInterruptAction)
		{
			ATimedInterruptAction a = (ATimedInterruptAction) act;
			
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getTimeExpression()));
 			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof AUntimedTimeoutAction)
		{
			AUntimedTimeoutAction a = (AUntimedTimeoutAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof ATimeoutAction)
		{
			ATimeoutAction a = (ATimeoutAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getTimeoutExpression()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRight(), bvars));
		}
		else if(act instanceof AHidingAction)
		{
			AHidingAction a = (AHidingAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(a.getChansetExpression()));

		}
		else if(act instanceof AStartDeadlineAction)
		{
			AStartDeadlineAction a = (AStartDeadlineAction) act;

			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars)); 
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getExpression()));
		}
		else if(act instanceof AEndDeadlineAction)
		{
			AEndDeadlineAction a = (AEndDeadlineAction) act;	
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeft(), bvars)); 
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getExpression()));	
		}
		else if(act instanceof AChannelRenamingAction)
		{
			AChannelRenamingAction a = (AChannelRenamingAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleRenamingExprDeps(a.getRenameExpression()));
		}
		else if(act instanceof AMuAction)
		{
			AMuAction a = (AMuAction) act;
			
			for (ILexIdentifierToken id : a.getIdentifiers()) 
			{
				
				nodeDeps.add(new LexNameToken("", id.getName().toString(), a.getLocation()));
			}
			
			for (PAction pa : a.getActions())
			{
				nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(pa, bvars));
			}
		}
		if(act instanceof AReferenceAction)
		{
			AReferenceAction a = (AReferenceAction) act;

			LinkedList<PExp> args = a.getArgs();
			if (args.size() != 0)
			{
				for (PExp e : a.getArgs()) 
				{
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars,e));
				}
			}
			nodeDeps.add(a.getName());
		}
		else if (act instanceof SParallelAction)
		{
			nodeDeps.addAll(ThmProcessUtil.getIsabelleParallelActionDeps(act, bvars));
		}
		else if (act instanceof SReplicatedAction)
		{
			nodeDeps.addAll(ThmProcessUtil.getIsabelleReplicatedActionDeps(act, bvars));
		}
		else if(act instanceof SStatementAction)
		{
			nodeDeps.addAll(ThmProcessUtil.getIsabelleStatementDeps(act, bvars));
		}
		return nodeDeps;
	}

	
	
	private static LinkedList<ILexNameToken> getIsabelleReplicatedActionDeps(PAction act, LinkedList<ILexNameToken> bvars) {

		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
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
		return nodeDeps;
	}

	private static LinkedList<ILexNameToken> getIsabelleParallelActionDeps(
			PAction act, LinkedList<ILexNameToken> bvars) {

		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		if(act instanceof AInterleavingParallelAction)
		{
			AInterleavingParallelAction a = (AInterleavingParallelAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeftAction(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRightAction(), bvars));
		}
		else if(act instanceof ASynchronousParallelismParallelAction)
		{
			ASynchronousParallelismParallelAction a = (ASynchronousParallelismParallelAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeftAction(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRightAction(), bvars));
		}
		else if(act instanceof AGeneralisedParallelismParallelAction)
		{
			AGeneralisedParallelismParallelAction a = (AGeneralisedParallelismParallelAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeftAction(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRightAction(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(a.getChansetExpression()));
		}
		else if(act instanceof AAlphabetisedParallelismParallelAction)
		{
			AAlphabetisedParallelismParallelAction a = (AAlphabetisedParallelismParallelAction) act;
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getLeftAction(), bvars));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getRightAction(), bvars));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(a.getLeftChansetExpression()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(a.getRightChansetExpression()));
		}
		return nodeDeps;
	}

	/****
	 * Method to get the String for a CML statement 
	 * 
	 * NOTE: There are various unsupported statements, for these we return an Isabelle comment
	 * @param stmt - the statement to process
	 * @param svars - the state variables to bind to
	 * @param bvars - the bound variables (mainly operation parameters, block variables)
	 * @return the Isabelle string
	 */
	private static LinkedList<ILexNameToken> getIsabelleStatementDeps(PAction stmt,
			LinkedList<ILexNameToken> bvars) {

		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>(); 
		
		if(stmt instanceof ACallStatementAction)
		{
			ACallStatementAction a = (ACallStatementAction) stmt;

			for (PExp e: a.getArgs())
			{
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars,e));
			}
			nodeDeps.add(a.getName());
		}
		else if (stmt instanceof ABlockStatementAction)
		{
			ABlockStatementAction a = (ABlockStatementAction) stmt;
			

			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
			if(a.getDeclareStatement() != null) 
			{				
				for (PDefinition pdef : a.getDeclareStatement().getAssignmentDefs())
				{
					AAssignmentDefinition aDef = (AAssignmentDefinition) pdef;
					bvars.add(aDef.getName());
					nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, aDef.getExpression()));
				}
			}

			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
		}
		else if(stmt instanceof AIfStatementAction)
		{
			AIfStatementAction a = (AIfStatementAction) stmt;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getIfExp()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getThenStm(), bvars));	

			if(a.getElseStm() != null)
			{
				nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getElseStm(), bvars));	
			}
			LinkedList<AElseIfStatementAction> elseIf = a.getElseIf();
			for(AElseIfStatementAction e : elseIf)
			{
				nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(e, bvars));	

			}
		}
		else if(stmt instanceof AElseIfStatementAction)
		{
			AElseIfStatementAction a = (AElseIfStatementAction) stmt;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getElseIf()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getThenStm(), bvars));		

		}
		else if(stmt instanceof ASingleGeneralAssignmentStatementAction)
		{
			ASingleGeneralAssignmentStatementAction a = (ASingleGeneralAssignmentStatementAction) stmt;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getStateDesignator()));
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getExpression()));
		}
		else if(stmt instanceof AAssignmentCallStatementAction)
		{
			 AAssignmentCallStatementAction a = (AAssignmentCallStatementAction) stmt;
//           [designator]:exp
//           [call]:action.#Statement.call
		}
		else if(stmt instanceof AWhileStatementAction)
		{
			AWhileStatementAction a = (AWhileStatementAction) stmt;
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a.getCondition()));
			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(a.getAction(), bvars));
//	        | {declare}  [assignmentDefs]:definition*
		}
		else  if(stmt instanceof ANonDeterministicDoStatementAction)
		{
//			ANonDeterministicDoStatementAction a = (ANonDeterministicDoStatementAction) stmt;
//	        [alternatives]:action.#Statement.nonDeterministicAlt*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ANonDeterministicIfStatementAction)
		{
//			ANonDeterministicIfStatementAction a = (ANonDeterministicIfStatementAction) stmt;
//           [alternatives]:action.#Statement.nonDeterministicAlt*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ANotYetSpecifiedStatementAction)
		{
//			 ANotYetSpecifiedStatementAction a = (ANotYetSpecifiedStatementAction) stmt;
//           [opname]:LexNameToken
//           [args]:exp*
			//TODO: NOT HANDLED
		}
		else if(stmt instanceof ALetStatementAction)
		{
//			ALetStatementAction a = (ALetStatementAction) stmt;
			//[action]:action [localDefinitions]:definition*
			//TODO: NOT YET HANDLED
		}
		else if(stmt instanceof ANonDeterministicAltStatementAction)
		{
//			ANonDeterministicAltStatementAction a = (ANonDeterministicAltStatementAction) stmt;
//           [guard]:exp
//           [action]:action
			//TODO: NOT YET HANDLED
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
		else if(stmt instanceof ASpecificationStatementAction)
		{
//			ASpecificationStatementAction a = (ASpecificationStatementAction) stmt;
//           [externals]:clause.external*
//           [precondition]:exp
//           [postcondition]:exp
			//MAY GEN LEMMA TO PROVE STATING FRAME NOT VIOLATED
			//TODO: NOT YET HANDLED
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
		return nodeDeps;
		
	}
	
	
	
	
	
	
	
	
	
	private static String getIsabelleStateInv(AStateDefinition node)
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
	
	
	private static LinkedList<ILexNameToken> getIsabelleStateInvDeps(AStateDefinition node)
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
