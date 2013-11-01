package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.ACaseAlternativeStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class WeedingSkipActionToStmCleaner extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		WeedingSkipActionToStmCleaner lv = new WeedingSkipActionToStmCleaner();
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

	private WeedingSkipActionToStmCleaner()
	{
	}

	@SuppressWarnings("deprecation")
	@Override
	public void caseASkipAction(ASkipAction node) throws AnalysisException
	{
		INode grandParent = node.parent().parent();
		if ((grandParent instanceof PStm
				|| grandParent instanceof ACaseAlternativeStm || grandParent instanceof SOperationDefinition)
				&& node.parent() instanceof AActionStm)
		{
			grandParent.replaceChild(node.parent(), AstFactory.newASkipStm(node.getLocation()));
		} else if (node.getAncestor(SOperationDefinition.class) != null)
		{
			node.parent().replaceChild(node, new AStmAction(node.getLocation(), AstFactory.newASkipStm(node.getLocation())));
		}
	}

}