package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.runtime.Context;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ProcessThread implements Runnable {
	
	private PProcess process;
	private Context context;
	public final Thread t;
	private CmlEvaluator evalutor = new CmlEvaluator();
	private SynchronousQueue<List<ACommunicationAction>> offeredEvents = new SynchronousQueue<List<ACommunicationAction>>();
	private SynchronousQueue<ACommunicationAction> recievedEvent = new SynchronousQueue<ACommunicationAction>();
	private List<ProcessThread> childProcesses = new LinkedList<ProcessThread>();		
	
	
	public ProcessThread(Context context, PProcess process)
	{
		this.context = context;
		this.process = process;
		this.t = new Thread(this);
		this.t.setDaemon(true);
		//this.t.setName(process.)
	}
	
	public List<ACommunicationAction> WaitForEventOffer()
	{
		List<ACommunicationAction> events = null;
		try {
			events = offeredEvents.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return events;
	}
	
	public void eventOccured(ACommunicationAction event)
	{
		try {
			recievedEvent.put(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addChildProcess(Context context, PProcess process)
	{
		childProcesses.add(new ProcessThread(context,process));
	}
				
	@Override
	public void run() {
				
		try {
			ProcessValue pvalue = (ProcessValue)process.apply(evalutor,context);
			offeredEvents.put(pvalue.getOfferedEvents());
						
			while(pvalue != null && !pvalue.getOfferedEvents().isEmpty())
			{
				ACommunicationAction ca = recievedEvent.take();
				pvalue = (ProcessValue)ca.getAction().apply(evalutor,pvalue.getCurrentContext());
				offeredEvents.put(pvalue.getOfferedEvents());
			}
			
		} catch (AnalysisException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
