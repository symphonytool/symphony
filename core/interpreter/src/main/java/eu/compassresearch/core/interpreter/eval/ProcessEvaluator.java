package eu.compassresearch.core.interpreter.eval;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.scheduler.InstantiatedProcess;
import eu.compassresearch.core.interpreter.scheduler.ProcessThread;
import eu.compassresearch.core.interpreter.scheduler.SequentialCompositionProcess;


@SuppressWarnings("serial")
public class ProcessEvaluator extends QuestionAnswerAdaptor<Context,CMLProcess> {
			
	@Override
	public CMLProcess caseAInstantiationProcess(AInstantiationProcess node,
			Context question) throws AnalysisException {
		
		//Grab the definition from the name
		AProcessDefinition processDefinition = (AProcessDefinition) 
				CmlRuntime.getGlobalEnvironment().lookupName(node.getProcessName().getIdentifier());
		
		//TODO Initialize the process state 
		Context inner = new Context(question);
		//TODO Add the process arguments
		CMLProcess instantiatedProcess = processDefinition.getProcess().apply(this,inner);
		CMLProcess process = new InstantiatedProcess(processDefinition,instantiatedProcess);
		return process;
	}
	
	@Override
	public CMLProcess caseAStateProcess(AStateProcess node, Context question)
			throws AnalysisException {
		
		//TODO Add state, value, etc to the corresponding processValue and context  
		ProcessThread pt = new ProcessThread(question, node);
		
		return pt;
	}
		
//	@Override
//	public Value caseASynchronousParallelismProcess(
//			ASynchronousParallelismProcess node, Context question)
//			throws AnalysisException {
//		
//		//question.getProcessThread().waitForSchedule();
//		
//		Set<LexIdentifierToken> channels = CmlRuntime.getGlobalEnvironment().getGlobalChannels();
//		
//		if (channels == null)
//				throw new AnalysisException("No channels are defined to synchronize on");
//		
//		ChannelSynchronizationConstraint comSyncLeft = new ChannelSynchronizationConstraint(channels,node.getRight());
//		ChannelSynchronizationConstraint comSyncRight = new ChannelSynchronizationConstraint(channels,node.getLeft());
//				
//		Context innerLeft = new Context(question,comSyncLeft);
//		Context innerRight = new Context(question,comSyncRight);
//				
//		ProcessValue leftValue = (ProcessValue)node.getLeft().apply(this,innerLeft);
//		ProcessValue rightValue = (ProcessValue)node.getRight().apply(this, innerRight);
//		
//		List<ACommunicationAction> leftEvents = leftValue.getOfferedEvents();
//		List<ACommunicationAction> rightEvents = rightValue.getOfferedEvents();
//					
//		return new ProcessValue();
//	}
	
	@Override
	public CMLProcess caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {
		
		CMLProcess leftProcess = node.getLeft().apply(this,question);
		CMLProcess rightProcess = node.getRight().apply(this,question);
				
		CMLProcess process = new SequentialCompositionProcess(leftProcess, rightProcess);
				
		return process;
	}
}
