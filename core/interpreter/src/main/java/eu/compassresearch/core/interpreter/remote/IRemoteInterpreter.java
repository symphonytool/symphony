package eu.compassresearch.core.interpreter.remote;

import java.util.Set;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public interface IRemoteInterpreter
{
	/**
	 * Gets the currently available events for the interpreter in the current transition
	 * 
	 * @return
	 */
	public Set<? extends CmlTransition> getEvents();

	/**
	 * Selects an event that has no arguments
	 * 
	 * @param event
	 */
	public void select(CmlTransition event);

	/**
	 * Selects an event that takes arguments. The number of arguments must be equal to that of getArgumentCount(event)
	 * 
	 * @param event
	 *            the event
	 * @param arguments
	 *            string representation of the events
	 */
	public void select(CmlTransition event, String... arguments);

	/**
	 * Checks if an event requires arguments
	 * 
	 * @param event
	 * @return
	 */
	public boolean requireArguments(CmlTransition event);

	/**
	 * Gets the count of arguments for the event
	 * 
	 * @param event
	 * @return
	 */
	public int getArgumentCount(CmlTransition event);

	/**
	 * Checks if the interpreter has finished its execution
	 * 
	 * @return
	 */
	public boolean isFinished();
	
	/**
	 * Gets the current state of the interpreter
	 * @return {@link CmlInterpreterState#FAILED} if an error occurred otherwise any of the available states.
	 */
	public CmlInterpreterState getState();
}
