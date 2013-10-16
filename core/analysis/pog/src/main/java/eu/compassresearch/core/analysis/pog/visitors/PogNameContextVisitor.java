package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.utility.POException;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.lex.LexNameToken;

public class PogNameContextVisitor extends AnswerCMLAdaptor<PONameContext>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//FIXME Add/Incorporate Overture Assistants
	
	IPogAssistantFactory af;
	
	
	
	
	public PogNameContextVisitor()
	{
		af=new CmlPogAssistantFactory();
	}

	@Override
	public PONameContext defaultPDefinition(PDefinition node) throws AnalysisException
	{
		return new PONameContext(node.apply(af.getVariableNameCollector()));
	}
	
	//Need to code around process state since it has no name
	
	@Override
	public PONameContext caseAStateDefinition(AStateDefinition node)
			throws AnalysisException
	{
		LexNameList variableNames = new LexNameList();
		
		for (PDefinition d : node.getStateDefs())
		{
			if (d instanceof AClassInvariantDefinition){
				AProcessDefinition apd= node.getAncestor(AProcessDefinition.class);
				if (apd==null)
					throw new	POException(d, "Could not locate respective process definition");
				variableNames.add(new LexNameToken("", apd.getName().getName()+"State", d.getLocation().clone()));		
			}
			else 
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
