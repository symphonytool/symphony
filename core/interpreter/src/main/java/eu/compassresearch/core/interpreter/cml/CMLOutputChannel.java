package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

public interface CMLOutputChannel<T extends Value> extends CMLChannel {

	public void write(T value);
	
}
