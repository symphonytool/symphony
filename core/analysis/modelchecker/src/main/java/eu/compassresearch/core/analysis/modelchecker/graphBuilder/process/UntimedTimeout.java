package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class UntimedTimeout extends Choice {
	
	public UntimedTimeout(Process firstProcess ,Process secondProcess) {
		super(firstProcess, secondProcess);
	}

	@Override
	public String toString() {
		return this.getFirstProcess() + "[_>" + this.getSecondProcess();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof UntimedTimeout) {
			result = super.equals(obj);
		}
		return result;
	}
	
	@Override
	public boolean isDeadlock(){
		return this.getFirstProcess().isDeadlock() || this.getSecondProcess().isDeadlock();
	}


}
