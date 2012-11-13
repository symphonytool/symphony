package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.lex.LexNameToken;

public interface ChannelObserver {

	public void onChannelEvent(CmlChannelEvent event);
	
	public LexNameToken name();
}
