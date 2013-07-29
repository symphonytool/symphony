package eu.compassresearch.core.interpreter;

import java.util.Random;

import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePairList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.InternalTransition;

public class AbstractInspectionVisitor extends
		QuestionAnswerCMLAdaptor<Context, Inspection> {

	//The process that contains this instance
	protected final CmlBehaviour 									owner;
	protected final CmlExpressionVisitor							cmlExpressionVisitor = new CmlExpressionVisitor();
	
	/**
	 * Used for making random but deterministic decisions
	 */
	protected final Random											rnd = new Random(9784345);
	
	/**
	 * Evaluator for definitions
	 */
	protected QuestionAnswerCMLAdaptor<Context, NameValuePairList>	cmlDefEvaluator = new CmlDefinitionVisitor();
	
	/**
	 * Interface that gives access to methods that access protected parts of a CmlBehaviour
	 */
	protected final VisitorAccess 									visitorAccess;
	
	protected final QuestionAnswerCMLAdaptor<Context, Inspection>	parentVisitor;
	/**
	 * 
	 * @param ownerProcess
	 */
	public AbstractInspectionVisitor(CmlBehaviour ownerProcess, VisitorAccess visitorAccess, QuestionAnswerCMLAdaptor<Context, Inspection>	parentVisitor)
	{
		this.owner = ownerProcess;
		this.visitorAccess = visitorAccess;
		this.parentVisitor = parentVisitor;
	}
	
	/**
	 * Common Helpers
	 */
	
	protected CmlAlphabet createSilentTransition(INode srcNode, INode dstNode, String transitionText)
	{
		return new CmlAlphabet(new CmlTock(owner),new InternalTransition(owner,srcNode,dstNode,transitionText));
	}
	
	protected CmlAlphabet createSilentTransition(INode srcNode, INode dstNode)
	{
		return createSilentTransition(srcNode,dstNode,null);
	}
	
	protected Inspection newInspection(CmlAlphabet transitions,CmlCalculationStep step)
	{
		return new Inspection(new CmlTrace(owner.getTraceModel()), transitions,step);
	}
	
}