package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class ExtChoice implements Choice {
	
	private Process firstProcess;
	private Process secondProcess;
	

	public ExtChoice(Process firstProcess, Process secondProcess2) {
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
		
	}

}
