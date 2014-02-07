package eu.compassresearch.core.typechecker.weeding;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.core.typechecker.DefinitionList;

/**
 * @author kel & cb
 */
public class Weeding5RemoveInitialDefinitions extends
		DepthFirstAnalysisCMLAdaptor
{

	public static void apply(DefinitionList sourceForest)
	{

		Weeding5RemoveInitialDefinitions lv = new Weeding5RemoveInitialDefinitions();
		for (PDefinition s : sourceForest)
		{
			if (s != null)
			{
				try
				{
					s.apply(lv);
				} catch (AnalysisException e)
				{
				}
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