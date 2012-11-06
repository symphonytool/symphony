package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CMLAlphabet;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLChannelEvent;
import eu.compassresearch.core.interpreter.cml.CMLProcess;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ChannelObserver;
import eu.compassresearch.core.interpreter.cml.ProcessState;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;

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
		
		System.out.println(node.getIdentifier() + "->");
		pushNext(node.getAction(), question);
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CMLBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {
		
		System.out.println("Skip");
		
		state = ProcessState.FINISHED;
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public void setState(ProcessState state) {
		this.state = state;
	}
	
}
