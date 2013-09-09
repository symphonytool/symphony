package eu.compassresearch.core.analysis.modelchecker.formula.graph.process;


public class ExtChoice extends Choice {
	
	
	public ExtChoice(Process firstProcess, Process secondProcess2) {
	   super(firstProcess, secondProcess2);
	}
	
	@Override
	public String toString() {
		return this.getFirstProcess() + "[]" + this.getSecondProcess();
	}

}
