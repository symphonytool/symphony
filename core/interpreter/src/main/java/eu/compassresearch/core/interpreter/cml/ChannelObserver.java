package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.lex.LexNameToken;

public interface ChannelObserver {

	public void onChannelEvent(CMLChannelEvent event);
	
	public LexNameToken name();
}
