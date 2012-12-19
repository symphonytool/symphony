package eu.compassresearch.core.interpreter.eval;

import java.util.List;
import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.util.Pair;

public abstract class AbstractEvaluator<T  extends INode> extends
		QuestionAnswerCMLAdaptor<Context, CmlBehaviourSignal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3882847083437565869L;
	private Stack<Pair<T,Context>> executionStack = new Stack<Pair<T,Context>>();
	protected Pair<T,Context> prevExecution = null;
	
	public boolean hasNext()
	{
		return !executionStack.isEmpty();
	}
	
	public boolean hasPrev()
	{
		return prevExecution != null;
	}
	
	public Pair<T,Context> prevState()
	{
		return prevExecution;
	}
	
	public Pair<T,Context> nextState()
	{
		return executionStack.peek();
	}
	
	protected List<Pair<T,Context>> getExecutionStack()
	{
		return executionStack;
	}
	
	protected void pushNext(T node, Context context)
	{
		executionStack.push(new Pair<T, Context>(node, context));
	}
	
	protected CmlBehaviourSignal executeNext() throws AnalysisException
	{
		if(hasNext())
		{
			Pair<T,Context> next = executionStack.pop();
			prevExecution = next;
			return next.first.apply(this,next.second);
		}
		else{
			throw new InterpreterRuntimeException("Trying to execute a finished Process...THIS SHOULD BE CHANGE INTO A DIFFERENT EXCEPTION");
		}
		
	}
	
}
