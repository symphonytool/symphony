package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

public class Schema implements Process {
	
	private String str;
	
	
	public Schema(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof Schema){
			Schema other = (Schema) obj;
			result = this.str.equals(other.str);
		}
		return result;
		
	}
	
	@Override
	public boolean isDeadlock(){
		return false;
	}

}
	
