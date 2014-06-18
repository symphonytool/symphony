package eu.compassresearch.core.analysis.pog.visitors;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.pub.IProofObligationList;
import org.overture.pog.visitors.PogParamDefinitionVisitor;

import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.analysis.pog.obligations.CmlOperationDefinitionContext;
import eu.compassresearch.core.analysis.pog.utility.ClonerProcessState;

public class PogSpecialDefVisitor extends
		PogParamDefinitionVisitor<IPOContextStack, IProofObligationList>
{

	public PogSpecialDefVisitor(
			QuestionAnswerAdaptor<IPOContextStack, ? extends IProofObligationList> parentVisitor,
			QuestionAnswerAdaptor<IPOContextStack, ? extends IProofObligationList> mainVisitor,
			IPogAssistantFactory assistantFactory)
	{
		super(parentVisitor, mainVisitor, assistantFactory);
	}

	@Override
	protected void collectOpCtxt(AExplicitOperationDefinition node,
			IPOContextStack question, Boolean precond) throws AnalysisException
	{
		AActionProcess stater = node.getAncestor(AActionProcess.class);
		if (stater != null)
		{
			List<AInstanceVariableDefinition> stateDefs = stater.apply(new ClonerProcessState());
			question.push(new CmlOperationDefinitionContext(node, precond, stateDefs));
		} else
		{
			PDefinition stateDef;

			if (node.getClassDefinition() != null)
			{
				stateDef = node.getClassDefinition().clone();
			} else
			{
				stateDef = node.getState();
			}
			question.push(new CmlOperationDefinitionContext(node, precond, stateDef));
		}
	}
	
	

}
