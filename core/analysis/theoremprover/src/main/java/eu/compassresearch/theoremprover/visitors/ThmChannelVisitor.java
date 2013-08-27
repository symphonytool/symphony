package eu.compassresearch.theoremprover.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.theoremprover.thms.ThmChannel;
import eu.compassresearch.theoremprover.thms.ThmChanset;
import eu.compassresearch.theoremprover.thms.ThmNode;
import eu.compassresearch.theoremprover.thms.ThmNodeList;
import eu.compassresearch.theoremprover.utils.ThmChanUtil;
import eu.compassresearch.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

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
		PDefinition chan = chandefs.getFirst();
		name = chan.getName();
		type = ThmTypeUtil.getIsabelleType(((AChannelType) chan.getType()).getType());
		//Generate Channel syntax
		LinkedList<ILexNameToken> nodeDeps = ThmChanUtil.getIsabelleChanDeps(node);

		ThmNode tn = new ThmNode(name, nodeDeps, new ThmChannel(name.toString(), type));
		tnl.add(tn);
		
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
		
		LinkedList<ILexNameToken> nodeDeps = ThmExprUtil.getIsabelleVarsetExprDeps(chExpr);


		ThmNode tn = new ThmNode(name, nodeDeps, new ThmChanset(name.toString(), expr));
		tnl.add(tn);
		
		return tnl;
	}
}
