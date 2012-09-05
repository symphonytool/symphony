package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;

import eu.compassresearch.ast.actions.ACommunicationAction;

public class ParallelSynchronisationProcess extends AbstractCMLProcess
{
	private CMLProcess leftProcess; 
	private CMLProcess rightProcess;
	
	public ParallelSynchronisationProcess(CMLProcess leftProcess, CMLProcess rightProcess)
	{
		this.leftProcess = leftProcess;
		this.rightProcess = rightProcess;
	}
		
	@Override
	public List<ACommunicationAction> WaitForEventOffer() {
				
		List<ACommunicationAction> leftActions = leftProcess.WaitForEventOffer();
		List<ACommunicationAction> rigthActions = rightProcess.WaitForEventOffer();
		
		
		
		return super.WaitForEventOffer();
	}

	@Override
	public void start() {
		
		this.leftProcess.start();
		this.rightProcess.start();
	}

	@Override
	public boolean isAlive() {
		return this.leftProcess.isAlive() || this.rightProcess.isAlive();
	}
}
