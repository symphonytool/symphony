package eu.compassresearch.core.analysis.pog.utility;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.process.AActionProcess;

public class ClonerProcessState extends
		AnswerCMLAdaptor<List<AInstanceVariableDefinition>>
{

	@Override
	public List<AInstanceVariableDefinition> createNewReturnValue(INode node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AInstanceVariableDefinition> createNewReturnValue(Object node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AInstanceVariableDefinition> caseAActionProcess(
			AActionProcess node) throws AnalysisException
	{
		return node.getActionDefinition().apply(this);
	}

	@Override
	public List<AInstanceVariableDefinition> caseAActionClassDefinition(
			AActionClassDefinition node) throws AnalysisException
	{
		List<AInstanceVariableDefinition> r = new LinkedList<AInstanceVariableDefinition>();

		for (PDefinition def : node.getDefinitions())
		{
			if (def instanceof AInstanceVariableDefinition)
			{
				r.add((AInstanceVariableDefinition) def.clone());
			}
		}

		return r;

	}

}
