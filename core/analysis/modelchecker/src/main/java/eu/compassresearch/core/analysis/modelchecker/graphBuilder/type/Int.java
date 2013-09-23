package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;


public class Int implements Type {
	
	private int value;
	private String s;
	
	public Int(int value) {
		this.value = value;
	}
	
	public Int(String s){
		this.s = s;
	}
		
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public String toFormula() {
		return "Int("+String.valueOf(value)+")";
	}
	
	public String toFormulaWithState(){
		return "Int("+s+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Int)){
			return false;
		}else{
			Int aux = (Int) obj;
			return this.getValue() == aux.getValue();
		}
		
	}


}
