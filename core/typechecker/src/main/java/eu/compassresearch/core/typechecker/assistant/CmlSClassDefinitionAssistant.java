package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

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

		Environment env = PParametrisationAssistant.updateEnvironment(tc,base, c);

		super.typeCheckPass(c, p, env, tc);
	}

}
