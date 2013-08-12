package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class IntChoice implements Choice {
	
	private Process firstProcess;
	private Process secondProcess;
	
	
	public IntChoice(Process firstProcess ,Process secondProcess) {
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
		
		
	}

}
