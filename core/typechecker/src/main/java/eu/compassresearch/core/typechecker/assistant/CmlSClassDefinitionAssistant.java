package eu.compassresearch.core.typechecker.assistant;

import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexNameToken;

public class CmlSClassDefinitionAssistant extends SClassDefinitionAssistantTC
{

	public CmlSClassDefinitionAssistant(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public void typeCheckPass(SClassDefinition c, Pass p, Environment base,
			QuestionAnswerAdaptor<TypeCheckInfo, PType> tc)
			throws AnalysisException
	{

		Environment env = updateActionEnvironment(c, base);

		super.typeCheckPass(c, p, env, tc);
	}

	public static Environment updateActionEnvironment(SClassDefinition c,
			Environment base)
	{
		Environment env = base;
		if (c instanceof AActionClassDefinition)
		{
			AProcessDefinition process = c.getAncestor(AProcessDefinition.class);

			List<PDefinition> definitions = new Vector<PDefinition>();

			if (process != null && !process.getLocalState().isEmpty())
			{
				for (ATypeSingleDeclaration tDecl : process.getLocalState())
				{
					for (ILexIdentifierToken id : tDecl.getIdentifiers())
					{
						ILexNameToken name = new LexNameToken("", id);
						PExp exp = AstFactory.newAUndefinedExp(name.getLocation());
						AAssignmentDefinition def = AstFactory.newAAssignmentDefinition(name, tDecl.getType(), exp);
						AInstanceVariableDefinition ivd = AstFactory.newAInstanceVariableDefinition(def.getName(), def.getType(), def.getExpression());
						def.getType().parent(ivd);
						definitions.add(ivd);
					}
				}
				env = new FlatCheckedEnvironment(af, definitions, base, NameScope.NAMES);
			}
		}
		return env;
	}
}
