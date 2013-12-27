package eu.compassresearch.core.typechecker.analysis;

import java.util.Collection;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.DefinitionList;

public class CollectGlobalStateClass extends AnalysisCMLAdaptor
{

	private final Collection<PDefinition> globalDefinitions = new Vector<PDefinition>();

	public static DefinitionList getGlobalRoot(DefinitionList sourceForest)
			throws AnalysisException
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

	@Override
	public void defaultPDefinition(PDefinition node) throws AnalysisException
	{
		globalDefinitions.add(node);
	}

}
