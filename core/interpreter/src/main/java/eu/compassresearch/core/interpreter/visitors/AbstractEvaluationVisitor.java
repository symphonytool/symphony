package eu.compassresearch.core.interpreter.visitors;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.util.Pair;

public abstract class AbstractEvaluationVisitor extends QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> {

	/**
	 * Interface that gives access to methods that access protected parts of a CmlBehaviour
	 */
	private VisitorAccess 													visitorAccess;
	protected final CmlBehaviour 											owner;								
	/**
	 * Evaluator for expressions
	 */
	protected final QuestionAnswerCMLAdaptor<Context, Value>				cmlExpressionVisitor = new CmlExpressionVisitor();
	/**
	 * Evaluator for definitions
	 */
	protected QuestionAnswerCMLAdaptor<Context, NameValuePairList>			cmlDefEvaluator = new CmlDefinitionVisitor();
	/**
	 * Used for making random but deterministic decisions
	 */
	protected final Random													rnd = new Random(9784345);
	
	protected final QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>>	parentVisitor;
	
	public AbstractEvaluationVisitor(QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> parentVisitor, CmlBehaviour owner, VisitorAccess controlAccess)
	{
		this.parentVisitor = parentVisitor;
		this.owner = owner;
		this.visitorAccess = controlAccess;
	}
	
	protected void setLeftChild(CmlBehaviour child)
	{
		this.visitorAccess.setLeftChild(child);
	}
	
	protected void setRightChild(CmlBehaviour child)
	{
		this.visitorAccess.setRightChild(child);
	}
	
	protected void removeTheChildren()
	{
		for(Iterator<CmlBehaviour> iterator = children().iterator(); iterator.hasNext(); )
		{
			CmlBehaviour child = iterator.next();
			supervisor().removePupil(child);
			iterator.remove();
		}
	}
	
	protected ILexNameToken name()
	{
		return owner.name();
	}
		
	protected CmlSupervisorEnvironment supervisor()
	{
		return owner.supervisor();
	}
	
	protected List<CmlBehaviour> children()
	{
		return owner.children();
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
	public Pair<INode, Context> defaultPExp(PExp node, Context question)
			throws AnalysisException {

		if(!node.apply(cmlExpressionVisitor,question).boolValue(question))
		{
			throw new ValueException(4061, question.prepostMsg, question);
		}
		return new Pair<INode, Context>(new ASkipAction(), question);
	}
}
