package eu.compassresearch.core.analysis.modelchecker.formula.graph.param;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.type.Type;

public class DPar implements Param {

	private Type type1;
	private Type type2;

	public DPar(Type type1, Type type2) {
		this.type1 = type1;
		this.type2 = type2;
	}

	@Override
	public String toString() {
		return "("+type1.toString()+","+type2.toString()+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type1 == null) ? 0 : type1.hashCode());
		result = prime * result + ((type2 == null) ? 0 : type2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DPar other = (DPar) obj;
		if (type1 == null) {
			if (other.type1 != null)
				return false;
		} else if (!type1.equals(other.type1))
			return false;
		if (type2 == null) {
			if (other.type2 != null)
				return false;
		} else if (!type2.equals(other.type2))
			return false;
		return true;
	}
}
