package eu.compassresearch.core.analysis.modelchecker.formula.graph.binding;

public class BBinding implements Binding {
	
	private String begin;
	private String middle;
	private String end;

	
	public BBinding(String begin,String middle,String end) {
		this.begin = begin;
		this.middle = middle;
		this.end = end;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append(begin);
		result.append(",");
		result.append(middle);
		result.append(",");
		result.append(end);
		result.append("}");
		
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begin == null) ? 0 : begin.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((middle == null) ? 0 : middle.hashCode());
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
		BBinding other = (BBinding) obj;
		if (begin == null) {
			if (other.begin != null)
				return false;
		} else if (!begin.equals(other.begin))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (middle == null) {
			if (other.middle != null)
				return false;
		} else if (!middle.equals(other.middle))
			return false;
		return true;
	}


}
