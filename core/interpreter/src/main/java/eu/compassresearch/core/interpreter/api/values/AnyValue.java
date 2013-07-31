package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

/**
 * Represents any value of a specific type. This corresponds to the top element of the lattice
 * where the possible values of the type are in the middle and AnyValue are the top and Undefined are
 * the bottom.
 * 
 * E.g for the nat type:
 * 
 * 		AnyValue
 * 		/	|   \
 * 	   1,2,....,infinite								
 * 		\   |   /
 * 		Undefined
 * 
 * @author akm
 *
 */
public class AnyValue extends Value {

	//unicode seems to be causing problems on Windows and Mac
	//private static final String topString = "\u22A4"; 
	private static final String topString = "?"; 
	private PType type;
	
	public AnyValue(PType type)
	{
		this.type = type;
	}
	
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
	
	public PType getType()
	{
		return type;
	}

	@Override
	public Object clone() {
		return new AnyValue(type);
	}

}
