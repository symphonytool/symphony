package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;


public class Assing implements Process {
	
	private String number;
	
	public Assing(String number) {

		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "assgn";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof Assing){
			Assing other = (Assing) obj;
			result = this.getNumber().equals(other.getNumber());
		}
		return result;
	}
	
	public boolean isDeadlock(){
		return false;
	}
}
