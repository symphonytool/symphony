package eu.compassresearch.core.interpreter.values;


public class AnyValue extends CmlValue {

	private static final String topString = "\u22A4"; 
	
	@Override
	public String toString() {
		return topString;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof AnyValue;
	}

	@Override
	public int hashCode() {
		return topString.hashCode();
	}

	@Override
	public String kind() {
		return toString();
	}

	@Override
	public Object clone() {
		return new AnyValue();
	}

}
