package eu.compassresearch.core.interpreter.events;

import org.overture.ast.lex.LexNameToken;


public interface ChannelObserver {

	public void onChannelEvent(CmlChannelEvent event);
}
