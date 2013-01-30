package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.runtime.CmlContext;

public abstract class CmlValue extends Value {

	public CmlObjectValue CmlObjectValue(CmlContext ctxt) throws ValueException
	{
		abort(4105, "Can't get object value of " + kind(), ctxt.getVdmContext());
		return null;
	}
	
	public CmlAlphabet cmlAlphabetValue(CmlContext ctxt) throws ValueException
	{
		abort(4105, "Can't get alphabet value of " + kind(), ctxt.getVdmContext());
		return null;
	}
	

}
