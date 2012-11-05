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
import eu.compassresearch.core.interpreter.cml.CMLProcess;
import eu.compassresearch.core.interpreter.cml.CMLSupervisorEnvironment;
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
		executionStack.push(new Pair<PAction, Context>(action, context));
	}
	
	@Override
	public void start(CMLSupervisorEnvironment env) {
		this.env= env; 
		state = ProcessState.RUNNABLE;
	}

//	@Override
//	public CMLBehaviourSignal execute(CMLSupervisorEnvironment env) throws AnalysisException {
//		this.env= env;
//
//		//inspect
//		CMLAlphabet alpha = inspect();
//		CMLBehaviourSignal ret = null;
//
//		//execute if the next is an invisible action
//		if(alpha.containsTau()){
//			state = ProcessState.RUNNING;
//			ret = executeNext();
//			//state = ProcessState.WAIT;
//		}
//		else 
//		{	
//			//if no com is selected yet we set go to wait state
//			if(env.communicationSelected() && alpha.containsCommunication(env.selectedCommunication()))
//			{
//				ret = executeNext();
//			}
//			else 
//			{
//				state = ProcessState.WAIT;
//				ret = CMLBehaviourSignal.EXEC_SUCCESS;
//			}
//		}
//
//		return ret;
//
//
//	}

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
		
		executionStack.push(new Pair<PAction, Context>(node.getAction(), question));
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CMLBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {
		
		System.out.println("Skip");
		
		state = ProcessState.FINISHED;
		
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
}
