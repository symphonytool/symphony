package eu.compassresearch.core.interpreter.eval;

import java.util.List;
import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;

public abstract class AbstractEvaluationVisitor extends QuestionAnswerCMLAdaptor<Context, CmlBehaviourSignal> {

	public interface ControlAccess
	{
		CmlBehaviourThread ownerThread();
		void pushNext(INode node,Context context);
		CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child);
		void addChild(CmlBehaviourThread child);
		void removeTheChildren();
		CmlBehaviourThread createChild(INode node, Context question, LexNameToken name);
		void mergeState(CmlBehaviourThread other);
		CmlAlphabet getHidingAlphabet();
		void setHidingAlphabet(CmlAlphabet alpha);
	};
	
	//Interface that gives access to methods that control the behaviour
	private ControlAccess 								controlAccess;
	//Evaluator for expressions and definitions
	protected QuestionAnswerCMLAdaptor<Context, Value>	cmlExpressionVisitor = new CmlExpressionVisitor();
	protected CmlDefinitionVisitor						cmlDefEvaluator = new CmlDefinitionVisitor();
	//use for making random but deterministic decisions
	protected Random 									rnd = new Random(9784345);
	//name of the thread
	LexNameToken 										name;
	
	protected final AbstractEvaluationVisitor			parentVisitor;
	
	public AbstractEvaluationVisitor(AbstractEvaluationVisitor parentVisitor)
	{
		this.parentVisitor = parentVisitor;
	}
	
	public void init(ControlAccess controlAccess)
	{
		this.controlAccess = controlAccess;
		name = this.controlAccess.ownerThread().name();
	}
	
	protected void pushNext(INode node,Context context)
	{
		controlAccess.pushNext(node, context);
	}
	
	protected void setHidingAlphabet(CmlAlphabet alpha)
	{
		controlAccess.setHidingAlphabet(alpha);
	}
	
	protected CmlAlphabet getHidingAlphabet()
	{
		return controlAccess.getHidingAlphabet();
	}
	
	protected void mergeState(CmlBehaviourThread other)
	{
		controlAccess.mergeState(other);
	}
	
	protected CmlBehaviourThread createChild(INode node, Context question, LexNameToken name)
	{
		return controlAccess.createChild(node, question, name);
	}
	
	protected void addChild(CmlBehaviourThread child)
	{
		controlAccess.addChild(child);
	}
	
	protected void removeTheChildren()
	{
		controlAccess.removeTheChildren();
	}
		
	protected CmlSupervisorEnvironment supervisor()
	{
		return controlAccess.ownerThread().supervisor();
	}
	
	protected List<CmlBehaviourThread> children()
	{
		return controlAccess.ownerThread().children();
	}
	
	protected CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child)
	{
		return controlAccess.executeChildAsSupervisor(child);
	}
	
	protected CmlBehaviourThread ownerThread()
	{
		return controlAccess.ownerThread();
	}
	
	protected void error(INode errorNode, String message)
	{
		throw new InterpreterRuntimeException(message);
	}
	
	/*
	 * This case is used to evaluate pre/post conditions and invariants
	 * (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#defaultPExp(org.overture.ast.expressions.PExp, java.lang.Object)
	 * 
	 */
	@Override
	public CmlBehaviourSignal defaultPExp(PExp node, Context question)
			throws AnalysisException {

		if(!node.apply(cmlExpressionVisitor,question).boolValue(question))
		{
			throw new ValueException(4061, question.prepostMsg, question);
		}
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
}
