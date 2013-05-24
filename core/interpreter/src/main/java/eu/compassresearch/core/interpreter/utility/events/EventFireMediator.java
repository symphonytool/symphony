package eu.compassresearch.core.interpreter.utility.events;

public interface EventFireMediator<O, E> {

	public void fireEvent(O observer, Object source, E event);
	
}
