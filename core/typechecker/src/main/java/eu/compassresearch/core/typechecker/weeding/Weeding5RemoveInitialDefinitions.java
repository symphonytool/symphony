package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SClassDefinition;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding5RemoveInitialDefinitions extends
		DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding5RemoveInitialDefinitions lv = new Weeding5RemoveInitialDefinitions();
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

	private Weeding5RemoveInitialDefinitions()
	{
	}

	@Override
	public void caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException
	{
		if (node.parent() instanceof SClassDefinition)
		{
			node.parent().replaceChild(node, node.getOperationDefinition());
		}
	}

}