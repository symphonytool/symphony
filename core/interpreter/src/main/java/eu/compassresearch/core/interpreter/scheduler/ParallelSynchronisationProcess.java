package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.actions.ACommunicationAction;

public class ParallelSynchronisationProcess implements CMLProcess
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
				
		return null;
	}

	@Override
	public void start() {
		
		this.leftProcess.start();
		this.rightProcess.start();
	}

	@Override
	public boolean isSkip() {
		return this.leftProcess.isSkip() && this.rightProcess.isSkip();
	}

	@Override
	public void eventOccured(ACommunicationAction event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getChannelSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemainingInterpretationState(boolean expand) {
		// TODO Auto-generated method stub
		return null;
	}
}
