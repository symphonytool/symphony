package eu.compassresearch.core.typechecker.assistant;

import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelBlockDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetBlockDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AConfigDefinition;
import eu.compassresearch.ast.definitions.AFunctionBlockDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypeBlockDefinition;
import eu.compassresearch.ast.definitions.AValueBlockDefinition;

public class CmlDefinitionCollector extends AbstractCmlDefinitionCollector
{


	public CmlDefinitionCollector(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	private List<PDefinition> addSelf(PDefinition node)
	{
		List<PDefinition> defs = new Vector<PDefinition>();
		defs.add(node);
		return defs;
	}

	@Override
	public List<PDefinition> caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{
		return addSelf(node);
	}

	@Override
	public List<PDefinition> caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{
		return addSelf(node);
	}

	@Override
	public List<PDefinition> caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		return addSelf(node);
	}

	@Override
	public List<PDefinition> caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		return addSelf(node);
	}
	
	@Override
	public List<PDefinition> caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{
		return addSelf(node);
	}

	@Override
	public List<PDefinition> caseAConfigDefinition(AConfigDefinition node)
			throws AnalysisException {
		return new Vector<PDefinition>();
	}

	@Override
	public List<PDefinition> caseATypeBlockDefinition(ATypeBlockDefinition node)
			throws AnalysisException {
		return new Vector<PDefinition>();
	}

	@Override
	public List<PDefinition> caseAChannelBlockDefinition(
			AChannelBlockDefinition node) throws AnalysisException {
		return new Vector<PDefinition>();
	}

	@Override
	public List<PDefinition> caseAChansetBlockDefinition(
			AChansetBlockDefinition node) throws AnalysisException {
		return new Vector<PDefinition>();
	}

	@Override
	public List<PDefinition> caseAFunctionBlockDefinition(
			AFunctionBlockDefinition node) throws AnalysisException {
		return new Vector<PDefinition>();
	}

	@Override
	public List<PDefinition> caseAValueBlockDefinition(
			AValueBlockDefinition node) throws AnalysisException {
		return new Vector<PDefinition>();
	}
}
