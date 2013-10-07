package eu.compassresearch.core.analysis.theoremprover.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChannel;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmChanset;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmChanUtil;

@SuppressWarnings("serial")
public class ThmChannelVisitor extends AnswerCMLAdaptor<ThmNodeList>
{
	public ThmChannelVisitor(AnswerCMLAdaptor<ThmNodeList> parent)
	{
	}

	/**
	 * CML channel definition 
	 */
	@Override
	public ThmNodeList caseAChannelNameDefinition(AChannelNameDefinition node)
			throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		ILexNameToken name = null;
		String type = "";

		LinkedList<PDefinition> chandefs = node.getSingleType().getType().getDefinitions();
		
		for(PDefinition chan : chandefs)
		{
			//Generate Channel syntax
			name = chan.getName();
			type = ThmTypeUtil.getIsabelleType(((AChannelType) chan.getType()).getType());
			NodeNameList nodeDeps = ThmChanUtil.getIsabelleChanDeps(node);

			ThmNode tn = new ThmNode(name, nodeDeps, new ThmChannel(name.toString(), type));
			tnl.add(tn);
		}
		
		return tnl;
	}

	/**
	 * CML chanset definition
	 */
	@Override
	public ThmNodeList caseAChansetDefinition(AChansetDefinition node) throws AnalysisException
	{
		ThmNodeList tnl = new ThmNodeList();
		//TODO: FIX THIS ONCE RESOLVED
		LexNameToken name = new LexNameToken("", node.getIdentifier().toString(), node.getIdentifier().getLocation());
					

		PVarsetExpression chExpr = node.getChansetExpression();
		String expr = ThmExprUtil.getIsabelleVarsetExpr(chExpr);
		
		NodeNameList nodeDeps = ThmExprUtil.getIsabelleVarsetExprDeps(chExpr);


		ThmNode tn = new ThmNode(name, nodeDeps, new ThmChanset(name.toString(), expr));
		tnl.add(tn);
		
		return tnl;
	}

	@Override
	public ThmNodeList createNewReturnValue(INode node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThmNodeList createNewReturnValue(Object node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
