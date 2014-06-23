package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionDefinition;

public class CmlDefinitionTypeResolver extends
		AbstractCmlDefinitionTypeResolver
{

	public CmlDefinitionTypeResolver(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}
	
	
	@Override
	public void caseAActionDefinition(AActionDefinition node,
			NewQuestion question) throws AnalysisException
	{
		if (!node.getDeclarations().isEmpty())
		{
			for (PParametrisation par : node.getDeclarations())
			{
				try
				{
					af.createPTypeAssistant().typeResolve(par.getDeclaration().getType(), null, question.rootVisitor, question.question);
				} catch (TypeCheckException te)
				{
					TypeChecker.report(3427, te.getMessage(), te.location);
				}
			}
		}
	}

}
