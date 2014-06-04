package eu.compassresearch.core.analysis.theoremprover.utils;

import java.util.LinkedList;

import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;


public class ThmProcessUtil {
	
	public static String isaProc = "definition";
	public static String isaAct = "definition";
	public static String procDelimLeft = "`";
	public static String procDelimRight = "`";
	public static String isaActProc = "locale";
	public static String isaProcBegin = "begin";
	public static String isaProcEnd = "end";
	public static String isaOp = "definition";
	public static String opParamLeft = "\\<parallel>";
	public static String opParamRight = "\\<parallel>";
	public static String opExpLeft = "\\<lparr>";
	public static String opExpRight = "\\<rparr>";
	public static String opBodyLeft = "{: ";
	public static String opBodyRight = " :}";
	public static String opTurn = "\\<turnstile>";
	public static String isaMainAction = "MainAction";
	public static String isaMu = "\\<mu>";
	

	public static String isaReturn = "return ";

	public static String skip = "SKIP";
	public static String stop = "STOP";
	public static String chaos = "CHAOS";
	public static String div = "DIV";
	public static String wait = "WAIT ";
	public static String comm = " -> ";
	public static String guardLeftBrack = "[\\<lparr>";
	public static String guardRightBrack = "\\<rparr>]";
	public static String guardSep = " & ";
	public static String seqComp = " ; ";
	public static String extChoice = " \\<box> ";
	public static String intChoice = " |-| ";
	public static String interrupt = " /-\\ ";
	public static String timeIntLeft = "/(";
	public static String timeIntRight = ")\\";
	public static String timeout = "[>";
	public static String timeoutLeft = " [(";
	public static String timeoutRight = ")> ";
	public static String hiding = " \\\\ ";
	public static String startsby = " startby ";
	public static String endsby = " endby ";
	public static String interleave = " ||| ";
	public static String syncParallel = " || ";
	public static String mainAction = "MainAction";
	
	public static String replExtChoice = "[] ";
	public static String replIntChoice = "|~| ";
	public static String replPar = "|| ";
	public static String replInter = "||| ";
	public static String replSeq = "; ";
	
	public static String ifLeft = "\\<triangleleft>";
	public static String ifRight = "\\<triangleright>";
	public static String assign = " := ";
	public static String isaWhile = "while ";
	public static String isaDo = " do ";
	public static String isaOd = " od ";
	public static String repActNotHandled = "(*replicated action not handled*)";
	public static String stmtNotHandled = "(*statement not handled*)";
	public static String procNotHandled = "(*process not handled*)";
	public static String undefined = chaos;

	
	public static NodeNameList getProcessNames(AActionProcess act){

		NodeNameList procNodeNames = new NodeNameList();
		//Collect all the statement/operation/action paragraphs and deal with them 
		//all together.
		LinkedList<AInstanceVariableDefinition> procVars = new LinkedList<AInstanceVariableDefinition>();
		LinkedList<SOperationDefinition> operations = new LinkedList<SOperationDefinition>();
		LinkedList<AImplicitFunctionDefinition> impfunctions = new LinkedList<AImplicitFunctionDefinition>();
		LinkedList<AExplicitFunctionDefinition> expfunctions = new LinkedList<AExplicitFunctionDefinition>();
		LinkedList<AActionDefinition> actions = new LinkedList<AActionDefinition>();
		LinkedList<PDefinition> others = new LinkedList<PDefinition>();
		AActionClassDefinition actdef = (AActionClassDefinition) act.getActionDefinition();
			
		for (PDefinition pdef : actdef.getDefinitions())
		{
			if (pdef instanceof AInstanceVariableDefinition)
			{
				AInstanceVariableDefinition sdef = (AInstanceVariableDefinition) pdef;
				procVars.add(sdef);
			}
			else if (pdef instanceof AExplicitOperationDefinition)
			{
				AExplicitOperationDefinition op = (AExplicitOperationDefinition) pdef;
				operations.add(op);
			}
			else if (pdef instanceof AImplicitOperationDefinition)
			{
				AImplicitOperationDefinition op = (AImplicitOperationDefinition) pdef;
				operations.add(op);
			}
			else if (pdef instanceof AImplicitFunctionDefinition)
			{
				AImplicitFunctionDefinition exp = (AImplicitFunctionDefinition) pdef;
				impfunctions.add(exp);
			}
			else if (pdef instanceof AExplicitFunctionDefinition)
			{
				AExplicitFunctionDefinition exp = (AExplicitFunctionDefinition) pdef;
				expfunctions.add(exp);
			}
			else if (pdef instanceof AActionDefinition)
			{
				AActionDefinition actdefn = (AActionDefinition) pdef;
				actions.add(actdefn);
			}
			else
			{
				others.add(pdef);
			}
		}
		//Add all names to list
		procNodeNames.addAll(ThmProcessUtil.getStateNames(procVars));
		procNodeNames.addAll(ThmProcessUtil.getOperationNames(operations));
		procNodeNames.addAll(ThmProcessUtil.getExpFunctionNames(expfunctions));
		procNodeNames.addAll(ThmProcessUtil.getImpFunctionNames(impfunctions));
		procNodeNames.addAll(ThmProcessUtil.getActionNames(actions));
		return procNodeNames;
	}
	
	public static NodeNameList getProcessStatementNames(AActionProcess act)
	{
		LinkedList<AInstanceVariableDefinition> statements = new LinkedList<AInstanceVariableDefinition>();
		AActionClassDefinition actdef = (AActionClassDefinition) act.getActionDefinition();
		
		for (PDefinition pdef : actdef.getDefinitions())
		{
			if (pdef instanceof AInstanceVariableDefinition)
			{
				AInstanceVariableDefinition sdef = (AInstanceVariableDefinition) pdef;
				statements.add(sdef);
			}
		}
		return ThmProcessUtil.getStateNames(statements);
	}
	
	

	
	/***
	 * Method to retrieve all function names from a collection of explicit functions
	 * @param statements - the collection of functions
	 * @return a list of names (as ILexNameTokens)
	 */
	public static NodeNameList getExpFunctionNames(LinkedList<AExplicitFunctionDefinition> expfunctions) {
		NodeNameList fNames = new NodeNameList();
		
		//for each function
		for(AExplicitFunctionDefinition f : expfunctions){
			//get the name and add it to the list
			fNames.add(f.getName());
		}
		return fNames;
	}

	
	/***
	 * Method to retrieve all function names from a collection of implicit functions
	 * @param statements - the collection of functions
	 * @return a list of names (as ILexNameTokens)
	 */
	public static NodeNameList getImpFunctionNames(LinkedList<AImplicitFunctionDefinition> functions) {
		NodeNameList fNames = new NodeNameList();
		
		//for each function
		for(AImplicitFunctionDefinition f : functions){
			//get the name and add it to the list
			fNames.add(f.getName());
		}
		return fNames;
	}
	
	/***
	 * Method to retrieve all state variable names from a collection of state definitions
	 * @param statements - the collection of state variables
	 * @return a list of names (as ILexNameTokens)
	 */
	public static NodeNameList getStateNames(LinkedList<AInstanceVariableDefinition> procVars)
	{
		//first we need to get all the state identifier names so expressions use correct
		//reference
		NodeNameList statenames = new NodeNameList();
		//for each state definition
		for (AInstanceVariableDefinition pdef : procVars)
		{
			//get the name and add it to the list
			statenames.add(pdef.getName());
			LexNameToken tildaStateName = new LexNameToken("", pdef.getName().toString() + "~", pdef.getLocation());
			statenames.add(tildaStateName);
		}
		return statenames;
	}

	/**
	 * Method to retrieve all operation and operation precondition names from a collection of operations definitions
	 * @param operations - the collection of operations
	 * @return a list of names (as ILexNameTokens)
	 */
	public static NodeNameList getOperationNames(
			LinkedList<SOperationDefinition> operations) {
		
		NodeNameList opNames = new NodeNameList();
		//for each operation
		for(SOperationDefinition op : operations){
			//get the name and add it to the list
			opNames.add(op.getName());
			//Construct a name for the operation precondition
			LexNameToken preOpName = new LexNameToken("", "pre_" + op.getName().toString(), op.getLocation());
			opNames.add(preOpName);
		}
		return opNames;
	}

	/**
	 * Method to retrieve all state variable names from a collection of actions definitions
	 * @param actions - the collection of actions
	 * @return a list of names (as ILexNameTokens)
	 */
	public static NodeNameList getActionNames(
			LinkedList<AActionDefinition> actions) {

		NodeNameList actNames = new NodeNameList();
		//for each operation
		for(AActionDefinition a : actions)
			//get the name and add it to the list
			actNames.add(a.getName());
		return actNames;
	}
}