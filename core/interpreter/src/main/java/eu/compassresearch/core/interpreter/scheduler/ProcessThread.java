package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.runtime.Context;

public class ProcessThread implements Runnable {
	
	private PProcess process;
	//private PAction currentAction;
	private Context context;
	private CmlEvaluator evalutor = new CmlEvaluator();
	
	private List<ACommunicationAction> currentlyAcceptedEvent;
	
	public ProcessThread(Context context, PProcess process)
	{
		this.context = context;
		this.process = process;
		
	}
		
	public void step()
	{
		
	}

	@Override
	public void run() {
				
		try {
			process.apply(evalutor,context);
		} catch (AnalysisException e) {

			e.printStackTrace();
		}
		
	}
}
