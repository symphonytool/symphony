package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public class ConditionalChoice implements Choice {
	private Process firstProcess;
	private Process secondProcess;
	private int number;
	
	
	public ConditionalChoice(Process firstProcess, Process secondProcess, int number) {
		this.number = number;
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
		
	}

}
