package eu.compassresearch.core.typechecker.weeding;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class Weeding4FixOperationTypes extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp)
	{

		Weeding4FixOperationTypes lv = new Weeding4FixOperationTypes();
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

	private Weeding4FixOperationTypes()
	{
	}

	@Override
	public void caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node) throws AnalysisException
	{
		// Fix: location, type
		node.setLocation(node.getName().getLocation().clone());

		List<PPattern> resultNames = new Vector<PPattern>();
		List<PType> resultTypes = new Vector<PType>();

		for (APatternTypePair plt : node.getResult())
		{
			resultNames.add(plt.getPattern().clone());
			resultTypes.add(plt.getType().clone());
		}

		APatternTypePair resultPattern = null;

		if (resultNames.size() > 1)
		{
			resultPattern = AstFactory.newAPatternTypePair(AstFactory.newATuplePattern(resultNames.get(0).getLocation(), resultNames), AstFactory.newAProductType(resultNames.get(0).getLocation(), resultTypes));
		} else if (!resultNames.isEmpty())
		{
			resultPattern = AstFactory.newAPatternTypePair(resultNames.get(0), resultTypes.get(0));
		}

		List<PType> ptypes = new Vector<PType>();

		for (APatternListTypePair ptp : node.getParameterPatterns())
		{
			ptypes.addAll(getTypeList(ptp));
		}

		AOperationType operationType = AstFactory.newAOperationType(node.getLocation(), ptypes, (node.getResult() == null
				|| node.getResult().isEmpty() ? AstFactory.newAVoidType(node.getName().getLocation())
				: resultPattern.getType()));
		node.setType(operationType);
	}

	private Collection<? extends PType> getTypeList(APatternListTypePair node)
	{
		List<PType> list = new Vector<PType>();

		for (int i = 0; i < node.getPatterns().size(); i++)
		{
			PType type = (PType) node.getType();
			list.add(type);
		}

		return list;
	}
}
