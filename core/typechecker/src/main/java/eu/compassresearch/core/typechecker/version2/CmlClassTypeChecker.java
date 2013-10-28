package eu.compassresearch.core.typechecker.version2;

import java.util.List;

import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.typechecker.ClassTypeChecker;
import org.overture.typechecker.Environment;
import org.overture.typechecker.PublicClassEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.core.typechecker.CmlTypeCheckerAssistantFactory;
import eu.compassresearch.core.typechecker.FlatCheckedGlobalEnvironment;

public class CmlClassTypeChecker extends ClassTypeChecker
{

	private List<PDefinition> globalDefs;

	public CmlClassTypeChecker(List<SClassDefinition> classes,
			List<PDefinition> globalDefs)
	{
		super(classes, new CmlTypeCheckerAssistantFactory());
		this.globalDefs = globalDefs;
	}

	@Override
	public QuestionAnswerAdaptor<TypeCheckInfo, PType> getTypeCheckVisitor()
	{
		return new CmlVdmTypeCheckVisitor();
	}

	@Override
	public Environment getAllClassesEnvronment()
	{
		Environment globalEnv = new FlatCheckedGlobalEnvironment(assistantFactory, globalDefs, NameScope.NAMESANDSTATE, null);
		Environment allClasses = new PublicClassEnvironment(assistantFactory, classes, globalEnv, null);
		return allClasses;
	}

	public ITypeCheckerAssistantFactory getAssistantFactory()
	{
		return this.assistantFactory;
	}

}
