package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class IPar implements ParallelProcess {
	
	private Process process1;
	private String str;
	private Process process2;
	
	
	public IPar(Process process1, String str, Process process2) {

		this.process1 = process1;
		this.str = str;
		this.process2 = process2;
	}
	
	
	
	public Process getProcess1() {
		return process1;
	}



	public void setProcess1(Process process1) {
		this.process1 = process1;
	}



	public String getStr() {
		return str;
	}



	public void setStr(String str) {
		this.str = str;
	}



	public Process getProcess2() {
		return process2;
	}



	public void setProcess2(Process process2) {
		this.process2 = process2;
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof IPar) {
			IPar other = (IPar) obj;
			result = this.getProcess1().equals(other.getProcess1())
					&& this.getProcess2().equals(other.getProcess2())
					&& this.getStr().equals(other.getStr());
		}
		return result;
	}
	

}
