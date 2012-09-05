package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;
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
	
	private List<ProcessThread> childProcesses = new LinkedList<ProcessThread>();		
	
	
	public ProcessThread(Context context, AStateProcess process)
	{
		this.context = context;
		this.process = process;
		this.t = new Thread(this);
		this.t.setDaemon(true);
		//this.t.setName(process.)
	}
			
	public void addChildProcess(Context context, AStateProcess process)
	{
		childProcesses.add(new ProcessThread(context,process));
	}
				
	@Override
	public void run() {
				
		try {
//			ProcessValue pvalue = (ProcessValue)process.apply(evalutor,context);
//									
//			offeredEventsChannel.put(pvalue.getOfferedEvents());
//			
//			if(pvalue.isComposition())
//			{
//				CMLProcess p = pvalue.getProcess();
//				List<ACommunicationAction> offeredEvents = null;
//				
//				do{
//					ACommunicationAction ca = recievedEvent.take();
//					p.eventOccured(ca);
//					offeredEvents = p.WaitForEventOffer();
//					offeredEventsChannel.put(offeredEvents);
//				}while(!offeredEvents.isEmpty());
//			}
//			else
//			{
			ProcessValue pvalue = (ProcessValue)process.getAction().apply(evalutor,context);
			offeredEventsChannel.put(pvalue.getOfferedEvents());

			while(pvalue != null && !pvalue.getOfferedEvents().isEmpty())
			{
				ACommunicationAction ca = recievedEvent.take();
				pvalue = (ProcessValue)ca.getAction().apply(evalutor,pvalue.getCurrentContext());
				offeredEventsChannel.put(pvalue.getOfferedEvents());
			}
//			}
			
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
	public boolean isAlive() {
		
		return this.t.isAlive();
	}
}
