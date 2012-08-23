package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.Value;

public class ProcessValue extends Value {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "process";
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		
		return 0;
	}

	@Override
	public String kind() {
		
		return toString();
	}

	@Override
	public Object clone() {
		
		return new ProcessValue();
	}

}
