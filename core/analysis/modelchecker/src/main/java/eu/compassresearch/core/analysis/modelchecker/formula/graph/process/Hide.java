package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class Hide implements Process {
	private Process firstProcess;
	private String str;
	
	
	public Hide(Process firstProcess ,String str) {
		this.firstProcess = firstProcess;
		this.str = str;
}
	
}
