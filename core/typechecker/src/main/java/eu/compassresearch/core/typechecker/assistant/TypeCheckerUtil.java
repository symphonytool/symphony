package eu.compassresearch.core.typechecker.assistant;

import java.util.List;
import java.util.Set;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.ast.util.PTypeSet;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.statement.ABlockSimpleBlockStmAssistantTC;
import org.overture.typechecker.assistant.type.PTypeAssistantTC;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;

public class TypeCheckerUtil
{
	/**
	 * Find a channel, action or chanset in the environment
	 * 
	 * @param env
	 * @param identifier
	 * @return
	 */
	public static PDefinition findDefinition(ILexIdentifierToken identifier,
			Environment env)
	{
		Set<PDefinition> defs = env.findMatches(new eu.compassresearch.ast.lex.CmlLexNameToken("", identifier));

		if (defs.isEmpty())
		{
			return null;
		} else
		{
			return defs.iterator().next();
		}
	}

	/**
	 * Creates the action type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setType(PAction node, PType... types)
	{

		node.setType(generateUnionType(node.getLocation(), types));
		return node.getType();

	}

	/**
	 * Creates the action type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setTypeVoid(PAction node)
	{

		node.setType(AstFactory.newAVoidType(node.getLocation()));
		return node.getType();

	}

	/**
	 * Creates the action type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType getVoidType(PProcess node)
	{
		return AstFactory.newAVoidType(node.getLocation());
	}

	/**
	 * Creates the action type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setType(PAction node, List<PType> types)
	{
		node.setType(generateUnionType(node.getLocation(), types.toArray(new PType[] {})));
		return node.getType();

	}

	/**
	 * Creates the definition type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setType(PDefinition node, List<PType> types)
	{
		node.setType(generateUnionType(node.getLocation(), types.toArray(new PType[] {})));
		return node.getType();
	}

	/**
	 * Creates the PVarsetExpression type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setType(PVarsetExpression node, List<PType> types)
	{

		node.setType(generateUnionType(node.getLocation(), types.toArray(new PType[] {})));
		return node.getType();
	}

	/**
	 * Creates the definition type. This method is based on the simple block used in VDM operations
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType setType(PDefinition node, PType... types)
	{

		node.setType(generateUnionType(node.getLocation(), types));
		return node.getType();

	}

	/**
	 * Creates a untion type of the given types
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType generateUnionType(ILexLocation location,
			List<PType> types)
	{
		return generateUnionType(location, types.toArray(new PType[] {}));
	}

	/**
	 * Creates a untion type of the given types
	 * 
	 * @param node
	 * @param types
	 * @return
	 */
	public static PType generateUnionType(ILexLocation location, PType... types)
	{
		PTypeSet rtypes = new PTypeSet();
		PType last = null;

		for (PType stmt : types)
		{
			PType stype = stmt;

			last = stype;

			if (stype instanceof AUnionType)
			{
				AUnionType ust = (AUnionType) stype;

				for (PType t : ust.getTypes())
				{
					ABlockSimpleBlockStmAssistantTC.addOne(rtypes, t);

				}
			} else
			{
				ABlockSimpleBlockStmAssistantTC.addOne(rtypes, stype);

			}
		}

		// If the last statement reached has a void component, add this to the
		// overall
		// return type, as the block may return nothing.

		if (last != null
				&& (PTypeAssistantTC.isType(last, AVoidType.class) || PTypeAssistantTC.isUnknown(last)))
		{
			rtypes.add(AstFactory.newAVoidType(location));
		}

		return (rtypes.isEmpty() ? AstFactory.newAVoidType(location)
				: rtypes.getType(location));
	}
}
