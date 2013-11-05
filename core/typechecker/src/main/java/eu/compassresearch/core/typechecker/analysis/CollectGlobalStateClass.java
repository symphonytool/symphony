package eu.compassresearch.core.typechecker.analysis;

import java.util.Collection;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.DefinitionList;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

@SuppressWarnings("serial")
public class CollectGlobalStateClass extends AnalysisCMLAdaptor
{

	private final Collection<PDefinition> globalDefinitions = new Vector<PDefinition>();

	public static DefinitionList getGlobalRoot(DefinitionList sourceForest,
			ITypeIssueHandler issueHandler) throws AnalysisException
	{

		CollectGlobalStateClass me = new CollectGlobalStateClass();
		for (PDefinition source : sourceForest)
		{
			source.apply(me);
		}

		// this sort is just to make it easier to check since the order makes the definitions checked in the order they
		// are needed
		final DefinitionList sortedGlobalDefinitions = new DefinitionList();

		for (PDefinition def : me.globalDefinitions)
		{
			if (def instanceof ATypeDefinition)
			{
				sortedGlobalDefinitions.add(def);
			}
		}

		for (PDefinition def : me.globalDefinitions)
		{
			if (def instanceof AChannelDefinition)
			{
				sortedGlobalDefinitions.add(def);
			}
		}

		for (PDefinition def : me.globalDefinitions)
		{
			if (def instanceof AChansetDefinition)
			{
				sortedGlobalDefinitions.add(def);
			}
		}

		for (PDefinition def : me.globalDefinitions)
		{
			if (!sortedGlobalDefinitions.contains(def))
			{
				sortedGlobalDefinitions.add(def);
			}
		}

		// That's it
		return sortedGlobalDefinitions;// new GlobalDefinitions(members, channels);
	}

	@Override
	public void defaultPSource(PSource node) throws AnalysisException
	{
		for (PDefinition paragraph : node.getParagraphs())
		{
			paragraph.apply(this);
		}
	}

	// | {channels}
	// [channelDeclarations]: definition.channel*
	// | {chansets}
	// [chansets] : definition.chanset*
	// | {namesets}
	// [namesets] : definition.nameset*
	// | {actions}
	// [actions]: definition.action*
	// | {types}
	// [types]: definition.type*
	// | {operations}
	// [operations]:definition.#Operation*
	// | {functions}
	// (functionDefinitions):definition*

	@Override
	public void defaultPDefinition(PDefinition node) throws AnalysisException
	{
		globalDefinitions.add(node);
	}

	@Override
	public void caseAChannelsDefinition(AChannelsDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getChannelDeclarations())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getTypes())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getValueDefinitions())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseAChansetsDefinition(AChansetsDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getChansets())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getFunctionDefinitions())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseAOperationsDefinition(AOperationsDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getOperations())
		{
			def.apply(this);
		}
	}

	@Override
	public void caseAActionsDefinition(AActionsDefinition node)
			throws AnalysisException
	{
		for (PDefinition def : node.getActions())
		{
			def.apply(this);
		}
	}

}
