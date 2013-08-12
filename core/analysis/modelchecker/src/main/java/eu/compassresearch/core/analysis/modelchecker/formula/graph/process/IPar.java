package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;

	
public class IPar implements ParallelProcess {
	
	private Process process1;
	private String str;
	private Process process2;
	
	
	public IPar(Process process1, String str, Process process2) {

		this.process1 = process1;
		this.str = str;
		this.process2 = process2;
	}

}
