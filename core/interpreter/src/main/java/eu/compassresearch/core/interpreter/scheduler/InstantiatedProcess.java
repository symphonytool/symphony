package eu.compassresearch.core.interpreter.scheduler;

import java.util.List;
import java.util.Set;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.definitions.AProcessDefinition;

public class InstantiatedProcess implements CMLProcess {

	private AProcessDefinition processDefinition;
	private CMLProcess process;
	
	public InstantiatedProcess(AProcessDefinition processDefinition, CMLProcess process)
	{
		this.processDefinition = processDefinition;
		this.process = process;
	}
	
	@Override
	public List<ACommunicationAction> WaitForEventOffer() {
		
		return this.process.WaitForEventOffer();
	}

	@Override
	public void eventOccured(ACommunicationAction event) {
		
		System.out.println("Event on instance of process " + processDefinition.getName().getName());
		this.process.eventOccured(event);
		
	}

	@Override
	public Set<ACommunicationAction> getChannelSet() {
		
		return this.process.getChannelSet();
	}

	@Override
	public void start() {
		
		this.process.start();
		System.out.println("Instance of process " + processDefinition.getName().getName() + " started"); 
	}

	@Override
	public boolean isSkip() {
		
		return this.process.isSkip();
	}

}
