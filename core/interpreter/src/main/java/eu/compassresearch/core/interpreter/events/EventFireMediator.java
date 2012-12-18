package eu.compassresearch.core.interpreter.events;

public interface EventFireMediator<O, E> {

	public void fireEvent(O observer, Object source, E event);
	
}
