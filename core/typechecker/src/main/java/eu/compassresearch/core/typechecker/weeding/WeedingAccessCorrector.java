package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.node.tokens.TStatic;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class WeedingAccessCorrector extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		WeedingAccessCorrector lv = new WeedingAccessCorrector();
		for (PSource s : lp)
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

	private WeedingAccessCorrector()
	{
	}

	@Override
	public void caseAValueDefinition(AValueDefinition node)
			throws AnalysisException
	{
		node.getAccess().setAccess(new APublicAccess());
	}

	@Override
	public void caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException
	{
		if (!(node.getAccess().getAccess() instanceof APublicAccess))
		{
			// TODO when getAccess gets a location then report it here. We also need it to have a token so we
			// can see the difference from non parse from default
			// issueHandler.addTypeWarning(tdef, "Access specifier ignored. Global types are default public.");
		}
		node.getAccess().setAccess(new APublicAccess());
	}

	@Override
	public void caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException
	{
		defaultSFunctionDefinition(node);
	}

	@Override
	public void caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node)
			throws AnalysisException
	{
		defaultSFunctionDefinition(node);
	}

	@Override
	public void defaultSFunctionDefinition(SFunctionDefinition node)
			throws AnalysisException
	{
		node.getAccess().setAccess(new APublicAccess());
		node.getAccess().setStatic(new TStatic());
	}

}