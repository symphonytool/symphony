package eu.compassresearch.core.interpreter.eval;

import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.core.interpreter.runtime.ChannelSynchronizationConstraint;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.scheduler.ProcessThread;
import eu.compassresearch.core.interpreter.values.ProcessValue;


@SuppressWarnings("serial")
public class ProcessEvaluator extends QuestionAnswerAdaptor<Context,Value> {
	
	private CmlEvaluator parentInterpreter; 
	
	public ProcessEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	@Override
	public Value caseAInstantiationProcess(AInstantiationProcess node,
			Context question) throws AnalysisException {
						
				
		AProcessDefinition processDefinition = (AProcessDefinition) 
				CmlRuntime.getGlobalEnvironment().lookupName(node.getProcessName().getIdentifier());
		
		//FIXME don't do this, this needs to go in a separate execution thread
		//TODO Initialize the process state 
				
		Context inner = new Context(question);
		ProcessThread pt = CmlRuntime.getCmlScheduler().addProcessThread(processDefinition.getProcess(), inner);
		ProcessValue pv = new ProcessValue(pt,inner);
		inner.put(processDefinition.getName().getProcessName(),pv);

		return pv;
	}
	
	@Override
	public Value caseAStateProcess(AStateProcess node, Context question)
			throws AnalysisException {
		
		//TODO Add state, value, etc to the corresponding processValue  
		
		//question.getProcessThread().waitForSchedule();
		
		return node.getAction().apply(parentInterpreter,question);
	}
		
	@Override
	public Value caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node, Context question)
			throws AnalysisException {
		
		//question.getProcessThread().waitForSchedule();
		
		Set<LexIdentifierToken> channels = CmlRuntime.getGlobalEnvironment().getGlobalChannels();
		
		if (channels == null)
				throw new AnalysisException("No channels are defined to synchronize on");
		
		ChannelSynchronizationConstraint comSyncLeft = new ChannelSynchronizationConstraint(channels,node.getRight());
		ChannelSynchronizationConstraint comSyncRight = new ChannelSynchronizationConstraint(channels,node.getLeft());
				
		Context innerLeft = new Context(question,comSyncLeft);
		Context innerRight = new Context(question,comSyncRight);
				
		ProcessValue leftValue = (ProcessValue)node.getLeft().apply(this,innerLeft);
		ProcessValue rightValue = (ProcessValue)node.getRight().apply(this, innerRight);
		
		List<ACommunicationAction> leftEvents = leftValue.getOfferedEvents();
		List<ACommunicationAction> rightEvents = rightValue.getOfferedEvents();
			
		
		
		
		
		return new ProcessValue();
	}
		
}
