package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.ABracketType;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * 
 * 
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding2 extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding2 lv = new Weeding2();
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

	private Weeding2()
	{
	}

	
	@Override
	public void caseABracketType(ABracketType node) throws AnalysisException
	{
		node.parent().replaceChild(node, node.getType());
		node.getType().apply(this);
	}
}
