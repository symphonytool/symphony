package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;

/**
 *  This class represents a running CML Process. It represents a specific node as specified in D23.2 section 7.4.2,
 *  where a node is specified as a tuple (w,s,a) where w is the set of variables, s is the state values and a is the 
 *  current action.
 *  w and s are stored in the current Context object and a is represented by storing the next action AST node to be executed.
 * 
 * 	The possible transitions are handled in the visitor case methods.
 * 
 *  Therefore this Class should be fully consistent with the operational semantics described in D23.2 chapter 7.
 * 
 * 
 * @author akm
 *
 */
public class CMLActionInstance extends AbstractInstance<PAction> {

	private LexNameToken name;
	private AlphabetInspectionVisitor alphabetInspectionVisitor = new AlphabetInspectionVisitor(); 
	
	public CMLActionInstance(PAction action,Context context, LexNameToken name)
	{
		super(null);
		this.name = name;
		pushNext(action, context);
		//executionStack.push(new Pair<PAction, Context>(action, context));
	}
	
	@Override
	public void start(CMLSupervisorEnvironment env) {
		this.env= env; 
		state = ProcessState.RUNNABLE;
	}

	@Override
	public CMLAlphabet inspect() throws AnalysisException 
	{
		Pair<PAction,Context> next = nextState();
		return next.first.apply(alphabetInspectionVisitor,next.second);
	}

	@Override
	public ProcessState getState() {
		return state;
	}

	@Override
	public LexNameToken name() {
		return this.name;
	}

	@Override
	public CMLBehaviourSignal caseACommunicationAction(
			ACommunicationAction node, Context question)
			throws AnalysisException {
		
		pushNext(node.getAction(), question);
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 */
	@Override
	public CMLBehaviourSignal caseAReferenceAction(AReferenceAction node,
			Context question) throws AnalysisException {

		System.out.println("Hello World ref");
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CMLBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {
		
		state = ProcessState.FINISHED;
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public void setState(ProcessState state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
	
		if(hasNext())
			return nextState().first.toString();
		else
			return "Finished";
		
	}
	
}
