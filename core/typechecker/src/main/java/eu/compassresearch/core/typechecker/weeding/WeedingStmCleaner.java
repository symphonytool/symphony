package eu.compassresearch.core.typechecker.weeding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class WeedingStmCleaner extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		WeedingStmCleaner lv = new WeedingStmCleaner();
		for (PSource s : lp)
		{
			if (s != null)
				try
				{
					s.apply(lv);
				} catch (AnalysisException e)
				{
				}
		}
	}

	private WeedingStmCleaner()
	{
	}

	@Override
	public void caseAStmAction(AStmAction node) throws AnalysisException
	{
		node.getStatement().apply(this);
		if (node.parent() instanceof AActionStm)
		{
			node.parent().parent().replaceChild(node.parent(), node.getStatement());
			node.parent().parent(null);
			node.setStatement(null);
		}
	}

	@Override
	public void caseAActionStm(AActionStm node) throws AnalysisException
	{
		super.caseAActionStm(node);
		if (node.getAction() == null)
		{
			// discard empty wrapper
			node.parent().removeChild(node);
		}

	}

	// @Override
	// public void caseASkipAction(ASkipAction node) throws AnalysisException
	// {
	// INode grandParent = node.parent().parent();
	// if ((grandParent instanceof PStm || grandParent instanceof ACaseAlternativeStm || grandParent instanceof
	// SOperationDefinition)&&node.parent() instanceof AActionStm)
	// {
	// grandParent.replaceChild(node.parent(), AstFactory.newASkipStm(node.getLocation()));
	// }else if(node.getAncestor(SOperationDefinition.class)!=null)
	// {
	// node.parent().replaceChild(node, new AStmAction(node.getLocation(), AstFactory.newASkipStm(node.getLocation())));
	// }
	// }

	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException
	{

		if (node.getAncestor(SOperationDefinition.class) != null)
		{
			List<PStm> statements = new Vector<PStm>();
			statements.add(getStatement(node.getLeft()));

			INode tmp = node.getRight();
			while (tmp instanceof ASequentialCompositionAction)
			{
				ASequentialCompositionAction s = (ASequentialCompositionAction) tmp;

				statements.add(getStatement(s.getLeft()));
				tmp = s.getRight();
			}

			// tmp.apply(this);
			// tmp = tmp.parent();

			statements.add(getStatement((PAction) tmp));

			ABlockSimpleBlockStm block = AstFactory.newABlockSimpleBlockStm(node.getLocation(), new ArrayList<AAssignmentDefinition>());
			block.setStatements(statements);

			node.parent().parent().replaceChild(node.parent(), block);
			node.parent(null);

			block.parent().apply(this);

			// System.out.println("rewrite to block");
		}

		// visit all in block
	}

	private PStm getStatement(PAction node) throws AnalysisException
	{
		node.apply(this);
		return ((AStmAction) node).getStatement();
	}

}