package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

public interface LatticeElement
{
	//LatticeElement join(LatticeElement other);
	LatticeElement meet(LatticeElement other);
	
	boolean isComparable(LatticeElement other);
	
	boolean isTopElement();
	
	boolean isMostPrecise();
	
	Value getValue();
	
	PType getType();
}
