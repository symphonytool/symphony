package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThySortException;
import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;

public class ThmProcessStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmProcessStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}
	
	/**
	 * Return the ThmNode for a Action Process - this is more complex than most other
	 * Node utils, due to the internal scoping etc required in a process
	 * @param procName the process name
	 * @param act the action process of the owning process
	 * @return the ThmNode object for this process
	 * @throws AnalysisException 
	 */
	public String caseAActionProcess(AActionProcess act, ThmVarsContext vars) throws AnalysisException{
			
		//need to define a new collection of node lists for the definitions inside a 
		//process. This is because we need to limit scope and dependencies within a 
		//action process, and output it as a single (contained) node.
		ThmNodeList actTnl = new ThmNodeList();
		//Require a list of all names used within a process, so to ensure the dependency 
		//relationships within and outside the process can be dealt with.
		NodeNameList procNodeNames = ThmProcessUtil.getProcessNames(act);
		NodeNameList svars = ThmProcessUtil.getProcessStatementNames(act);
		//Placeholder for main action - only changed if there are state variables
		String mainActStateStr = " = `";
		AActionClassDefinition actdef = (AActionClassDefinition) act.getActionDefinition();	
		
		for (PDefinition pdef : actdef.getDefinitions())
		{
			ThmNodeList defNodes = new ThmNodeList();
			
			defNodes.addAll(pdef.apply(new TPVisitor(), new ThmVarsContext(svars, new NodeNameList())));//(ThmProcessUtil.getAExplicitFunctionDefinition(f));
			
			//restrict the function dependencies to only those names used within the process
			defNodes = defNodes.restrictExtOperationsDeps(procNodeNames);
			actTnl.addAll(defNodes);
		}
		
		//if there are state variables
		if (!svars.isEmpty())
		{
			//next generate nodes for the state variables, and add their initialised 
			//assignments to a collection for initialisation in main action
			//Also generate the invariant functions...
			LinkedList<String> initExprs = new LinkedList<String>();
			NodeNameList initExprNodeDeps = new NodeNameList();
			for (PDefinition pdef : actdef.getDefinitions())
			{
				if (pdef instanceof AInstanceVariableDefinition)
				{
					AInstanceVariableDefinition sdef = (AInstanceVariableDefinition) pdef;
					//Get the state variable name
					ILexNameToken sName = pdef.getName();
					//if the variable is initialised straight away, add it to the initExprs string
					//and get the dependencies
					initExprs.add(sName.getName() + ThmProcessUtil.assign + sdef.getExpression().apply(thmStringVisitor,new ThmVarsContext(svars, new NodeNameList()))); //ThmExprUtil.getIsabelleExprStr(svars, new NodeNameList(), st.getExpression()));
					initExprNodeDeps.addAll(sdef.getExpression().apply(new ThmDepVisitor(), new NodeNameList()));//(ThmExprUtil.getIsabelleExprDeps(new NodeNameList(),  st.getExpression()));
	
					//As we only care about the internal dependencies in initExprNodeDeps, remove
					//any dependencies to CML elements external to the process
					initExprNodeDeps = initExprNodeDeps.restrictDeps(procNodeNames);	
				}
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
			ThmNode stn = new ThmNode(initName, initExprNodeDeps, new ThmExplicitOperation(initName.getName(), new LinkedList<PPattern>(), null, null, initExpStr.toString(), null, null));
			actTnl.add(stn);		
			
			mainActStateStr = " = ` call IsabelleStateInit[]; ";
		}
		
		//sort the state, operation and actions, so that they are in dependency order
		String actString = "";
		try
		{
			actTnl = TPVisitor.sortThmNodes(actTnl);
			actString = actTnl.toString();
		}
		catch(ThySortException thye)
		{
			actString = "(*Thy gen error:*)\n" + "(*Isabelle Error when sorting nodes - "
					+ "please submit bug report with CML file*)\n\n" + thye.getSortErrorStatus() + "\n\n"; 
		}
				
		//Obtain the main action string
		String mainStr = ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + mainActStateStr + act.getAction().apply(thmStringVisitor, new ThmVarsContext(svars, new NodeNameList())) +  "`\"";
		
		//Finally construct the process String
		return (actString + "\n" + mainStr);
	}
	
	
	
	
	public String caseASequentialCompositionProcess(ASequentialCompositionProcess p, ThmVarsContext vars) throws AnalysisException{

		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.seqComp + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAExternalChoiceProcess(AExternalChoiceProcess p, ThmVarsContext vars) throws AnalysisException{
		
		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.extChoice + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAInternalChoiceProcess(AInternalChoiceProcess p, ThmVarsContext vars) throws AnalysisException{
		
		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.intChoice + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAInterleavingProcess(AInterleavingProcess p, ThmVarsContext vars) throws AnalysisException{

		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interleave  + p.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAGeneralisedParallelismProcess(AGeneralisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{

		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);
		String chExp = p.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}
	
//	public String caseAAlphabetisedParallelismProcess(AAlphabetisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//		String leftChExp = p.getLeftChansetExpression().apply(thmStringVisitor, vars);
//		String rightChExp = p.getRightChansetExpression().apply(thmStringVisitor, vars);
//		
//		return left + "[" + leftChExp + "||" + rightChExp +"]" + right;
//	}
	
//	public String caseAInterruptProcess(AInterruptProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interrupt + p.getRight().apply(thmStringVisitor, vars);
//	}
//	
//	public String caseATimedInterruptProcess(ATimedInterruptProcess p, ThmVarsContext vars) throws AnalysisException{
//		
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getTimeExpression().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeIntLeft + expr + ThmProcessUtil.timeIntRight + right;
//	}
//	
//	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
//		
//		String left =  p.getLeft().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeout + right;
//	}
//	
//	public String caseATimeoutProcess(ATimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getTimeoutExpression().apply(thmStringVisitor, vars);
//		String right = p.getRight().apply(thmStringVisitor, vars);
//
//		return left + ThmProcessUtil.timeoutLeft +  expr + ThmProcessUtil.timeoutRight + right;
//	}
	
	public String caseAHidingProcess(AHidingProcess p, ThmVarsContext vars) throws AnalysisException{

		String actStr = p.getLeft().apply(thmStringVisitor, vars);
		String chanStr = p.getChansetExpression().apply(thmStringVisitor, vars);
		return actStr + ThmProcessUtil.hiding + chanStr;
	}
	
//	public String caseAStartDeadlineProcess(AStartDeadlineProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getExpression().apply(thmStringVisitor, vars);		
//		return left + ThmProcessUtil.startsby + expr;
//	}
//	
//	public String caseAEndDeadlineProcess(AEndDeadlineProcess p, ThmVarsContext vars) throws AnalysisException{
//
//		String left = p.getLeft().apply(thmStringVisitor, vars);
//		String expr = p.getExpression().apply(thmStringVisitor, vars);	
//		return left + ThmProcessUtil.endsby + expr;	
//	}
	
	public String caseAReferenceProcess(AReferenceProcess p, ThmVarsContext vars) throws AnalysisException{

		StringBuilder argStr = new StringBuilder();
		LinkedList<PExp> args = p.getArgs();
		if (args.size() != 0)
		{
			argStr.append("(");
			for (Iterator<PExp> itr = p.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				argStr.append(e.apply(thmStringVisitor, vars));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					argStr.append(", ");
				}
			}
			argStr.append(")");
		}
		return p.getProcessName().toString() + argStr.toString();
	}
	
	public String casePProcess(PProcess p, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.procNotHandled;
	}
	
	@Override
	public String createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
