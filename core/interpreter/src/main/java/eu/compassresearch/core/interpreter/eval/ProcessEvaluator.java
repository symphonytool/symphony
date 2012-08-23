package eu.compassresearch.core.interpreter.eval;

import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.values.ProcessValue;


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
		inner.put(processDefinition.getName().getProcessName(),new ProcessValue());
		return processDefinition.getProcess().apply(parentInterpreter,inner);
	}
	
	@Override
	public Value caseAStateProcess(AStateProcess node, Context question)
			throws AnalysisException {
		
		//TODO Add state, value, etc to the corresponding processValue  
							
		return node.getAction().apply(parentInterpreter,question);
	}
		
	@Override
	public Value caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node, Context question)
			throws AnalysisException {
		
		Set<LexIdentifierToken> channels = CmlRuntime.getGlobalEnvironment().getGlobalChannels();
		
		if (channels == null)
				throw new AnalysisException("No channels are defined to synchrnize on");
		
		Context inner = new Context(question);
		inner.setSynchronizationChannels(channels);
		
		node.getLeft().apply(this,inner);
		node.getRight().apply(this, inner);
		
		//TODO I don't really now what would be appropriate to returned here, so I created a ProcessValue.
		return new ProcessValue();
	}
		
}
