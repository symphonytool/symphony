package eu.compassresearch.core.analysis.theoremprover.visitors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExpFunc;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmImpFunc;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmImplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmProcAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmProcStand;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmState;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThySortException;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class ThmDeclAndDefVisitor extends QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList>
{
	final private QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> parentVisitor;
	private ThmDepVisitor depVisitor;
	private ThmStringVisitor stringVisitor;
	
	public ThmDeclAndDefVisitor(QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> parent, ThmDepVisitor depVis, ThmStringVisitor stringVis)
	{
		depVisitor = depVis;
		stringVisitor = stringVis;
		parentVisitor = parent;
	}
	
	/**
	 * Visitor method for collection of type definitions
	 */
	@Override
	public ThmNodeList caseATypesDefinition(ATypesDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getTypes())
		{
			tnl.addAll(def.apply(parentVisitor, vars));
		}

		return tnl;
	}

	/**
	 * Visitor method for collection of value definitions
	 */
	@Override
	public ThmNodeList caseAValuesDefinition(AValuesDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getValueDefinitions())
		{
			tnl.addAll(def.apply(parentVisitor, vars));
		}

		return tnl;
	}

	/**
	 * Visitor method for collection of function definitions
	 */
	@Override
	public ThmNodeList caseAFunctionsDefinition(AFunctionsDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getFunctionDefinitions())
		{
			tnl.addAll(def.apply(parentVisitor, vars));
		}

		return tnl;
	}

	/**
	 * Visitor method for an explicitly defined function
	 */
	@Override
	public ThmNodeList caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		NodeNameList nodeDeps = new NodeNameList();

		ILexNameToken name = node.getName();
		
		//Deal with the parameters
		LinkedList<List<PPattern>> params = node.getParamPatternList();
		//Find bound values to exclude from dependency list
		NodeNameList b = new NodeNameList();
		for(PPattern p : params.getFirst() )
		{
			b.add(((AIdentifierPattern) p).getName());
		}
		//add the parameter types as dependencies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters())
		{
			nodeDeps.addAll(pTp.apply(depVisitor, new NodeNameList()));//(ThmTypeUtil.getIsabelleTypeDeps(pTp));
		}
		
		//Deal with the function body
		NodeNameList s = new NodeNameList();
		ThmVarsContext vc = new ThmVarsContext(s, b);
		String exp = node.getBody().apply(stringVisitor, vc); //ThmExprUtil.getIsabelleExprStr(s, b, node.getBody());
		nodeDeps.addAll(node.getBody().apply(depVisitor, b));//(ThmExprUtil.getIsabelleExprDeps(b, node.getBody()));

		//Deal with the function precondition
		String pre = null;
		if (node.getPrecondition() != null){
			pre = node.getPrecondition().apply(stringVisitor, vc); //ThmExprUtil.getIsabelleExprStr(s, b, node.getPrecondition());
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, b));//(ThmExprUtil.getIsabelleExprDeps(b, node.getPrecondition()));
		}
		
		//Deal with the function postcondition
		String post = null;
		if (node.getPostcondition() != null){
			post = node.getPostcondition().apply(stringVisitor, vc); //ThmExprUtil.getIsabelleExprStr(s, b, node.getPostcondition());
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, b));//(ThmExprUtil.getIsabelleExprDeps(b, node.getPostcondition()));
		}
		
		//Deal with the function result
		String resType = node.getExpectedResult().apply(stringVisitor, vc); //ThmTypeUtil.getIsabelleType(node.getExpectedResult());
		nodeDeps.addAll(node.getExpectedResult().apply(depVisitor, b));//(ThmTypeUtil.getIsabelleTypeDeps(node.getExpectedResult()));
		
		ThmNode tn = new ThmNode(name, nodeDeps, new ThmExpFunc(name.getName(), exp, post, pre, params, resType));
		tnl.add(tn);
		
		return tnl;
	}


	/**
	 * Visitor method for an implicitly defined function
	 */
	public ThmNodeList caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		NodeNameList nodeDeps = new NodeNameList();

		ILexNameToken name = node.getName();
		LinkedList<APatternListTypePair> params = node.getParamPatterns();
		APatternTypePair res = node.getResult();

		NodeNameList b = new NodeNameList();
		//Find bound values to exclude from dependency list
		for(APatternListTypePair p : params )
		{
			LinkedList<PPattern> pats = p.getPatterns();
			for(PPattern param : pats )
			{
				b.add(((AIdentifierPattern) param).getName());
			}
		}
		b.add(((AIdentifierPattern) res.getPattern()).getName());
		//add the parameter types as dependencies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters() )
		{
			nodeDeps.addAll(pTp.apply(depVisitor, b));//(ThmTypeUtil.getIsabelleTypeDeps(pTp));
		}
		
		
		//Empty list for state variables (functions won't use any)
		NodeNameList s = new NodeNameList();
		ThmVarsContext vc = new ThmVarsContext(s, b);
		//deal with the precondition
		String pre = null;
		if (node.getPrecondition() != null){
			pre = node.getPrecondition().apply(stringVisitor, vc);// ThmExprUtil.getIsabelleExprStr(s, b, node.getPrecondition());
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, b));//(ThmExprUtil.getIsabelleExprDeps(b, node.getPrecondition()));
		}
		//deal with the postcondition
		String post = null;
		if (node.getPostcondition() != null){
			post = node.getPostcondition().apply(stringVisitor, vc);//ThmExprUtil.getIsabelleExprStr(s, b, node.getPostcondition());
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, b));//(ThmExprUtil.getIsabelleExprDeps(b, node.getPostcondition()));
		}
		//deal with the return type
		String resType = res.getType().apply(stringVisitor, vc);//ThmTypeUtil.getIsabelleType(res.getType());
		nodeDeps.addAll(res.getType().apply(depVisitor, b));//(ThmTypeUtil.getIsabelleTypeDeps(res.getType()));

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmImpFunc(name.getName(), post, pre, params, res, resType));
		tnl.add(tn);
		
		return tnl;
	}


	/**
	 * Visitor method for a collection of channel definitions
	 */
	@Override
	public ThmNodeList caseAChannelsDefinition(AChannelsDefinition node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (AChannelDefinition c : node.getChannelDeclarations())
		{
			tnl.addAll(c.apply(parentVisitor, vars));
		}

		return tnl;
	}


	/**
	 * Visitor method for a collection of chanset definitions
	 */
	@Override
	public ThmNodeList caseAChansetsDefinition(AChansetsDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (AChansetDefinition c : node.getChansets())
		{
			tnl.addAll(c.apply(parentVisitor, vars));
		}

		return tnl;
	}

//	/**
//	 * Visitor method for a class
//	 */
//	@Override
//	public ThmNodeList caseACmlClassDefinition(ACmlClassDefinition node, ThmVarsContext vars) throws AnalysisException
//	{
//		//TODO: NOT YET HANDLED
//		ThmNodeList tnl = new ThmNodeList();
//		return tnl;
//	}


	/**
	 * Visitor method for a CML process
	 */
	@Override
	public ThmNodeList caseAProcessDefinition(AProcessDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;		
		
		PProcess process = node.getProcess();
		ILexNameToken processName = node.getName();
				
		if (process instanceof AActionProcess)
		{
			AActionProcess act = (AActionProcess) process;
			tn = getIsabelleActionProcess(processName, act); // NEEDS TO BE RE-THOUGHT - PROBS MOVE METHOD TO HERE...
		}
		else
		{
			//get the Isabelle string for the process node's process.
			String procString = process.apply(stringVisitor, new ThmVarsContext());//= ThmProcessUtil.getIsabelleProcessString(node.getProcess());
			//obtain the process dependencies
			NodeNameList nodeDeps = process.apply(depVisitor, new NodeNameList());//ThmProcessUtil.getIsabelleProcessDeps(node.getProcess());

			//check for self dependencies - if present, require a MU
			for(ILexNameToken n : nodeDeps)
			{
				if(n.toString().equals(processName.toString()))
				{
					procString = ThmProcessUtil.isaMu + " " + processName.toString() + ". " + procString;
					break;
				}
			}
			//create and return the theorem node.			
			tn =  new ThmNode(processName, nodeDeps, new ThmProcStand(processName.toString(), procString));
		}
		
		//add the process node to the node list
		tnl.add(tn);

		return tnl;
	}
	
	
	/**
	 * NEED TO GET VISITORS WORKING HERE MORE...
	 * 
	 * Return the ThmNode for a Action Process - this is more complex than most other
	 * Node utils, due to the internal scoping etc required in a process
	 * @param procName the process name
	 * @param act the action process of the owning process
	 * @return the ThmNode object for this process
	 * @throws AnalysisException 
	 */
	private ThmNode getIsabelleActionProcess(ILexNameToken procName, AActionProcess act) throws AnalysisException
	{
		NodeNameList nodeDeps = new NodeNameList();		
		
		//need to define a new collection of node lists for the definitions inside a 
		//process. This is because we need to limit scope and dependencies within a 
		//action process, and output it as a single (contained) node.
		ThmNodeList actTnl = new ThmNodeList();
		//Require a list of all names used within a process, so to ensure the dependency 
		//relationships within and outside the process can be dealt with.
		NodeNameList procNodeNames = new NodeNameList();
		//Placeholder for main action - only changed if there are state variables
		String mainActStateStr = " = `";

		//Collect all the statement/operation/action paragraphs and deal with them 
		//all together.
		LinkedList<AStateDefinition> statements = new LinkedList<AStateDefinition>();
		LinkedList<SOperationDefinition> operations = new LinkedList<SOperationDefinition>();
		LinkedList<AImplicitFunctionDefinition> impfunctions = new LinkedList<AImplicitFunctionDefinition>();
		LinkedList<AExplicitFunctionDefinition> expfunctions = new LinkedList<AExplicitFunctionDefinition>();
		LinkedList<AActionDefinition> actions = new LinkedList<AActionDefinition>();
		LinkedList<PDefinition> others = new LinkedList<PDefinition>();
		AActionClassDefinition actdef = (AActionClassDefinition) act.getActionDefinition();
		
		for (PDefinition pdef : actdef.getDefinitions())
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
		
		//first we need to get all the state identifier names so expressions use correct reference
		NodeNameList svars = ThmProcessUtil.getStateNames(statements);
		//also get operation and action names
		NodeNameList opNames = ThmProcessUtil.getOperationNames(operations);
		NodeNameList efNames = ThmProcessUtil.getExpFunctionNames(expfunctions);
		NodeNameList ifNames = ThmProcessUtil.getImpFunctionNames(impfunctions);
		NodeNameList actNames = ThmProcessUtil.getActionNames(actions);
		
		//Add all state, operation and action names to list
		procNodeNames.addAll(svars);
		procNodeNames.addAll(opNames);
		procNodeNames.addAll(actNames);
		procNodeNames.addAll(efNames);
		procNodeNames.addAll(ifNames);
				
		//if there are state variables
		if (!svars.isEmpty())
		{
			//next generate nodes for the state variables, and add their initialised 
			//assignments to a collection for initialisation in main action
			//Also generate the invariant functions...
			LinkedList<String> initExprs = new LinkedList<String>();
			NodeNameList initExprNodeDeps = new NodeNameList();
			for (AStateDefinition pdef : statements)
			{
				for (PDefinition sdef : pdef.getStateDefs())
				{
					if (sdef instanceof AAssignmentDefinition)
					{
						AAssignmentDefinition st = (AAssignmentDefinition) sdef;
	
						//Get the state variable name
						ILexNameToken sName = st.getName();
						NodeNameList sNodeDeps = new NodeNameList();
						//if the variable is initialised straight away, add it to the initExprs string
						//and get the dependencies
						if (st.getExpression() != null)
						{
							initExprs.add(sName.toString() + ThmProcessUtil.assign + st.getExpression().apply(stringVisitor,new ThmVarsContext(svars, new NodeNameList()))); //ThmExprUtil.getIsabelleExprStr(svars, new NodeNameList(), st.getExpression()));
							initExprNodeDeps.addAll(st.getExpression().apply(depVisitor, new NodeNameList()));//(ThmExprUtil.getIsabelleExprDeps(new NodeNameList(),  st.getExpression()));
							//Add all dependencies to the processes dependencies
							nodeDeps.addAll(initExprNodeDeps);
							//As we only care about the internal dependencies in initExprNodeDeps, remove
							//any dependencies to CML elements external to the process
							initExprNodeDeps = initExprNodeDeps.restrictDeps(procNodeNames);
						}
						//if the variable is not initialised straight away, leave it as undefined.
						else
						{
							initExprs.add(sName.toString() + " := undefined");
						}
						//obtain the type of the state variable, and the type dependencies
						String type = st.getType().apply(stringVisitor, new ThmVarsContext());//ThmTypeUtil.getIsabelleType(st.getType());
						nodeDeps.addAll(st.getType().apply(depVisitor, new NodeNameList()));//(ThmTypeUtil.getIsabelleTypeDeps(st.getType()));
			
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
			ThmNode stn = new ThmNode(initName, initExprNodeDeps, new ThmExplicitOperation(initName.getName(), new LinkedList<PPattern>(), null, null, initExpStr.toString(), null));
			actTnl.add(stn);		
			
			mainActStateStr = " = `IsabelleStateInit; ";
		}

		
		
		
		//if the process has explicit functions
		if(!efNames.isEmpty())
		{
			ThmNodeList funcNodes = new ThmNodeList();
			//Handle the functions.
			for(AExplicitFunctionDefinition f : expfunctions){
				funcNodes.addAll(f.apply(parentVisitor, new ThmVarsContext(svars, new NodeNameList())));//(ThmProcessUtil.getAExplicitFunctionDefinition(f));
			}
				
			//ThmNodeList funcNodes = getIsabelleExpFunctions(expfunctions);
			//Add all function dependencies to the list of process dependencies
			nodeDeps.addAll(funcNodes.getAllNodeDeps());
			//restrict the function dependencies to only those names used within the process
			funcNodes = funcNodes.restrictExtOperationsDeps(procNodeNames);
			actTnl.addAll(funcNodes);
		}
		
		//if the process has implicit functions
		if(!ifNames.isEmpty())
		{
			ThmNodeList funcNodes = new ThmNodeList();
			//Handle the functions.
			for(AImplicitFunctionDefinition f : impfunctions){
				funcNodes.addAll(f.apply(parentVisitor, new ThmVarsContext(svars, new NodeNameList())));//(ThmProcessUtil.getAExplicitFunctionDefinition(f));
			}
			//Handle the functions.
//			ThmNodeList funcNodes = getIsabelleImpFunctions(impfunctions);
			//Add all function dependencies to the list of process dependencies
			nodeDeps.addAll(funcNodes.getAllNodeDeps());
			//restrict the function dependencies to only those names used within the process
			funcNodes = funcNodes.restrictExtOperationsDeps(procNodeNames);
			actTnl.addAll(funcNodes);
		}
		
		//if the process has operations
		if(!opNames.isEmpty())
		{
			//Handle the operations
			ThmNodeList opNodes = new ThmNodeList();
			for(SOperationDefinition op : operations)
			{
				opNodes.addAll(op.apply(this, new ThmVarsContext(svars, new NodeNameList())));//	getIsabelleOperation(op, svars));
			}						
			//ThmProcessUtil.getIsabelleOperations(operations, svars);
			//Add all operation dependencies to the list of process dependencies
			nodeDeps.addAll(opNodes.getAllNodeDeps());
			//restrict the operation dependencies to only those names used within the process
			opNodes = opNodes.restrictExtOperationsDeps(procNodeNames);
			actTnl.addAll(opNodes);
		}

		//if the process has actions
		if(!actNames.isEmpty())
		{
			//Handle the operations
			ThmNodeList actNodes = new ThmNodeList();
			//Handle the actions.
			//for each Action Definition node
			for(AActionDefinition a : actions)
			{
				actNodes.addAll(a.apply(this, new ThmVarsContext(svars, new NodeNameList())));//	.add(getIsabelleAction(a, svars, new NodeNameList()));
			}
			//TODO:NEED TO CHECK EACH ACT FOR RECURSION (See notepad :))
			//ThmNodeList actNodes = (ThmProcessUtil.getIsabelleActions(actions, svars, new NodeNameList()));
			//Add all action dependencies to the list of process dependencies
			nodeDeps.addAll(actNodes.getAllNodeDeps());
			//restrict the action dependencies to only those names used within the process
			actNodes = actNodes.restrictExtOperationsDeps(procNodeNames);
			actTnl.addAll(actNodes);
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
		//Remove all inner dependencies from the process dependency list. We only care about the 
		//things external to the process that we depend upon.
		nodeDeps = nodeDeps.removeDeps(procNodeNames);
				
		//Obtain the main action string
		PAction mainAction = act.getAction();
		String mainStr = ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + mainActStateStr + mainAction.apply(stringVisitor, new ThmVarsContext(svars, new NodeNameList())) +  "`\"";
		
		//Finally construct the node to represent the process
		return new ThmNode(procName, nodeDeps, new ThmProcAction(procName.toString(), actString, mainStr));
	}
	
	
	/***
	 * Return the ThmNode for an operations of a process 
	 * @param operations - the operation to check
	 * @param svars - the state variables
	 * @return a ThmNode for the operation
	 * @throws AnalysisException 
	 */
	@Override
	public ThmNodeList caseAImplicitOperationDefinition(AImplicitOperationDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		NodeNameList nodeDeps = new NodeNameList();

		String pre = null;
		String post = null;
		LinkedList<APatternListTypePair> params = node.getParameterPatterns();			
		//Deal with the parameters
		//Find bound values to exclude from dependency list and add node dependencies
		for(APatternListTypePair p : params)
		{
			for(PPattern pat : p.getPatterns())
			{
				vars.addBVar(((AIdentifierPattern) pat).getName());
			}
			nodeDeps.addAll(p.getType().apply(depVisitor, vars.getBVars()));//(ThmTypeUtil.getIsabelleTypeDeps(p.getType()));
		}	
		//Add return type(s) to dependancy list and list of bound values
		APatternTypePair res = node.getResult();
		String resType = null;
		if (res != null)// && !(res.isEmpty()) && res.getFirst()!= null)
		{
			resType = res.getType().apply(stringVisitor, vars);//ThmTypeUtil.getIsabelleType(res.getFirst().getType());
			vars.addBVar(((AIdentifierPattern) res.getPattern()).getName());
			nodeDeps.addAll(res.getType().apply(depVisitor, vars.getBVars()));//ThmTypeUtil.getIsabelleTypeDeps(p.getType()));
		}	
		
		if (node.getPrecondition() != null)
		{
			pre = node.getPrecondition().apply(stringVisitor, vars);// ThmExprUtil.getIsabelleExprStr(svars, bvars, node.getPrecondition());
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, vars.getBVars()));//(ThmExprUtil.getIsabelleExprDeps(bvars, node.getPrecondition()));
		}
		if (node.getPostcondition() != null)
		{
			//Set the expression utility postcondition flag to true - so to generate primed variables
			ThmExprUtil.setPostExpr(true);
			post = node.getPostcondition().apply(stringVisitor, vars); //ThmExprUtil.getIsabelleExprStr(svars, bvars, node.getPostcondition());
			ThmExprUtil.setPostExpr(false);
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, vars.getBVars()));//(ThmExprUtil.getIsabelleExprDeps(bvars, node.getPostcondition()));

		}
		tn = new ThmNode(node.getName(), nodeDeps, new ThmImplicitOperation(node.getName().toString(), params, pre, post, res, resType));
		
		tnl.add(tn);
		return tnl;
	}
		
	@Override
	public ThmNodeList caseAExplicitOperationDefinition(AExplicitOperationDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		NodeNameList nodeDeps = new NodeNameList();
		
		LinkedList<PPattern> params = node.getParameterPatterns();
		//Need parameters for local bound vars
		for(PPattern p : params)
		{
			vars.addBVar(((AIdentifierPattern) p).getName());
		}
		//Deal with the parameters
		//add the parameter types as dependencies
		AOperationType opType = (AOperationType) node.getType();
		for(PType pType : opType.getParameters())
		{
			nodeDeps.addAll(pType.apply(depVisitor, vars.getBVars()));//(ThmTypeUtil.getIsabelleTypeDeps(pType));
		}
		//Add result type to dependency list
		nodeDeps.addAll(opType.getResult().apply(depVisitor, vars.getBVars()));//(ThmTypeUtil.getIsabelleTypeDeps(opType.getResult()));
		
		String body = node.getBody().apply(stringVisitor, vars);//ThmProcessUtil.getIsabelleActionString(exOp.getBody(), svars, bvars);
		String pre = null;
		String post = null;
		if (node.getPrecondition() != null)
		{
			pre = node.getPrecondition().apply(stringVisitor, vars);// ThmExprUtil.getIsabelleExprStr(svars, bvars, node.getPrecondition());
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, vars.getBVars()));//(ThmExprUtil.getIsabelleExprDeps(bvars, node.getPrecondition()));
		}
		if (node.getPostcondition() != null)
		{
			//Set the expression utility postcondition flag to true - so to generate primed variables
			ThmExprUtil.setPostExpr(true);
			post = node.getPostcondition().apply(stringVisitor, vars); //ThmExprUtil.getIsabelleExprStr(svars, bvars, node.getPostcondition());
			ThmExprUtil.setPostExpr(false);
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, vars.getBVars()));//(ThmExprUtil.getIsabelleExprDeps(bvars, node.getPostcondition()));
		}
		String resType = null;
		if(! (node.getActualResult() instanceof AVoidType))
		{
			resType = node.getActualResult().apply(stringVisitor, vars);//ThmTypeUtil.getIsabelleType(node.getActualResult());
		}
		
		tn = new ThmNode(node.getName(), nodeDeps, new ThmExplicitOperation(node.getName().toString(), params, pre, post, body.toString(), resType));
		
		tnl.add(tn);
		return tnl;
	}
	
	/***
	 * Operation to provide a theorem node for a CML action.
	 * 
	 * @param act - the action definition
	 * @param svars - process state variable names
	 * @param bvars - bound variable names
	 * @return the theorem node produced
	 * @throws AnalysisException 
	 */
	public ThmNodeList caseAActionDefinition(AActionDefinition act, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		
		ThmNode tn = null;
		//get the action name
		ILexNameToken actName = act.getName();
		//obtain the action dependencies
		NodeNameList nodeDeps = act.getAction().apply(depVisitor, vars.getBVars());//ThmProcessUtil.getIsabelleActionDeps(act.getAction(), bvars);
		//get the Isabelle string for the action node's action.
		String actString = act.getAction().apply(stringVisitor, vars); //ThmProcessUtil.getIsabelleActionString(act.getAction(), svars, bvars);
		//check for self dependencies - if present, require a MU
		for(ILexNameToken n : nodeDeps)
		{
			if(n.toString().equals(actName.toString()))
			{
				actString = ThmProcessUtil.isaMu + " " + actName.toString() + ". " + actString;
				break;
			}
		}
		//create the theorem node.
		tn = new ThmNode(actName, nodeDeps, new ThmAction(actName.toString(), actString));

		tnl.add(tn);
		return tnl;
	}
	
	
	
	
	
	
	
	@Override
	public ThmNodeList defaultPSingleDeclaration(PSingleDeclaration node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(parentVisitor, vars));
		return tnl;
	}
//
//	@Override
//	public ThmNodeList defaultPDefinition(PDefinition node) throws AnalysisException
//	{
//		ThmNodeList tnl = new ThmNodeList();
//		tnl.addAll(node.apply(parentVisitor));
//		return tnl;
//	}

	// Call the main visitor when it's not a definition/declaration
	@Override
	public ThmNodeList defaultINode(INode node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(parentVisitor, vars));
		return tnl;
	}

	@Override
	public ThmNodeList createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThmNodeList createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
