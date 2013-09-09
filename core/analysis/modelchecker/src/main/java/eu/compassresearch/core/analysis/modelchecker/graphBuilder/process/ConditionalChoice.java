package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class ConditionalChoice extends Choice {
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	private int number;
	
	
	public ConditionalChoice(int number,Process firstProcess, Process secondProcess) {
		super(firstProcess, secondProcess);
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "if (some exp) then " + firstProcess.toString() + " else " + secondProcess.toString();
	}
	 
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ConditionalChoice) {
			ConditionalChoice other = (ConditionalChoice) obj;
			result = super.equals(obj) && this.getNumber() == other.getNumber();
		}
		return result;
	}

	@Override
	public boolean isDeadlock(){
		return false;
	}

}
