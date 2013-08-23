package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;


public class BBinding implements Binding {
	
	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

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
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof BBinding){
			BBinding other = (BBinding) obj;
			result = this.getBegin().equals(other.getBegin()) && this.getMiddle().equals(other.getMiddle()) && this.getEnd().equals(other.getEnd());
		}
		return result;
	}


}
