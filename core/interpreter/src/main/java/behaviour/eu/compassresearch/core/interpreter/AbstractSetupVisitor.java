package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;
/**
 * Handles all setup actions. However, this is not meant to handle the semantic 
 * begin rules for actions.
 * @author akm
 *
 */
abstract class AbstractSetupVisitor extends QuestionAnswerCMLAdaptor<Context,INode> {

	//Interface that gives access to methods that control the behaviour
	private final VisitorAccess 								controlAccess;
	protected final CmlBehaviour 								owner;								
	//Evaluator for expressions and definitions
	protected final QuestionAnswerCMLAdaptor<Context, Value>	cmlExpressionVisitor = new CmlExpressionVisitor();
	protected final CmlDefinitionVisitor						cmlDefEvaluator = new CmlDefinitionVisitor();

	public AbstractSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		this.owner = owner;
		this.controlAccess = visitorAccess;
				
	}
	
	@Override
	public INode defaultINode(INode node, Context question)
			throws AnalysisException {
		return node;
	}

	protected void setLeftChild(CmlBehaviour child)
	{
		this.controlAccess.setLeftChild(child);
	}
	
	protected void setRightChild(CmlBehaviour child)
	{
		this.controlAccess.setRightChild(child);
	}
	
	protected void setChildContexts(Pair<Context,Context> preBuildContexts)
	{
		this.controlAccess.setChildContexts(preBuildContexts);
	}
}
