package eu.compassresearch.core.interpreter;

import java.util.HashMap;
import java.util.Map;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.EventFireMediator;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.debug.Breakpoint;

abstract class AbstractCmlInterpreter implements CmlInterpreter {

	/**
	 * Event handler for notifying when the interpreter status changes 
	 */
	protected EventSourceHandler<CmlInterpreterStatusObserver, InterpreterStatusEvent> statusEventHandler =
			new EventSourceHandler<CmlInterpreterStatusObserver, InterpreterStatusEvent>(this, 
					new EventFireMediator<CmlInterpreterStatusObserver, InterpreterStatusEvent>() {

				@Override
				public void fireEvent (
						CmlInterpreterStatusObserver observer,
						Object source, InterpreterStatusEvent event) {

					observer.onStatusChanged(source, event);

				}
			});
	
	/**
	 * A map of the active breakpoints where the key has the following format
	 * "<filepath>:<linenumber>"
	 */
	protected Map<String,Breakpoint> 	breakpoints = new HashMap<>();
	protected CmlBehaviour	   		   	runningTopProcess 	= null;
	protected SelectionStrategy 		environment;
	/**
	 * The current state of the interpreter
	 */
	private CmlInterpretationStatus      	currentState = null;

	/**
	 * Set the new state of the interpreter
	 */
	protected void setNewState(CmlInterpretationStatus newState) 
	{
		if(currentState != newState)
		{
			currentState = newState;
			statusEventHandler.fireEvent(new InterpreterStatusEvent(this, currentState));
		}
	}

	/**
	 * Retrieves the current state of the interpreter
	 * @return The current state of the interpreter
	 */
	public CmlInterpretationStatus getStatus()
	{
		return currentState;
	}

	@Override
	public SelectionStrategy getEnvironment()
	{
		return environment;
	}

	@Override
	public EventSource<CmlInterpreterStatusObserver> onStatusChanged() {

		return statusEventHandler;
	}
	
	//Breakpoints
	@Override
	public boolean addBreakpoint(Breakpoint bp) {
		
		String key = bp.getFile() + ":" + bp.getLine();
		
		if(breakpoints.containsKey(key))
			return false;
		else{
			breakpoints.put(key, bp);
			return true;
		}
	}
	
	@Override
	public CmlBehaviour findBehaviorById(int id) {
		
		return findBehaviorById(this.runningTopProcess,id);
	}
	
	private CmlBehaviour findBehaviorById(CmlBehaviour behavior, int id) {
		
		if(behavior.getId() == id)
			return behavior;
		else if(behavior.getLeftChild() != null)
		{
			return findBehaviorById(behavior.getLeftChild(),id);
		}
		else if(behavior.getRightChild() != null)
		{
			return findBehaviorById(behavior.getRightChild(),id);
		}
		
		return null;
	}
}
