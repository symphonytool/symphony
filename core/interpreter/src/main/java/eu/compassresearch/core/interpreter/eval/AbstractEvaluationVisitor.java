package eu.compassresearch.core.interpreter.eval;

import java.util.List;
import java.util.Random;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;

public abstract class AbstractEvaluationVisitor extends QuestionAnswerCMLAdaptor<Context, CmlBehaviourSignal> {

	public interface ControlAccess
	{
		CmlBehaviourThread ownerThread();
		void pushNext(INode node,Context context);
		boolean hasNext();
		void setState(CmlProcessState state);
		CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child);
		void addChild(CmlBehaviourThread child);
		void removeTheChildren();
		CmlBehaviourThread createChild(INode node, Context question, LexNameToken name);
		void mergeState(CmlBehaviourThread other);
		CmlAlphabet getHidingAlphabet();
		void setHidingAlphabet(CmlAlphabet alpha);
	};
	
	//Interface that gives access to methods that control the behaviour
	private ControlAccess 							controlAccess;
	//Evaluator for expressions and definitions
	protected QuestionAnswerCMLAdaptor<Context, Value>	cmlExpressionVisitor = new CmlExpressionVisitor();
	protected CmlDefinitionVisitor					cmlDefEvaluator = new CmlDefinitionVisitor();
	//use for making random but deterministic decisions
	protected Random 								rnd = new Random(9784345);
	//name of the thread
	LexNameToken 									name;
	
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
	
	protected void setState(CmlProcessState state)
	{
		controlAccess.setState(state);
	}
	
	protected void addChild(CmlBehaviourThread child)
	{
		controlAccess.addChild(child);
	}
	
	protected void removeTheChildren()
	{
		controlAccess.removeTheChildren();
	}
	
	protected boolean hasChildren()
	{
		return controlAccess.ownerThread().hasChildren();
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
	
	protected boolean hasNext()
	{
		return controlAccess.hasNext();
	}
	
	protected CmlBehaviourThread ownerThread()
	{
		return controlAccess.ownerThread();
	}
}
