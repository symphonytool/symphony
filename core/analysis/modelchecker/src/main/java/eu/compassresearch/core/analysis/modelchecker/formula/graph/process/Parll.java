package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

public class Parll implements ParallelProcess {
	
	
	private Process startProcess;
	private String str1;
	private String str2;
	private String str3;
	private Process endProcess;
	
	
	public Parll(Process startProcess, String str3, String str2, String str1, Process endProcess) {
		this.startProcess = startProcess;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.endProcess = endProcess;
		
	}
	
	
	

}
