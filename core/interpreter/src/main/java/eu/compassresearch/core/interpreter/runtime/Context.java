package eu.compassresearch.core.interpreter.runtime;

import org.overture.interpreter.values.Value;
import org.overture.typechecker.util.LexNameTokenMap;

public class Context extends LexNameTokenMap<Value>{
	

    /**
     * The enclosing Context to search if a symbol is not found in this
     */
	private Context outer;
	
	public Context()
	{
		this.outer = null;
	}
	
	public Context(Context outer)
	{
		this.outer = outer;
	}
	
}
