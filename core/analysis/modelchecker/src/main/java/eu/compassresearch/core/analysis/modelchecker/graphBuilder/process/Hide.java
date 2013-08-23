package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class Hide implements Process {
	public Process getFirstProcess() {
		return firstProcess;
	}

	public void setFirstProcess(Process firstProcess) {
		this.firstProcess = firstProcess;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	private Process firstProcess;
	private String str;
	
	
	public Hide(Process firstProcess ,String str) {
		this.firstProcess = firstProcess;
		this.str = str;
	}
	
	@Override
	public String toString() {
		return firstProcess.toString() + "\\" + str;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Hide) {
			result =  super.equals(obj);
		}
		return result;
	}
	
}
