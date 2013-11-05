package eu.compassresearch.core.analysis.pog.utility;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.process.AActionProcess;

public class ProcessStateCloner extends AnswerCMLAdaptor<List<AAssignmentDefinition>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<AAssignmentDefinition> createNewReturnValue(INode node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AAssignmentDefinition> createNewReturnValue(Object node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AAssignmentDefinition> caseAActionProcess(AActionProcess node)
			throws AnalysisException
	{
		List<AAssignmentDefinition> r = new LinkedList<AAssignmentDefinition>();
		// Unpack it and look for the state def
		for (PDefinition def : node.getActionDefinition().getDefinitions())
		{
			if (def instanceof AStateDefinition)
			{
				r.addAll(def.apply(this));
			}
		}

		return r;
	}

	@Override
	public List<AAssignmentDefinition> caseAStateDefinition(AStateDefinition node)
			throws AnalysisException
	{
		List<AAssignmentDefinition> r = new LinkedList<AAssignmentDefinition>();
		
		for (PDefinition def : node.getStateDefs()){
			if (def instanceof AAssignmentDefinition){
				r.add((AAssignmentDefinition)def.clone());
			}
		}
		
		return r;
	}

}
