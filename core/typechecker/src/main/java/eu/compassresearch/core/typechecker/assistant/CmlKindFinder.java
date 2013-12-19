package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelBlockDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetBlockDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AConfigDefinition;
import eu.compassresearch.ast.definitions.AFunctionBlockDefinition;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypeBlockDefinition;
import eu.compassresearch.ast.definitions.AValueBlockDefinition;

public class CmlKindFinder extends AbstractCmlKindFinder
{


	public CmlKindFinder(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	// = {action}
	// | {chanset}
	// | {nameset}
	// | {process}
	// | {channel}//renamed removed Name
	// | {channels}
	// | {chansets}
	// | {namesets}
	// | {actions}
	// | {types}
	// | {operations}
	// | {functions}
	// | {values}
	// | {initial}
	// | {state}

	@Override
	public String caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		return "action";
	}

	@Override
	public String caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{
		return "chanset";
	}

	@Override
	public String caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{
		return "nameset";
	}

	@Override
	public String caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		return "process";
	}

	@Override
	public String caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{
		return "channel";
	}

	@Override
	public String caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException
	{
		return "initial";
	}

	@Override
	public String caseAStateDefinition(AStateDefinition node)
			throws AnalysisException
	{
		return "state";
	}

	@Override
	public String caseAConfigDefinition(AConfigDefinition node)
			throws AnalysisException {
		return "config";
	}

	@Override
	public String caseATypeBlockDefinition(ATypeBlockDefinition node)
			throws AnalysisException {
		return null;
	}

	@Override
	public String caseAChannelBlockDefinition(AChannelBlockDefinition node)
			throws AnalysisException {
		return null;
	}

	@Override
	public String caseAChansetBlockDefinition(AChansetBlockDefinition node)
			throws AnalysisException {
		return null;
	}

	@Override
	public String caseAFunctionBlockDefinition(AFunctionBlockDefinition node)
			throws AnalysisException {
		return null;
	}

	@Override
	public String caseAValueBlockDefinition(AValueBlockDefinition node)
			throws AnalysisException {
		return null;
	}
	
}
