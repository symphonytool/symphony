package eu.compassresearch.core.analysis.pog.utility;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.utility.POException;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.analysis.pog.visitors.CmlPogAssistantFactory;

public class MakerNameContexts extends AnswerCMLAdaptor<PONameContext>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IPogAssistantFactory af;

	public MakerNameContexts()
	{
		af = new CmlPogAssistantFactory();
	}

	@Override
	public PONameContext defaultPDefinition(PDefinition node)
			throws AnalysisException
	{
		return new PONameContext(node.apply(af.getVariableNameCollector()));
	}

	// Need to code around process state since it has no name

	@Override
	public PONameContext caseAStateDefinition(AStateDefinition node)
			throws AnalysisException
	{
		LexNameList variableNames = new LexNameList();

		for (PDefinition d : node.getStateDefs())
		{
			if (d instanceof AClassInvariantDefinition)
			{
				AProcessDefinition apd = node.getAncestor(AProcessDefinition.class);
				if (apd == null)
					throw new POException(d, "Could not locate respective process definition");
				variableNames.add(new CmlLexNameToken("", apd.getName().getName()
						+ "State", d.getLocation().clone()));
			} else
			{

				variableNames.addAll(d.apply(af.getVariableNameCollector()));
			}
		}
		return new PONameContext(variableNames);
	}

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
	public PONameContext caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node) throws AnalysisException
	{
		return new PONameContext(new LexNameList(node.getName()));
	}

	@Override
	public PONameContext caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node) throws AnalysisException
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
