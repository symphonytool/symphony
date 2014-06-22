package eu.compassresearch.core.typechecker.assistant;

import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.process.PProcess;

public class AReferenceAssistant
{
	public static void checkArgTypes(AReferenceAction node, PType type,
			List<PType> ptypes, List<PType> atypes)
	{
		checkArgTypes(node, node.getLocation(), type, ptypes, atypes);
	}

	public static void checkArgTypes(PProcess node, PType type,
			List<PType> ptypes, List<PType> atypes)
	{
		checkArgTypes(node, node.getLocation(), type, ptypes, atypes);
	}

	public static void checkArgTypes(INode node, ILexLocation location,
			PType type, List<PType> ptypes, List<PType> atypes)
	{
		if (ptypes.size() != atypes.size())
		{
			TypeCheckerErrors.report(3216, "Expecting " + ptypes.size()
					+ " arguments", location, node);
		} else
		{
			int i = 0;

			for (PType atype : atypes)
			{
				PType ptype = ptypes.get(i++);

				if (!TypeComparator.compatible(ptype, atype))
				{
					TypeCheckerErrors.report(3217, "Unexpected type for argument "
							+ i, location, type);
					TypeCheckerErrors.detail2("Expected", ptype, "Actual", atype);
				}
			}
		}
	}

}
