package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.PONameContext;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;

public class PogNameContextVisitor extends AnswerCMLAdaptor<PONameContext>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//FIXME Add/Incorporate Overture Assistants
	
	@Override
	public PONameContext defaultPDefinition(PDefinition node) throws AnalysisException
	{
		return new PONameContext(PDefinitionAssistantTC.getVariableNames(node));
	}
	
	//FIXME Do we need a name handler for friggin' processes?

	@Override
	public PONameContext caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		return new PONameContext(new LexNameList(node.getName()));
	}

	@Override
	public PONameContext caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		return new PONameContext(new LexNameList(node.getName()));
	}
	
	@Override
	public PONameContext caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node) throws AnalysisException
	{
		return new PONameContext(new LexNameList(node.getName()));
	}

	@Override
	public PONameContext caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node) throws AnalysisException
	{
		return new PONameContext(new LexNameList(node.getName()));
	}

	
	
	@Override
	public PONameContext defaultPCMLDefinition(PCMLDefinition node)
			throws AnalysisException
	{
		// no names to return
		return null;
	}

	@Override
	public PONameContext createNewReturnValue(INode node)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PONameContext createNewReturnValue(Object node)
	{
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
