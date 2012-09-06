package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ProcessThread extends AbstractCMLProcess implements Runnable {
	
	private AStateProcess process;
	private Context context;
	private final Thread t;
	private CmlEvaluator evalutor = new CmlEvaluator();
	private PAction currentAction;
	private List<ACommunicationAction> currentlyOfferedEvents = new LinkedList<ACommunicationAction>();	
		
	public ProcessThread(Context context, AStateProcess process)
	{
		this.context = context;
		this.process = process;
		this.t = new Thread(this);
		this.t.setDaemon(true);
		//this.t.setName(process.)
		//add fake to not make isSkip true before it has been started
		currentlyOfferedEvents.add(new ACommunicationAction());
		currentAction = process.getAction();
	}
					
	@Override
	public void run() {

		try {
			ProcessValue pvalue = (ProcessValue)process.getAction().apply(evalutor,context);
			currentlyOfferedEvents = pvalue.getOfferedEvents();
			offeredEventsChannel.put(currentlyOfferedEvents);

			while(pvalue != null && !currentlyOfferedEvents.isEmpty())
			{
				ACommunicationAction ca = recievedEvent.take();
				pvalue = (ProcessValue)ca.getAction().apply(evalutor,pvalue.getCurrentContext());
				currentlyOfferedEvents = pvalue.getOfferedEvents();
				offeredEventsChannel.put(currentlyOfferedEvents);
			}

		} catch (AnalysisException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		
		this.t.start();
	}

	@Override
	public boolean isSkip() {
		
		//return !this.t.isAlive() || currentlyOfferedEvents.isEmpty();
		return currentlyOfferedEvents.isEmpty();
	}

	@Override
	public String getRemainingInterpretationState(boolean expand) {
		
		return "NA";
	}
}
