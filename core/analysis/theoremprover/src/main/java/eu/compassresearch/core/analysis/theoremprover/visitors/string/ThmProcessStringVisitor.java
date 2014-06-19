package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.SReplicatedActionBase;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AChannelRenamingProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AExternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInterleavingReplicatedProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASequentialCompositionReplicatedProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.process.SReplicatedProcessBase;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmProcAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmStateInv;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
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
		List<AClassInvariantDefinition> invs = ThmProcessUtil.getProcessInvariants(act);
		//Placeholder for main action - only changed if there are state variables
		String mainActStateStr = " = `";
		AActionClassDefinition actdef = (AActionClassDefinition) act.getActionDefinition();
		
		
		for (PDefinition pdef : actdef.getDefinitions())
		{
			ThmNodeList defNodes = new ThmNodeList();
			
			defNodes.addAll(pdef.apply(new TPVisitor(), new ThmVarsContext(svars, vars.getBVars())));//(ThmProcessUtil.getAExplicitFunctionDefinition(f));
			
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
				
		// Filter out action nodes as we will handle them in one go at the end
		
		ThmNodeList actions = new ThmNodeList();
		
		for (Iterator<ThmNode> itr = actTnl.iterator(); itr.hasNext(); ) {
			ThmNode tn = itr.next();
			if (tn.getArtifact() instanceof ThmAction) {
				itr.remove();
				actions.add(tn);
			}
		}
		
		//sort the state and operations, so that they are in dependency order
		String stateString = "";
		try
		{
			actTnl = TPVisitor.sortThmNodes(actTnl);
		}
		catch(ThySortException thye)
		{
			stateString = "(*Thy gen error:*)\n" + "(*Isabelle Error when sorting nodes - "
				    	+ "please submit bug report with CML file*)\n\n" + thye.getSortErrorStatus() + "\n\n"; 
		}
		
		stateString = actTnl.toString();
		
		StringBuffer actsb = new StringBuffer();
		
		if (!actions.isEmpty()) {
			actsb.append("cmlacts\n");
		}
		
		for (Iterator<ThmNode> itr = actions.iterator(); itr.hasNext(); ) {
			actsb.append(itr.next().toString());
			if (itr.hasNext()) actsb.append (" and");
			actsb.append("\n");
		}
		
		// Create a definition which accumulates all state invariants
		StringBuffer mainInv = new StringBuffer();
		boolean hasInvs = false;
		mainInv.append("definition \"state_inv = |");
		
		for (ThmNode tn : actTnl) {
			if (tn.getArtifact() instanceof ThmStateInv) {
				if (hasInvs) mainInv.append(" and ");
				hasInvs = true;
				mainInv.append("(@" + ((ThmStateInv) tn.getArtifact()).getName() + ")");
			}
		}
		
		if (!hasInvs) mainInv.append("true");
		mainInv.append("|\"\n");
		
		//Obtain the main action string
		String mainStr = ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + mainActStateStr + act.getAction().apply(thmStringVisitor, new ThmVarsContext(svars, new NodeNameList())) +  "`\"";
		
		//Finally construct the process String
		return (stateString + "\n" + actsb.toString() + mainInv.toString() + "\n" + mainStr);
	}
	
	
	

	
	public String caseAAlphabetisedParallelismProcess(AAlphabetisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{
		
		return ThmProcessUtil.undefined;
	}
	
	private String printReplicatedProcessBase(
			SReplicatedProcessBase p, ThmVarsContext vars) throws AnalysisException {

		StringBuffer sb = new StringBuffer();
		
		for (PSingleDeclaration d : p.getReplicationDeclaration()) {
			if (d instanceof AExpressionSingleDeclaration) {
				AExpressionSingleDeclaration d1 = (AExpressionSingleDeclaration) d;
				sb.append(d1.getIdentifier().toString());
				sb.append(ThmExprUtil.inSet);
				sb.append(d1.getExpression().apply(thmStringVisitor, vars));
				// FIXME: The LexNameToken needs a module, currently empty
				vars.addBVar(new LexNameToken("", d1.getIdentifier().clone()));
			}	
		}
		
		sb.append(" @ ");
		sb.append(p.getReplicatedProcess().apply(thmStringVisitor, vars));
				
		return sb.toString();
		
	}
	
	public String caseAAlphabetisedParallelismReplicatedProcess(AAlphabetisedParallelismReplicatedProcess p, ThmVarsContext vars) throws AnalysisException{

		StringBuffer sb = new StringBuffer();
		
		sb.append(ThmProcessUtil.replPar);
		
		for (PSingleDeclaration d : p.getReplicationDeclaration()) {
			if (d instanceof AExpressionSingleDeclaration) {
				AExpressionSingleDeclaration d1 = (AExpressionSingleDeclaration) d;
				sb.append(d1.getIdentifier().toString());
				sb.append(ThmExprUtil.inSet);
				sb.append(d1.getExpression().apply(thmStringVisitor, vars));
				// FIXME: The LexNameToken needs a module, currently empty
				vars.addBVar(new LexNameToken("", d1.getIdentifier().clone()));
			}	
		}

		sb.append(" @ [");
		sb.append(p.getChansetExpression().apply(thmStringVisitor, vars));
		sb.append("] ");
		
		sb.append(p.getReplicatedProcess().apply(thmStringVisitor, vars));
		
		return sb.toString();
		
	}
	
	public String caseAChannelRenamingProcess(AChannelRenamingProcess a, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
	}
	
	public String caseAEndDeadlineProcess(AEndDeadlineProcess p, ThmVarsContext vars) throws AnalysisException{

		return ThmProcessUtil.undefined;
	}
		
	public String caseAExternalChoiceProcess(AExternalChoiceProcess a, ThmVarsContext vars) throws AnalysisException{
		return a.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.extChoice + a.getRight().apply(thmStringVisitor, vars);
	}
	
	public String caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess p, ThmVarsContext vars) throws AnalysisException {
		return (ThmProcessUtil.replExtChoice + printReplicatedProcessBase(p, vars));
	}
	
	public String caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess p, ThmVarsContext vars) throws AnalysisException{
				
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);
		String chExp = p.getChansetExpression().apply(thmStringVisitor, vars);
		
		return left + "[|" + chExp +"|]" + right;
	}

	public String caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess p, ThmVarsContext vars) throws AnalysisException{
		return (ThmProcessUtil.replPar + printReplicatedProcessBase(p, vars));
	}

	public String caseAHidingProcess(AHidingProcess p, ThmVarsContext vars) throws AnalysisException{

	    String exp   = p.getLeft().apply(thmStringVisitor, vars);
		String chExp = p.getChansetExpression().apply(thmStringVisitor, vars);
		
		return exp + ThmProcessUtil.hiding + chExp;
	}

	public String caseAInstantiationProcess(AInstantiationProcess node, ThmVarsContext vars) throws AnalysisException{

		return ThmProcessUtil.undefined;
	}

	public String caseAInterleavingProcess(AInterleavingProcess p, ThmVarsContext vars) throws AnalysisException{
		
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);

		return left + ThmProcessUtil.interleave + right;
		
	}

	public String caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess p, ThmVarsContext vars) throws AnalysisException{
		return (ThmProcessUtil.replInter + printReplicatedProcessBase(p, vars));
	}

	public String caseAInternalChoiceProcess(AInternalChoiceProcess p, ThmVarsContext vars) throws AnalysisException{
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);

		return left + ThmProcessUtil.intChoice + right;
	}

	public String caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess p, ThmVarsContext vars) throws AnalysisException{
		return (ThmProcessUtil.replIntChoice + printReplicatedProcessBase(p, vars));
	}

	public String caseAInterruptProcess(AInterruptProcess p, ThmVarsContext vars) throws AnalysisException{
		return p.getLeft().apply(thmStringVisitor, vars) + ThmProcessUtil.interrupt + p.getRight().apply(thmStringVisitor, vars);
	}

	public String caseASequentialCompositionProcess(
			ASequentialCompositionProcess p, ThmVarsContext vars) throws AnalysisException{
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);

		return left + ThmProcessUtil.seqComp + right;
	}

	public String caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess p, ThmVarsContext vars) throws AnalysisException{
		return (ThmProcessUtil.replSeq + printReplicatedProcessBase(p, vars));
	}

	public String caseAStartDeadlineProcess(AStartDeadlineProcess node, ThmVarsContext vars) throws AnalysisException{

		return ThmProcessUtil.undefined;
	}

	public String caseATimedInterruptProcess(ATimedInterruptProcess node, ThmVarsContext vars) throws AnalysisException{

		return ThmProcessUtil.undefined;
	}

	public String caseATimeoutProcess(ATimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);
		String time  = p.getTimeoutExpression().apply(thmStringVisitor, vars);
		
		return left + ThmProcessUtil.timeoutLeft + time + ThmProcessUtil.timeoutRight + right;
	}

	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess p, ThmVarsContext vars) throws AnalysisException{
		String left = p.getLeft().apply(thmStringVisitor, vars);
		String right = p.getRight().apply(thmStringVisitor, vars);

		return left + ThmProcessUtil.timeout + right;
	}
	
	public String caseAReferenceProcess(AReferenceProcess p, ThmVarsContext vars) throws AnalysisException{

		StringBuilder argStr = new StringBuilder();
		LinkedList<PExp> args = p.getArgs();
		argStr.append("(");
		if (args.size() != 0)
		{
			
			for (Iterator<PExp> itr = p.getArgs().listIterator(); itr.hasNext(); ) {
				PExp e = itr.next();
				
				argStr.append(e.apply(thmStringVisitor, vars));
				//If there are remaining expressions, add a ","
				if(itr.hasNext()){	
					argStr.append(", ");
				}
			}
		}
		argStr.append(")");
		return "(@" + p.getProcessName().toString() + argStr.toString() + ")";
	}
	
	public String casePProcess(PProcess p, ThmVarsContext vars) throws AnalysisException{
		return ThmProcessUtil.undefined;
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
