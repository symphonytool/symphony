package eu.compassresearch.core.analysis.theoremprover.visitors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.AIgnorePattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SInvariantType;

import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChannel;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChanset;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExpFunc;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmExplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmImpFunc;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmImplicitOperation;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmProcAction;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmProcStand;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmRecType;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmState;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmStateInv;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmType;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmValue;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class ThmDeclAndDefVisitor extends QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList>
{
	private ThmDepVisitor depVisitor;
	private ThmStringVisitor stringVisitor;
	private TPVisitor tpVisitor;
	
	public ThmDeclAndDefVisitor(TPVisitor tpVisitor, ThmDepVisitor depVisitor, ThmStringVisitor stringVisitor){
		this.depVisitor = depVisitor;
		this.stringVisitor = stringVisitor;
		this.tpVisitor = tpVisitor;
	}
	
	@Override
	public ThmNodeList caseATypeDefinition(ATypeDefinition node, ThmVarsContext vars)
			throws AnalysisException {

		ThmNodeList tnl = new ThmNodeList();
		ThmNode tn = null;
		ILexNameToken name = node.getName();
		PType type = node.getType();
		String typeStr = "";
		NodeNameList nodeDeps = new NodeNameList();
		String invCall = "";
		LinkedList<String> invparams = new LinkedList<String>();
		ThmNodeList invNode = null;

		
		if(type instanceof SInvariantType)
		{	
			
			AExplicitFunctionDefinition invFunc = ((SInvariantType) type).getInvDef();
			if (invFunc != null)
			{
				//Get the invariant function using the TPVisitor
				invNode = invFunc.apply(tpVisitor,vars);

				//Consruct the string for calling the invariant
				invCall = "inv_"+ name;
				LinkedList<List<PPattern>> invParamList = invFunc.getParamPatternList();
				for(List<PPattern> pplist : invParamList)
				{
					for(PPattern pp : pplist)
					{
						invparams.add(((AIdentifierPattern) pp).getName().toString());
					}
				}
			}
		}
		if (type instanceof ANamedInvariantType)
		{
			//if the named type has an invariant...
			if (invNode != null)
			{
				for(ThmNode i : invNode)
				{
					//Add invariant definition to type dependency 
					nodeDeps.add(i.getId());
				}
				tnl.addAll(invNode);
			}
			
			ANamedInvariantType nametype = (ANamedInvariantType) type;
			PType tp = nametype.getType();
			//Send to visitor for Type String
			typeStr = tp.apply(stringVisitor, new ThmVarsContext());
			//Send to visitor for Deplist
			nodeDeps.addAll(tp.apply(depVisitor, new NodeNameList()));

			tn = new ThmNode(name, nodeDeps, new ThmType(name.toString(), typeStr, invCall, invparams));
			
		}
		else if (type instanceof ARecordInvariantType)
		{
			ARecordInvariantType rtype = (ARecordInvariantType) type;
			//Send to visitor for Deplist
			nodeDeps.addAll(rtype.apply(depVisitor, new NodeNameList()));

			String invDefn = "";

			//if the record type has an invariant...
			if (invNode != null)
			{
				//Get the invariant - should only be one.
				ThmNode i = invNode.firstElement();
				{
					((ThmExpFunc) i.getArtifact()).setRecordInv(true);
					invDefn = i.toString();
				}
			}
			tn = new ThmNode(name, nodeDeps, new ThmRecType(name.toString(), rtype.getFields(), invDefn, invCall, invparams));
		}
		
		tnl.add(tn);
		return tnl;
	}

//	/***
//	 * Method to return a string for the type invariant
//	 * @param node - the type AST node
//	 * @return an invariant string
//	 * @throws AnalysisException 
//	 */
//	private String getIsabelleTypeInv(ATypeDefinition node) throws AnalysisException
//	{
//		String inv = "";
//		PExp invExp = node.getInvExpression();
//		PPattern invPatt = node.getInvPattern();
//		ThmVarsContext vc = new ThmVarsContext();
//		if(invExp != null && invPatt != null){
//			//NodeNameList svars = new NodeNameList();
//			//NodeNameList evars = new NodeNameList();
//
//			if (invPatt instanceof AIdentifierPattern)
//			{
//				vc.addBVar(((AIdentifierPattern) invPatt).getName());
//			}
//			else if (invPatt instanceof ARecordPattern)
//			{
//				ARecordPattern recInvPatt = (ARecordPattern) invPatt;
//				for(PPattern p : recInvPatt.getPlist())
//				{
//					if (p instanceof AIdentifierPattern)
//					{
//						vc.addBVar(((AIdentifierPattern) p).getName());
//					}
//				}
//			}
//			inv = (" " + ThmTypeUtil.isaInv  + " " + invPatt.toString() + " == " + invExp.apply(stringVisitor, vc)); //ThmExprUtil.getIsabelleExprStr(svars, evars, invExp));
//		}
//		
//		return inv;
//	}
//
//
//	/***
//	 * Method to return the list of dependencies for the type invariant
//	 * @param node - the type AST node
//	 * @return the list of dependencies
//	 * @throws AnalysisException 
//	 */
//	private NodeNameList getIsabelleTypeInvDeps(ATypeDefinition node) throws AnalysisException
//	{
//		NodeNameList nodeDeps = new NodeNameList();
//
//		PExp invExp = node.getInvExpression();
//		PPattern invPatt = node.getInvPattern();
//		if(invExp != null && invPatt != null){
//			NodeNameList bvars = new NodeNameList();
//			if (invPatt instanceof AIdentifierPattern)
//			{
//				bvars.add(((AIdentifierPattern) invPatt).getName());
//
//			}
//			else if (invPatt instanceof ARecordPattern)
//			{
//				ARecordPattern recInvPatt = (ARecordPattern) invPatt;
//				for(PPattern p : recInvPatt.getPlist())
//				{
//					if (p instanceof AIdentifierPattern)
//					{
//						bvars.add(((AIdentifierPattern) p).getName());
//					}
//				}
//			}
//			nodeDeps.addAll(invExp.apply(depVisitor,bvars));//(ThmExprUtil.getIsabelleExprDeps(evars, invExp));
//		}
//		
//		return nodeDeps;
//	}

	@Override
	public ThmNodeList caseAValueDefinition(AValueDefinition node, ThmVarsContext vars)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		ILexNameToken valName =  ((AIdentifierPattern) node.getPattern()).getName();
		
		String typeStr = node.getType().apply(stringVisitor, new ThmVarsContext()); //ThmTypeUtil.getIsabelleType(node.getType());
		String exprStr = node.getExpression().apply(stringVisitor, new ThmVarsContext()); //ThmExprUtil.getIsabelleExprStr(svars, evars, node.getExpression());

		NodeNameList nodeDeps = node.apply(depVisitor, new NodeNameList());//ThmValueUtil.getIsabelleValueDeps(node);
		
		ThmNode tn = new ThmNode(valName, nodeDeps, new ThmValue(valName.toString(), typeStr, exprStr));
		tnl.add(tn);
		
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
		LinkedList<String> paramTypes = new LinkedList<String>(); 
		
		ILexNameToken name = node.getName();
		
		//Deal with the parameters
		LinkedList<List<PPattern>> params = node.getParamPatternList();
		//Find bound values to exclude from dependency list
		NodeNameList b = new NodeNameList();
		for(PPattern p : params.getFirst() )
		{
			if (p instanceof ARecordPattern ) {
				LinkedList<PPattern> recParams = ((ARecordPattern) p).getPlist();
				for(PPattern rec : recParams)
				{
					if (rec instanceof AIdentifierPattern ) {
						b.add(((AIdentifierPattern) rec).getName());
					}
				}
			}
			else if(!(p instanceof AIgnorePattern)){
				b.add(((AIdentifierPattern) p).getName());
			}
		}
		//add the parameter types as dependencies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters())
		{
			nodeDeps.addAll(pTp.apply(depVisitor, new NodeNameList()));
			paramTypes.add(pTp.apply(stringVisitor, new ThmVarsContext()));
		}
		
		//Deal with the function body
		NodeNameList s = new NodeNameList();
		ThmVarsContext vc = new ThmVarsContext(s, b);
		String exp = node.getBody().apply(stringVisitor, vc);
		nodeDeps.addAll(node.getBody().apply(depVisitor, b));

		//Deal with the function precondition
		String pre = null;
		if (node.getPrecondition() != null){
			pre = node.getPrecondition().apply(stringVisitor, vc);
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, b));
		}
		
		//Deal with the function postcondition
		String post = null;
		if (node.getPostcondition() != null){
			post = node.getPostcondition().apply(stringVisitor, vc);
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, b));
		}
		
		//Deal with the function result
		String resType = node.getType().getResult().apply(stringVisitor, vc);
		nodeDeps.addAll(node.getType().getResult().apply(depVisitor, b));
		
		ThmNode tn = new ThmNode(name, nodeDeps, new ThmExpFunc(name.getName(), exp, post, pre, params, paramTypes, resType));
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
		LinkedList<String> paramTypes = new LinkedList<String>();
		
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
				if (param instanceof ARecordPattern ) {
					LinkedList<PPattern> recParams = ((ARecordPattern) param).getPlist();
					for(PPattern rec : recParams)
					{
						if (rec instanceof AIdentifierPattern ) {
							b.add(((AIdentifierPattern) rec).getName());
						}
					}
				}
				else if(!(param instanceof AIgnorePattern)){
					b.add(((AIdentifierPattern) param).getName());
				}
			}
		}
		b.add(((AIdentifierPattern) res.getPattern()).getName());
		//add the parameter types as dependencies
		for(PType pTp : ((AFunctionType) node.getType()).getParameters() )
		{
			nodeDeps.addAll(pTp.apply(depVisitor, b));//(ThmTypeUtil.getIsabelleTypeDeps(pTp));
			paramTypes.add(pTp.apply(stringVisitor, new ThmVarsContext()));
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

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmImpFunc(name.getName(), post, pre, params, res, paramTypes, resType));
		tnl.add(tn);
		
		return tnl;
	}


	/**
	 * CML channel definition 
	 */
	@Override
	public ThmNodeList caseAChannelDefinition(AChannelDefinition node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ILexNameToken name = null;
		String type = "()";
		NodeNameList nodeDeps =  new NodeNameList();
		
		name = node.getName();
		PType chanType = node.getType();
		if (chanType != null)
		{
			nodeDeps = chanType.apply(depVisitor, new NodeNameList());//ThmChanUtil.getIsabelleChanDeps(node);
			type = chanType.apply(stringVisitor, new ThmVarsContext()); //ThmTypeUtil.getIsabelleType(((AChannelType) chan.getType()).getType());
		}
		ThmNode tn = new ThmNode(name, nodeDeps, new ThmChannel(name.toString(), type));
		tnl.add(tn);
		
		return tnl;
	}

	/**
	 * CML chanset definition
	 */
	@Override
	public ThmNodeList caseAChansetDefinition(AChansetDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		//TODO: FIX THIS ONCE RESOLVED
		LexNameToken name = new LexNameToken("", node.getIdentifier().toString(), node.getIdentifier().getLocation());
		String expr = "";
		NodeNameList nodeDeps = new NodeNameList();

		PVarsetExpression chExpr = node.getChansetExpression();
		if (chExpr != null)
		{
			expr = chExpr.apply(stringVisitor, new ThmVarsContext());//ThmExprUtil.getIsabelleVarsetExpr(chExpr.apply(stringVisitor, new ThmVarsContext()););
			nodeDeps = chExpr.apply(depVisitor, new NodeNameList());//ThmExprUtil.getIsabelleVarsetExprDeps(chExpr);
		}

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmChanset(name.toString(), expr));
		tnl.add(tn);
		
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

			AProcessDefinition parentProcess = act.getAncestor(AProcessDefinition.class);
			
			List<String> param = new LinkedList<String>();
			
			ThmVarsContext bvars = new ThmVarsContext();
			NodeNameList nnl = new NodeNameList();
			
			// Add potential parameters to the process and mark them as bound
			for (PParametrisation p : parentProcess.getLocalState()) {
				StringBuffer sb = new StringBuffer();
				
				// FIXME: Abstract this out
				
				sb.append("  fixes ");
				sb.append(p.getDeclaration().getName().toString() + "\n");
				sb.append("  assumes \"`\\<lparr>^");
				sb.append(p.getDeclaration().getName().toString() + "^ hasType ");
				sb.append(p.getDeclaration().getType().apply(stringVisitor, new ThmVarsContext()));
				sb.append("\\<rparr>`\"");
				param.add(sb.toString());
				nnl.add(p.getDeclaration().getName());
				bvars.addBVar(p.getDeclaration().getName());
			}
			
			//get the Isabelle string for the process node's process.
			String procString = act.apply(stringVisitor, bvars);
			//obtain the process dependencies
			NodeNameList nodeDeps = act.apply(depVisitor, nnl);
			
			tn = new ThmNode(parentProcess.getName(), nodeDeps, new ThmProcAction(parentProcess.getName().toString(), param, procString));
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
				if(n.getName().equals(processName.getName()))
				{
					procString = ThmProcessUtil.isaMu + " " + processName.getName() + ". " + procString;
					break;
				}
			}
			//create and return the theorem node.			
			tn =  new ThmNode(processName, nodeDeps, new ThmProcStand(processName.getName(), procString));
		}
		
		//add the process node to the node list
		tnl.add(tn);

		return tnl;
	}
	
	
	@Override
	public ThmNodeList caseAInstanceVariableDefinition(AInstanceVariableDefinition node, ThmVarsContext vars) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		//Get the state variable name
		ILexNameToken sName = node.getName();
		//obtain the type of the state variable, and the type dependencies
		String typeString = node.getType().apply(stringVisitor, new ThmVarsContext());
		NodeNameList nodeDeps = node.getType().apply(depVisitor, new NodeNameList());

		ThmNode stn = new ThmNode(sName, nodeDeps, new ThmState(sName.getName(), typeString));
		tnl.add(stn);
		//TODO: Define state invariants
		return tnl;
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
				if (pat instanceof ARecordPattern ) {
					LinkedList<PPattern> recParams = ((ARecordPattern) pat).getPlist();
					for(PPattern rec : recParams)
					{
						if (rec instanceof AIdentifierPattern ) {
							vars.addBVar(((AIdentifierPattern) rec).getName());
						}
					}
				}
				else if(!(pat instanceof AIgnorePattern))	{
					vars.addBVar(((AIdentifierPattern) pat).getName());
				}
			}
			
			nodeDeps.addAll(p.getType().apply(depVisitor, vars.getBVars()));
		}	
		//Add return type(s) to dependency list and list of bound values
		APatternTypePair res = node.getResult();
		String resType = null;
		if (res != null)// && !(res.isEmpty()) && res.getFirst()!= null)
		{
			resType = res.getType().apply(stringVisitor, vars);
			vars.addBVar(((AIdentifierPattern) res.getPattern()).getName());
			nodeDeps.addAll(res.getType().apply(depVisitor, vars.getBVars()));
		}	
		
		// Get the frame of this implicit operation
		LinkedList<String> frame = new LinkedList<String>();
		
		if (node.getExternals() != null)
		{
			for (AExternalClause ext : node.getExternals()) {
				if (ext.getMode().toString().equals("wr")) {
					for (ILexNameToken i : ext.getIdentifiers()) {
						frame.add(i.toString());
					}
				}
			}
		}
		
		if (node.getPrecondition() != null)
		{
			pre = node.getPrecondition().apply(stringVisitor, vars);
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, vars.getBVars()));
		}
		if (node.getPostcondition() != null)
		{
			//Set the expression utility postcondition flag to true - so to generate primed variables
			ThmExprUtil.setPostExpr(true);
			stringVisitor.setPostExpr(true);
			post = node.getPostcondition().apply(stringVisitor, vars); 
			ThmExprUtil.setPostExpr(false);
			stringVisitor.setPostExpr(false);
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, vars.getBVars()));

		}
		tn = new ThmNode(node.getName(), nodeDeps, new ThmImplicitOperation(node.getName().getName(), params, frame, pre, post, res, resType));
		
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
			if (p instanceof ARecordPattern ) {
				LinkedList<PPattern> recParams = ((ARecordPattern) p).getPlist();
				for(PPattern rec : recParams)
				{
					if (rec instanceof AIdentifierPattern ) {
						vars.addBVar(((AIdentifierPattern) rec).getName());
					}
				}
			}
			else if(!(p instanceof AIgnorePattern))	{
				vars.addBVar(((AIdentifierPattern) p).getName());
			}
		}
		
		
		//Deal with the parameters
		//add the parameter types as dependencies
		AOperationType opType = (AOperationType) node.getType();
		for(PType pType : opType.getParameters())
		{
			nodeDeps.addAll(pType.apply(depVisitor, vars.getBVars()));
		}
		//Add result type to dependency list
		nodeDeps.addAll(opType.getResult().apply(depVisitor, vars.getBVars()));
		
		String body = node.getBody().apply(stringVisitor, vars);
		nodeDeps.addAll(node.getBody().apply(depVisitor, vars.getBVars()));
		String pre = null;
		String post = null;
		if (node.getPrecondition() != null)
		{
			pre = node.getPrecondition().apply(stringVisitor, vars);
			nodeDeps.addAll(node.getPrecondition().apply(depVisitor, vars.getBVars()));
		}
		if (node.getPostcondition() != null)
		{
			//Set the expression utility postcondition flag to true - so to generate primed variables
			ThmExprUtil.setPostExpr(true);
			stringVisitor.setPostExpr(true);
			post = node.getPostcondition().apply(stringVisitor, vars); 
			ThmExprUtil.setPostExpr(false);
			stringVisitor.setPostExpr(false);
			nodeDeps.addAll(node.getPostcondition().apply(depVisitor, vars.getBVars()));
		}
//		String resType = null;
//		if(! (node.getActualResult() instanceof AVoidType))
//		{
//			resType = node.getActualResult().apply(stringVisitor, vars);//ThmTypeUtil.getIsabelleType(node.getActualResult());
//		}

		LinkedList<String> paramTypes = new LinkedList<String>();
		for(PType p : ((AOperationType) node.getType()).getParameters())
		{
				paramTypes.add(p.apply(stringVisitor, vars));
		}
		String resType = ((AOperationType) node.getType()).getResult().apply(stringVisitor, vars);
		
		tn = new ThmNode(node.getName(), nodeDeps, new ThmExplicitOperation(node.getName().getName(), params, pre, post, body.toString(), paramTypes, resType));
		
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

		StringBuffer apsb = new StringBuffer();
		
		// Deal with potential action parameters
		for (Iterator<PParametrisation> ai = act.getDeclarations().iterator(); ai.hasNext(); ) {
			ALocalDefinition adec = ai.next().getDeclaration(); 
			apsb.append(adec.getName().toString());
			vars.addBVar(adec.getName());
			apsb.append(" : ");
			apsb.append(adec.getType().apply(stringVisitor, vars));
			if (ai.hasNext()) {
				apsb.append(", ");
			}
		}

		
		//obtain the action dependencies
		NodeNameList nodeDeps = act.getAction().apply(depVisitor, vars.getBVars());//ThmProcessUtil.getIsabelleActionDeps(act.getAction(), bvars);
		
		
		if (!act.getDeclarations().isEmpty())
			apsb.append(" @ ");
		
		//get the Isabelle string for the action node's action.
		String actString = apsb.toString() + act.getAction().apply(stringVisitor, vars); //ThmProcessUtil.getIsabelleActionString(act.getAction(), svars, bvars);
		//check for self dependencies - if present, require a MU
		/*
		for(ILexNameToken n : nodeDeps)
		{
			if(n.getName().equals(actName.getName()))
			{
				actString = ThmProcessUtil.isaMu + " " + actName.getName() + ". " + actString;
				break;
			}
		}
		*/
		//create the theorem node.
		tn = new ThmNode(actName, nodeDeps, new ThmAction(actName.getName(), actString));

		tnl.add(tn);
		return tnl;
	}
	
	
	@Override
	public ThmNodeList caseAClassInvariantDefinition(
			AClassInvariantDefinition inv, ThmVarsContext vars)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		ILexNameToken sName = inv.getName();
		
		String invName = sName.toString().replace("$", ""); 		
		// inv.getClassDefinition().getDefinitions().getFirst().getName().getName();
		//obtain the invariant expression, and dependencies

		String exprString = inv.getExpression().apply(stringVisitor, vars);
		NodeNameList nodeDeps = inv.getExpression().apply(depVisitor, new NodeNameList());

		ThmNode stn = new ThmNode(sName, nodeDeps, new ThmStateInv(invName, exprString));
		tnl.add(stn);
		
		return tnl;
	}
	
	//If an unhandled Definition
	public ThmNodeList defaultPDefinition(PDefinition node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		return tnl;
	}
	
	@Override
	public ThmNodeList defaultPSingleDeclaration(PSingleDeclaration node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		return tnl;
	}
//
//	@Override
//	public ThmNodeList defaultPDefinition(PDefinition node) throws AnalysisException
//	{
//		ThmNodeList tnl = new ThmNodeList();
//		tnl.addAll(node.apply(this));
//		return tnl;
//	}

	// Call the main visitor when it's not a definition/declaration
	@Override
	public ThmNodeList defaultINode(INode node, ThmVarsContext vars)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		tnl.addAll(node.apply(tpVisitor, vars));
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
