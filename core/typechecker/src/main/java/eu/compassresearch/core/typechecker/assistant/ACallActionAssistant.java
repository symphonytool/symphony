package eu.compassresearch.core.typechecker.assistant;

import java.util.List;

import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.ACallAction;

public class ACallActionAssistant
{
	public static void checkArgTypes(ACallAction node, PType type,
			List<PType> ptypes, List<PType> atypes)
	{
		if (ptypes.size() != atypes.size())
		{
			TypeCheckerErrors.report(3216, "Expecting " + ptypes.size()
					+ " arguments", node.getLocation(), node);
		} else
		{
			int i = 0;

			for (PType atype : atypes)
			{
				PType ptype = ptypes.get(i++);

				if (!TypeComparator.compatible(ptype, atype))
				{
					TypeCheckerErrors.report(3217, "Unexpected type for argument "
							+ i, node.getLocation(), type);
					TypeCheckerErrors.detail2("Expected", ptype, "Actual", atype);
				}
			}
		}
	}
}
