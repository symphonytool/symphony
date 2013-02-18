package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.Value;


public class AnyValue extends Value {

	//unicode seems to ne causing problems on windows and mac
	//private static final String topString = "\u22A4"; 
	private static final String topString = "?"; 
	
	
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
