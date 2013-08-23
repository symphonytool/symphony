package eu.compassresearch.theoremprover.visitors;

//POG-related imports
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.theoremprover.thms.ThmExpFunc;
import eu.compassresearch.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.theoremprover.thms.ThmImpFunc;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmProcAction;
import eu.compassresearch.theoremprover.thms.ThmProcStand;
import eu.compassresearch.theoremprover.thms.ThmState;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

@SuppressWarnings("serial")
public class ThmDeclAndDefVisitor extends AnswerCMLAdaptor<ThmNodeList>
{
	final private AnswerAdaptor<ThmNodeList> parentVisitor;

	public ThmDeclAndDefVisitor(AnswerCMLAdaptor<ThmNodeList> parent)
	{
		this.parentVisitor = parent;
	}
	
	/**
	 * Visitor method for collection of type definitions
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
	 * Visitor method for collection of value definitions
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
	 * Visitor method for collection of function definitions
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

	/**
	 * Visitor method for an explicitly defined function
	 */
	@Override
	public ThmNodeList caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		ILexNameToken name = node.getName();
		
		//Deal with the parameters
		LinkedList<List<PPattern>> params = node.getParamPatternList();
		//Find bound values to exclude from dependancy list
		LinkedList<ILexNameToken> b = new LinkedList<ILexNameToken>();
		for(PPattern p : params.getFirst() )
		{
			b.add(((AIdentifierPattern) p).getName());
		}
		//add the parameter types as dependancies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters() )
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(pTp));
		}
		
		//Deal with the function body
		LinkedList<ILexNameToken> s = new LinkedList<ILexNameToken>();
		String exp = ThmExprUtil.getIsabelleExprStr(s, b, node.getBody());
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getBody()));

		//Deal with the function precondition
		String pre = null;
		if (node.getPrecondition() != null){
			pre = ThmExprUtil.getIsabelleExprStr(s, b, node.getPrecondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPrecondition()));
		}
		
		//Deal with the function postcondition
		String post = null;
		if (node.getPostcondition() != null){
			post = ThmExprUtil.getIsabelleExprStr(s, b, node.getPostcondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPostcondition()));
		}
		
		//Deal with the function result
		String resType = ThmTypeUtil.getIsabelleType(node.getExpectedResult());
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getExpectedResult()));
		
		ThmNode tn = new ThmNode(name, nodeDeps, new ThmExpFunc(name.getName(), exp, post, pre, params, resType));
		tnl.add(tn);
		
		return tnl;
	}


	/**
	 * Visitor method for an implicitly defined function
	 */
	public ThmNodeList caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();

		ILexNameToken name = node.getName();
		LinkedList<APatternListTypePair> params = node.getParamPatterns();
		APatternTypePair res = node.getResult();

		LinkedList<ILexNameToken> b = new LinkedList<ILexNameToken>();
		//Find bound values to exclude from dependancy list
		for(APatternListTypePair p : params )
		{
			LinkedList<PPattern> pats = p.getPatterns();
			for(PPattern param : pats )
			{
				b.add(((AIdentifierPattern) param).getName());
			}
		}
		b.add(((AIdentifierPattern) res.getPattern()).getName());
		//add the parameter types as dependancies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters() )
		{
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(pTp));
		}
		
		
		//Empty list for state variables (functions won't use any)
		LinkedList<ILexNameToken> s = new LinkedList<ILexNameToken>();
		//deal with the precondition
		String pre = null;
		if (node.getPrecondition() != null){
			pre = ThmExprUtil.getIsabelleExprStr(s, b, node.getPrecondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPrecondition()));
		}
		//deal with the postcondition
		String post = null;
		if (node.getPostcondition() != null){
			post = ThmExprUtil.getIsabelleExprStr(s, b, node.getPostcondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPostcondition()));
		}
		//deal with the return type
		String resType = ThmTypeUtil.getIsabelleType(res.getType());
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(res.getType()));

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmImpFunc(name.getName(), post, pre, params, res, resType));
		tnl.add(tn);
		
		return tnl;
	}


	/**
	 * Visitor method for a collection of channel definitions
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


	/**
	 * Visitor method for a collection of chanset definitions
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

	/**
	 * Visitor method for a class
	 */
	@Override
	public ThmNodeList caseACmlClassDefinition(ACmlClassDefinition node) throws AnalysisException
	{
		//TODO: NOT YET HANDLED
		ThmNodeList tnl = new ThmNodeList();
		return tnl;
	}


	/**
	 * Visitor method for a CML process
	 */
	@Override
	public ThmNodeList caseAProcessDefinition(AProcessDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		ILexNameToken procName = node.getName();
		
		//If the process is an action process (with encapsulated state
		if (node.getProcess() instanceof AActionProcess)
		{
			AActionProcess act = (AActionProcess) node.getProcess();
			
			//need to define a new collection of node lists for the definitions inside a 
			//process. This is because we need to limit scope and dependancies within a 
			//action process, and output it as a single (contained) node.
			ThmNodeList actTnl = new ThmNodeList();
			//Require a list of all names used within a process, so to ensure the dependancy 
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
							initExprs.add(sName.toString() + " := "+ ThmExprUtil.getIsabelleExprStr(svars, new LinkedList<ILexNameToken>(), st.getExpression()));
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
			LexNameToken initName = new LexNameToken("", "IsabelleStateInit", node.getLocation());
			ThmNode stn = new ThmNode(initName, initExprNodeDeps, new ThmExplicitOperation(initName.getName(), new LinkedList<PPattern>(), null, null, initExpStr.toString()));
			actTnl.add(stn);
			
			
			//TODO: Handle the operations - BODIES REQUIRED
			LinkedList<ThmNode> opNodes = ThmProcessUtil.getIsabelleOperations(operations, svars);
			//Add all dependancies to the list of process deps
			nodeDeps.addAll(ThmProcessUtil.getIsabelleOperationsDeps(opNodes));
			//restrict the operation dependancies to only those names used within the process
			opNodes = ThmProcessUtil.restrictExtOperationsDeps(opNodes, procNodeNames);
			actTnl.addAll(opNodes);
			//TODO: Handle the actions.
			//TODO:NEED TO CHECK EACH ACT FOR RECURSION (See notepad :))
			LinkedList<ThmNode> actNodes =(ThmProcessUtil.getIsabelleActions(actions, svars, new LinkedList<ILexNameToken>()));

			//Add all dependancies to the list of process deps
//			nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(actNodes));
			//restrict the operation dependancies to only those names used within the process
//			actNodes = ThmProcessUtil.restrictExtActionDeps(opNodes, procNodeNames);
			actTnl.addAll(actNodes);
			
			//sort the state, operation and actions, so that they are in dependancy order
			actTnl = TPVisitor.sortThmNodes(actTnl);
			//Remove all inner dependancies from the process dependancy list. We only care about the 
			//things external to the process that we depend upon.
			nodeDeps = ThmProcessUtil.removeProcessDeps(nodeDeps,procNodeNames);

			//Obtain the main action string
			PAction mainAction = act.getAction();
			String mainStr = ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + " = IsabelleStateInit; " + ThmProcessUtil.getIsabelleActionString(mainAction, svars, new LinkedList<ILexNameToken>()) +  "\"";
			
			//Finally construct the node to represent the process
			tn = new ThmNode(procName, nodeDeps, new ThmProcAction(procName.toString(), actTnl.toString(), mainStr));
		}
		//Is not a stateful process
		//TODO: Handle other process types.
		else
		{
			String procBody = ""; //ThmProcessUtil.getIsabelleProcess(pProc);
			tn = new ThmNode(procName, nodeDeps, new ThmProcStand(procName.toString(), procBody));
		}
		
		//add the process to the process list
		tnl.add(tn);

		return tnl;
	}

	@Override
	public ThmNodeList caseAStateDefinition(AStateDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		for (PDefinition def : node.getStateDefs())
		{
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
		ThmNodeList tnl = new ThmNodeList();

		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions)
		{
			tnl.addAll(action.apply(parentVisitor));
		}

		return tnl;
	}

//	@Override
//	public ThmNodeList caseAActionDefinition(AActionDefinition node) throws AnalysisException
//	{
//		ThmNodeList tnl = new ThmNodeList();
//
//		PAction action = node.getAction();
//		//tnl.addAll(action.apply(parentVisitor));
//
//		return tnl;
//	}

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
}
