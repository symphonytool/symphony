package eu.compassresearch.core.analysis.theoremprover.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChannel;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChanset;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class ThmChannelVisitor extends QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList>
{
	private ThmDepVisitor depVisitor;
	private ThmStringVisitor stringVisitor;
	
	
	public ThmChannelVisitor(QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> parent, ThmDepVisitor depVis, ThmStringVisitor stringVis)
	{
		depVisitor = depVis;
		stringVisitor = stringVis;
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
		String type = "";
		
		name = node.getName();
		PType chanType = node.getType();
		if (chanType != null)
		{
			type = chanType.apply(stringVisitor, new ThmVarsContext()); //ThmTypeUtil.getIsabelleType(((AChannelType) chan.getType()).getType());
		}
		NodeNameList nodeDeps = node.apply(depVisitor, new NodeNameList());//ThmChanUtil.getIsabelleChanDeps(node);
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
