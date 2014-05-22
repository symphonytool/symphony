package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class TimedTimeout extends UntimedTimeout {
	
	private String timeout;
	
	public TimedTimeout(Process firstProcess ,Process secondProcess, String timeout) {
		super(firstProcess, secondProcess);
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return this.getFirstProcess() + "[" + timeout + ">" + this.getSecondProcess();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof TimedTimeout) {
			result = this.timeout.equals(((TimedTimeout) obj).timeout) 
					&& this.getFirstProcess().equals(((TimedTimeout) obj).getFirstProcess()) 
					&& this.getSecondProcess().equals(((TimedTimeout) obj).getSecondProcess());
		}
		return result;
	}
	
}
