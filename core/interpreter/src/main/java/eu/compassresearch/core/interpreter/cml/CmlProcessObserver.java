package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.lex.LexNameToken;

public interface CmlProcessObserver {

	public void onStateChange(CmlProcessStateEvent stateEvent);
	
	public LexNameToken name();
}
