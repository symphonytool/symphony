package eu.compassresearch.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.lex.CmlLexNameToken;

@SuppressWarnings("deprecation")
public class CmlAstFactory extends AstFactory
{
	static int actionClassIndex = 0;

	public static AActionClassDefinition newAActionClassDefinition(
			ILexLocation loc, List<PDefinition> members)
	{
		ILexNameToken className = new CmlLexNameToken("", "$actionClass"
				+ actionClassIndex++, loc);
		List<? extends ILexNameToken> superclasses = new ArrayList<ILexNameToken>();

		AActionClassDefinition result = new AActionClassDefinition();
		initClassDefinition(result, className, superclasses, members);
		// result.setActionDesinitions(actionDefs);

		return result;
	}

	public static AActionDefinition newAActionDefinition(
			ILexIdentifierToken identifier, PAction action)
	{
		AActionDefinition adef = new AActionDefinition();
		adef.setName(new CmlLexNameToken("", identifier));
		adef.setAction(action);
		adef.setLocation(identifier.getLocation());
		// adef.setDeclarations(parametrisationList);
		return adef;
	}
	
	public static ASkipAction newASkipAction(ILexLocation loc)
	{
		ASkipAction skip = new ASkipAction();
		skip.setLocation(loc);
		skip.setType(AstFactory.newAVoidType(loc));
		return skip;
	}
	
	

	public static Collection<? extends PSingleDeclaration> generateSingleTypeDeclerations(
			ILexLocation loc, List<LexIdentifierToken> list, PType type,
			PExp exp)
	{
		List<PSingleDeclaration> rdecls = new Vector<PSingleDeclaration>();
		for (ILexIdentifierToken id : list)
		{
			if (type != null)
			{

				rdecls.add(new ATypeSingleDeclaration(loc, NameScope.GLOBAL, id, type));

			} else
			{
				rdecls.add(new AExpressionSingleDeclaration(loc, NameScope.GLOBAL, id, exp.clone()));
			}
		}
		return rdecls;
	}

	public static AFatEnumVarsetExpression newAFatEnumVarsetExpression(
			ILexLocation location, List<ANameChannelExp> channelNames)
	{
		return new AFatEnumVarsetExpression(location, channelNames);
	}

	public static ANameChannelExp newANameChannelExp(ILexLocation location,
			ILexNameToken identifier, LinkedList<PExp> expressions)
	{
		return new ANameChannelExp(location, identifier, expressions);
	}

	public static AActionDefinition newAActionDefinition(ILexLocation location,
			NameScope local, boolean used, AAccessSpecifierAccessSpecifier access, Pass pass,
			List<? extends PParametrisation> declarations_, PAction action)
	{
		return new AActionDefinition(location,local,used,access,pass,declarations_,action);
	}
}
