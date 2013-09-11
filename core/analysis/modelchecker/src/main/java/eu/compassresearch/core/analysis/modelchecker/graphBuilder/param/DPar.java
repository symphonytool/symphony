package eu.compassresearch.core.analysis.modelchecker.graphBuilder.param;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class DPar implements Param {

	private Type firstType;
	public Type getFirstType() {
		return firstType;
	}

	public void setFirstType(Type firstType) {
		this.firstType = firstType;
	}

	public Type getSecondType() {
		return secondType;
	}

	public void setSecondType(Type secondType) {
		this.secondType = secondType;
	}

	private Type secondType;

	public DPar(Type type1, Type type2) {
		this.firstType = type1;
		this.secondType = type2;
	}

	@Override
	public String toString() {
		return "("+firstType.toString()+","+secondType.toString()+")";
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof DPar){
			DPar other = (DPar) obj;
			result = this.getFirstType().equals(other.getFirstType()) && this.getSecondType().equals(other.getSecondType());
		}
		return result;
	}
	
}
