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
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.scheduler.InstantiatedProcess;
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
		
		
		
		//TODO Initialize the process state 
				
		Context inner = new Context(question);

		//TODO Add the process arguments
		
		ProcessValue instantiatedProcessValue = (ProcessValue)processDefinition.getProcess().apply(this,inner);
		
		CMLProcess process = new InstantiatedProcess(processDefinition,instantiatedProcessValue.getProcess());
		
//		ProcessThread pt = new ProcessThread(inner, processDefinition.getProcess());
//		//CmlRuntime.getCmlScheduler().addProcessThread(processDefinition.getProcess(), inner);
		ProcessValue pv = new ProcessValue(process,inner);
		//inner.put(processDefinition.getName().getProcessName(),pv);
//		pt.start();
		return pv;
	}
	
	@Override
	public Value caseAStateProcess(AStateProcess node, Context question)
			throws AnalysisException {
		
		//TODO Add state, value, etc to the corresponding processValue and context  
		
		ProcessThread pt = new ProcessThread(question, node);
		
		return new ProcessValue(pt,question);
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
