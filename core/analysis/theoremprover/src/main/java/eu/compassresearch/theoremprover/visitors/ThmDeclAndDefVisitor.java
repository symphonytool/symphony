package eu.compassresearch.theoremprover.visitors;

//POG-related imports
import java.util.Iterator;
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
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AFunctionType;
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
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
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
public class ThmDeclAndDefVisitor  extends
		AnswerCMLAdaptor<ThmNodeList>
{

	// Errors and other things are recorded on this guy
	final private AnswerAdaptor<ThmNodeList> parentVisitor;

	public ThmDeclAndDefVisitor(AnswerCMLAdaptor<ThmNodeList> parent)
	{
		this.parentVisitor = parent;

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
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();

		ILexNameToken name = node.getName();
		
		//Deal with the parameters
		LinkedList<List<PPattern>> params = node.getParamPatternList();
		//Find bound values to exclude from dependancy list
		LinkedList<ILexNameToken> b = new LinkedList();
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
		LinkedList<ILexNameToken> s = new LinkedList();
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
	
	public ThmNodeList caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		LinkedList<ILexNameToken> nodeDeps = new LinkedList();

		ILexNameToken name = node.getName();
		LinkedList<APatternListTypePair> params = node.getParamPatterns();
		APatternTypePair res = node.getResult();

		LinkedList<ILexNameToken> b = new LinkedList();
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
		
		
		
		LinkedList<ILexNameToken> s = new LinkedList();
		String pre = null;
		if (node.getPrecondition() != null){
			pre = ThmExprUtil.getIsabelleExprStr(s, b, node.getPrecondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPrecondition()));
		}
		String post = null;
		if (node.getPostcondition() != null){
			post = ThmExprUtil.getIsabelleExprStr(s, b, node.getPostcondition());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(b, node.getPostcondition()));
		}
		String resType = ThmTypeUtil.getIsabelleType(res.getType());
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(res.getType()));

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmImpFunc(name.getName(), post, pre, params, res, resType));
		tnl.add(tn);
		
		return tnl;
	}
	
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
		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		ILexNameToken procName = node.getName();
		
		PProcess pProc = node.getProcess();
		if (pProc instanceof AActionProcess)
		{
			AActionProcess act = (AActionProcess) pProc;
			PAction mainAction = act.getAction();
			
			//need to define a new collection of node lists for the definitions inside a 
			//process. This is because we need to limit scope and dependancies within a 
			//action process, and output it as a single (contained) node.
			ThmNodeList actTnl = new ThmNodeList();
			LinkedList<AStateDefinition> statements = new LinkedList();
			LinkedList<PDefinition> operations = new LinkedList();
			LinkedList<PDefinition> actions = new LinkedList();
			LinkedList<PDefinition> others = new LinkedList();
			
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
			LinkedList<ILexNameToken> svars = new LinkedList();
			for (AStateDefinition pdef : statements)
			{
				for (PDefinition sdef : pdef.getStateDefs())
				{
					if (sdef instanceof AAssignmentDefinition)
					{
						AAssignmentDefinition st = (AAssignmentDefinition) sdef;

						ILexNameToken name = st.getName();
						svars.add(name);
					}
				}
			}
			
			//next generate nodes for the state variables, and add their initialised 
			//assignments to a collection for initialisation in main action
			//Also generate the invariant functions...
			LinkedList<String> initExprs = new LinkedList();
			LinkedList<ILexNameToken> initExprNodeDeps = new LinkedList();
			for (AStateDefinition pdef : statements)
			{
				for (PDefinition sdef : pdef.getStateDefs())
				{
					if (sdef instanceof AAssignmentDefinition)
					{
						AAssignmentDefinition st = (AAssignmentDefinition) sdef;

						ILexNameToken sName = st.getName();
						LinkedList<ILexNameToken> sNodeDeps = new LinkedList();
						initExprs.add("$" +sName.toString() + " := "+ ThmExprUtil.getIsabelleExprStr(svars, new LinkedList(), st.getExpression()));
						initExprNodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(new LinkedList(),  st.getExpression()));
						String type = ThmTypeUtil.getIsabelleType(st.getType());
			
						ThmNode stn = new ThmNode(sName, sNodeDeps, new ThmState(sName.getName(), type));
						actTnl.add(stn);
					}
				}
				//TODO: Define state invariants
			}

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
			ThmNode stn = new ThmNode(initName, initExprNodeDeps, new ThmExplicitOperation(initName.getName(), null, null, initExpStr.toString()));
			actTnl.add(stn);
			
			
			//TODO: Handle the operations.
			
			//TODO: Handle the actions.
			
			//sort the state, operation and actions, so that they are in dependancy order
			//TODO: BREAKS HERE!!!!
			actTnl = TPVisitor.sortThmNodes(actTnl);
			String mainStr = "";//ThmActionUtil.getIsabelleAction(mainAction);
			
			tn = new ThmNode(procName, nodeDeps, new ThmProcAction(procName.toString(), actTnl.toString(), mainStr));

		}
		else
		{
			String procBody = ""; //ThmProcessUtil.getIsabelleProcess(pProc);
			tn = new ThmNode(procName, nodeDeps, new ThmProcStand(procName.toString(), procBody));
		}
		
		

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

	@Override
	public ThmNodeList caseAActionDefinition(AActionDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();

		PAction action = node.getAction();
		//tnl.addAll(action.apply(parentVisitor));

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
//	
//	@Override
//	public ThmNodeList caseAAssignmentDefinition(
//			AAssignmentDefinition node)
//			throws AnalysisException
//	{
//		ThmNodeList tnl = new ThmNodeList();
//
//		ILexNameToken name = node.getName();
//		LinkedList<ILexNameToken> nodeDeps = new LinkedList();
//		//TODO: MAY REQUIRE THAT THIS GOES INTO A UTIL - AS WILL NEED THE SVARS... LEAVE FOR NOW
//		String expr = ThmExprUtil.getIsabelleExprStr(new LinkedList(), new LinkedList(), node.getExpression());
//		String type = ThmTypeUtil.getIsabelleType(node.getType());
//		
//		ThmNode tn = new ThmNode(name, nodeDeps, new ThmState(name.getName(), type, expr));
//		tnl.add(tn);
//
//		return tnl;
//	}

}
