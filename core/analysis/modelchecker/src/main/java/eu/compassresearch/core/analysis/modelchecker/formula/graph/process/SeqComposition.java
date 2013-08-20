package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class SeqComposition implements Process {
	private Process firstProcess;
	private Process secondProcess;
	
	
	public SeqComposition(Process firstProcess ,Process secondProcess) {
		this.firstProcess = firstProcess;
		this.secondProcess = secondProcess;
}
	
}
