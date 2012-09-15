package eu.compassresearch.core.interpreter.eval;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AIdentifierStateDesignator;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.core.interpreter.runtime.CMLContext;
import eu.compassresearch.core.interpreter.runtime.ChannelEvent;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ActionEvaluator extends QuestionAnswerAdaptor<CMLContext, Value> {

	private CmlEvaluator parentInterpreter; 
	
	public ActionEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	@Override
	public Value caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node, CMLContext question)
			throws AnalysisException {
				
		Value expValue = node.getExpression().apply(parentInterpreter,question);
		//TODO Change this to deal with it in general
		AIdentifierStateDesignator id = (AIdentifierStateDesignator)node.getStateDesignator();
		
		CMLContext nameContext = question.locate(id.getName());
		
		if(nameContext == null)
			nameContext = new CMLContext(node.getLocation());	
		
		question.put(id.getName(), expValue);
		
		System.out.println( id.getName() + " := " + expValue);
		
		return new ProcessValue(null);
	}
	
	@Override
	public Value caseACommunicationAction(ACommunicationAction node,
			CMLContext question) throws AnalysisException {
	
		ChannelEvent ev = question.getCurrentEvent();
		
		if(ev != null && ev.getChannelName().equals(node.getIdentifier().getName()))
		{
			question.resetEvent();
			ProcessValue v = (ProcessValue)node.getAction().apply(parentInterpreter,question);
			
			ProcessValue retV = null;
			
			if(v.isReduced())
				retV = v;
			else{
				retV = new ProcessValue(v.getOfferedEvents(),null);
				retV.setReduced(true);
				retV.setReducedAction(node.getAction());
			}
			
			return retV;
		}
		else
			return new ProcessValue(node,question);
				
	}
	
	@Override
	public Value caseASkipAction(ASkipAction node, CMLContext question)
			throws AnalysisException {
		
		//question.getProcessThread().waitForSchedule();
		
		//System.out.print("<Skip>");
		
		return new ProcessValue();
	}
	
	private PAction getNextAction(ProcessValue processValue, PAction currentAction)
	{
		PAction nextAction = null;
		
		if(processValue.isReduced())
			nextAction = processValue.getReducedAction();
		else
			nextAction = currentAction;
			
		return nextAction;
	}

	@Override
	public Value caseASequentialCompositionAction(
			ASequentialCompositionAction node, CMLContext question)
			throws AnalysisException {
				
		ProcessValue retValue = null;
		if(node.getLeft() != null)
		{
			ProcessValue leftValue = (ProcessValue)node.getLeft().apply(this,question);
			
			if(!leftValue.isSkip())
			{
				PAction nextAction = getNextAction(leftValue, node.getLeft());
				node.setLeft(nextAction);
				retValue = new ProcessValue(leftValue.getOfferedEvents(),null);
			}
			else
				node.setLeft(null);
		}
		
		if(retValue == null )
		{
			ProcessValue rightValue = (ProcessValue)node.getRight().apply(this,question);
			
			if(!rightValue.isSkip())
			{
				PAction nextAction = getNextAction(rightValue, node.getRight());
				retValue = new ProcessValue(nextAction);
			}
			else
				retValue = new ProcessValue(null);
		}

		return retValue;
	}
}
