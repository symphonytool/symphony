package eu.compassresearch.core.interpreter;

import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.QuestionCMLAdaptor;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

abstract class AbstractSetupVisitor extends QuestionCMLAdaptor<Context> {

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

	protected void setLeftChild(CmlBehaviour child)
	{
		this.controlAccess.setLeftChild(child);
	}
	
	protected void setRightChild(CmlBehaviour child)
	{
		this.controlAccess.setRightChild(child);
	}
}
