package eu.compassresearch.theoremprover.visitors;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.theoremprover.thms.ThmExpFunc;
import eu.compassresearch.theoremprover.thms.ThmImpFunc;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.thms.ThmProcStand;
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
		//Find bound values to exclude from dependency list
		LinkedList<ILexNameToken> b = new LinkedList<ILexNameToken>();
		for(PPattern p : params.getFirst() )
		{
			b.add(((AIdentifierPattern) p).getName());
		}
		//add the parameter types as dependencies
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
		
		if (node.getProcess() instanceof AActionProcess)
		{
			AActionProcess act = (AActionProcess) node.getProcess();
			tn = ThmProcessUtil.getIsabelleActionProcess(node.getName(), act);
		}
		else
		{
			//get the Isabelle string for the process node's process.
			String procString = ThmProcessUtil.getIsabelleProcessString(node.getProcess());
			//obtain the process dependencies
			LinkedList<ILexNameToken> nodeDeps = ThmProcessUtil.getIsabelleProcessDeps(node.getProcess());
			//create and return the theorem node.			
			tn =  new ThmNode(node.getName(), nodeDeps, new ThmProcStand(node.getName().toString(), procString));
		}
		
		//add the process node to the node list
		tnl.add(tn);

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
//
//	@Override
//	public ThmNodeList defaultPDefinition(PDefinition node) throws AnalysisException
//	{
//		ThmNodeList tnl = new ThmNodeList();
//		tnl.addAll(node.apply(parentVisitor));
//		return tnl;
//	}

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
