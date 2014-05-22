package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlBehaviour.BehaviourName;
import eu.compassresearch.core.interpreter.api.TransitionEvent;
import eu.compassresearch.core.interpreter.utility.Pair;

/**
 * Handles all setup actions. However, this is not meant to handle the semantic begin rules for actions.
 * 
 * @author akm
 */
abstract class AbstractSetupVisitor extends
		QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>>
{

	/**
	 * The behavior that owns this visitor object
	 */
	protected final CmlBehaviour owner;
	/**
	 * Interface that gives access to methods that control the behavior
	 */
	private final VisitorAccess controlAccess;
	/**
	 * Factory to create new CmlBehavior objects
	 */
	protected final CmlBehaviorFactory cmlBehaviorFactory;

	/**
	 * Evaluator for expressions
	 */
	protected final QuestionAnswerCMLAdaptor<Context, Value> cmlExpressionVisitor = new CmlExpressionVisitor();
	/**
	 * Evaluator for definitions
	 */
	protected final CmlDefinitionVisitor cmlDefEvaluator = new CmlDefinitionVisitor();

	public AbstractSetupVisitor(CmlBehaviour owner,
			VisitorAccess visitorAccess, CmlBehaviorFactory cmlBehaviorFactory)
	{
		this.owner = owner;
		this.controlAccess = visitorAccess;
		this.cmlBehaviorFactory = cmlBehaviorFactory;
	}

	@Override
	public Pair<INode, Context> defaultINode(INode node, Context question)
			throws AnalysisException
	{
		return new Pair<INode, Context>(node, question);
	}

	protected void clearLeftChild()
	{
		this.controlAccess.setLeftChild(null);
	}

	protected void setLeftChild(INode node, BehaviourName name, Context question)
			throws AnalysisException
	{
		this.controlAccess.setLeftChild(cmlBehaviorFactory.newCmlBehaviour(node, this.controlAccess.getChildContexts(question).first, new BehaviourName(name.clone()), owner));
	}

	protected void setLeftChild(INode node, Context question)
			throws AnalysisException
	{
		this.controlAccess.setLeftChild(cmlBehaviorFactory.newCmlBehaviour(node, this.controlAccess.getChildContexts(question).first, owner));
	}

	protected void clearRightChild()
	{
		this.controlAccess.setRightChild(null);
	}

	protected void setRightChild(INode node, BehaviourName name,
			Context question) throws AnalysisException
	{
		this.controlAccess.setRightChild(cmlBehaviorFactory.newCmlBehaviour(node, this.controlAccess.getChildContexts(question).second, name, owner));
	}

	protected void setRightChild(INode node, Context question)
			throws AnalysisException
	{
		this.controlAccess.setRightChild(cmlBehaviorFactory.newCmlBehaviour(node, this.controlAccess.getChildContexts(question).second, owner));
	}

	protected void setChildContexts(Pair<Context, Context> preBuildContexts)
	{
		this.controlAccess.setChildContexts(preBuildContexts);
	}

	protected void newTransitionEvent(TransitionEvent event)
	{
		this.controlAccess.newTransitionEvent(event);
	}

	protected Pair<Context, Context> getChildContexts(Context context)
	{
		return this.controlAccess.getChildContexts(context);
	}

	@Override
	public Pair<INode, Context> createNewReturnValue(INode node,
			Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<INode, Context> createNewReturnValue(Object node,
			Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
