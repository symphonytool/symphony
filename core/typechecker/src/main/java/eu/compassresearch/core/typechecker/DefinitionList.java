package eu.compassresearch.core.typechecker;

import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SFunctionDefinition;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;

public class DefinitionList extends Vector<PDefinition>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void apply(IAnalysis analysis) throws AnalysisException
	{
		for (PDefinition def : this)
		{
			def.apply(analysis);
		}
	}

	public List<SClassDefinition> getAllClasses()
	{

		final List<SClassDefinition> classes = new Vector<SClassDefinition>();
		// for (PSource pSource : globalDefs2)
		// {
		try
		{
			this.apply(new DepthFirstAnalysisCMLAdaptor()
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void caseAClassClassDefinition(AClassClassDefinition node)
						throws AnalysisException
				{
					classes.add(node);
				}

				@Override
				public void caseAActionClassDefinition(
						AActionClassDefinition node) throws AnalysisException
				{
					classes.add(node);
				}
			});
		} catch (AnalysisException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		return classes;
	}

	public List<PDefinition> getGlobalVdmDefinitions()// filterCSP(List<PDefinition> definitions)
	{
		final List<PDefinition> globalDefs = new Vector<PDefinition>();
		for (PDefinition def : this)
		{
			if (def instanceof SFunctionDefinition
					|| def instanceof AInstanceVariableDefinition
					|| def instanceof AValueDefinition
					|| def instanceof ATypeDefinition)
			{
				globalDefs.add(def);
			} else if (def instanceof SClassDefinition)
			{
				// globalClasses.add((SClassDefinition) def);
			}
		}

		return globalDefs;
	}

}
