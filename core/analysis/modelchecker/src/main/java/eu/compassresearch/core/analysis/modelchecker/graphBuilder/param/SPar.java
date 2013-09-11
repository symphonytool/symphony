package eu.compassresearch.core.analysis.modelchecker.graphBuilder.param;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class SPar implements Param {

	
	private Type type;
	
	public SPar(Type type) {
		this.type = type;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "("+type.toString()+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof SPar){
			SPar other = (SPar) obj;
			result = this.getType().equals(other.getType());
		}
		return result;
	}
}
