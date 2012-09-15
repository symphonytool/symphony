package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.runtime.CMLContext;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ProcessThread implements Runnable, CMLProcess {
	
	private AStateProcess process;
	private CMLContext context;
	private final Thread t;
	private CmlEvaluator evalutor = new CmlEvaluator();
	private PAction currentAction;
	private List<ACommunicationAction> currentlyOfferedEvents = new LinkedList<ACommunicationAction>();	
	private SynchronousQueue<List<ACommunicationAction>> offeredEventsChannel = new SynchronousQueue<List<ACommunicationAction>>();
	private SynchronousQueue<ACommunicationAction> recievedEvent = new SynchronousQueue<ACommunicationAction>();
	
	public ProcessThread(CMLContext context, AStateProcess process)
	{
		this.context = context;
		this.process = process;
		this.t = new Thread(this);
		this.t.setDaemon(true);
		//this.t.setName(process.)
		//add fake to not make isSkip true before it has been started
		currentlyOfferedEvents.add(new ACommunicationAction());
		currentAction = process.getAction().clone();
	}
					
	@Override
	public void run() {

		try 
		{
			ProcessValue pvalue = (ProcessValue)currentAction.apply(evalutor,context);
			currentlyOfferedEvents = pvalue.getOfferedEvents();
			offeredEventsChannel.put(currentlyOfferedEvents);

			while(pvalue != null && !currentlyOfferedEvents.isEmpty())
			{
				ACommunicationAction ca = recievedEvent.take();
				pvalue = (ProcessValue)ca.getAction().apply(evalutor,pvalue.getCurrentContext());
				currentAction = ca.getAction();
				currentlyOfferedEvents = pvalue.getOfferedEvents();
				offeredEventsChannel.put(currentlyOfferedEvents);
			}

		} catch (AnalysisException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public List<ACommunicationAction> WaitForEventOffer()
	{
		List<ACommunicationAction> events = null;
		try {
			events = offeredEventsChannel.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return events;
	}
	
	@Override
	synchronized public void eventOccured(ACommunicationAction event)
	{
		try {
			recievedEvent.put(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<String> getChannelSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		
		this.t.start();
	}

	@Override
	synchronized public boolean isSkip() {
		
		//return !this.t.isAlive() || currentlyOfferedEvents.isEmpty();
		return currentlyOfferedEvents.isEmpty();
	}

	@Override
	public String getRemainingInterpretationState(boolean expand) {
		
		return currentAction.toString();
	}
}
