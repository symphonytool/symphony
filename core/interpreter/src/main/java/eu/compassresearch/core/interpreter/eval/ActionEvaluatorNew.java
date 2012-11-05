package eu.compassresearch.core.interpreter.eval;

import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CMLBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CMLProcessNew;
import eu.compassresearch.core.interpreter.util.Pair;

public class ActionEvaluatorNew extends
AbstractEvaluator<PAction> {

	public ActionEvaluatorNew(PAction firstAction, Context context)
	{
		executionStack.push(new Pair<PAction, Context>(firstAction, context));
	}
	
	@Override
	public CMLBehaviourSignal caseACommunicationAction(ACommunicationAction node, Context context)
			throws AnalysisException {
		
		//CMLProcessNew nextAction = node.getAction().apply(this);

		//CMLProcessNew nextAction 
		
//		PrefixAction action =
//		if(node.getCommunicationParameters() == null)
			
		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CMLBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {

		return CMLBehaviourSignal.EXEC_SUCCESS;
	}
	
}
