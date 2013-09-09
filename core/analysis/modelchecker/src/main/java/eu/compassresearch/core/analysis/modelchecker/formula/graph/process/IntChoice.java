package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public class IntChoice extends Choice {
	
	public IntChoice(Process firstProcess ,Process secondProcess) {
		super(firstProcess, secondProcess);
	}

	@Override
	public String toString() {
		return this.getFirstProcess() + "|~|" + this.getSecondProcess();
	}

}
