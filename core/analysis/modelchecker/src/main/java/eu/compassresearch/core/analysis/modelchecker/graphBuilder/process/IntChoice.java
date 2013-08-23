package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class IntChoice extends Choice {
	
	public IntChoice(Process firstProcess ,Process secondProcess) {
		super(firstProcess, secondProcess);
	}

	@Override
	public String toString() {
		return this.getFirstProcess() + "|~|" + this.getSecondProcess();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof IntChoice) {
			result = super.equals(obj);
		}
		return result;
	}

}
