package eu.compassresearch.core.interpreter.api;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.SynchronousQueue;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.debug.CmlDebugger;
import eu.compassresearch.core.interpreter.debug.SocketServerCmlDebugger;
import eu.compassresearch.core.interpreter.debug.TransitionDTO;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;

public class DebugAnimationStrategy implements SelectionStrategy
{
	private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
	CmlTransitionSet availableChannelEvents;

	private List<TransitionDTO> waitingChoices;

	/**
	 * Transition Choice Queue
	 */
	private SynchronousQueue<TransitionDTO> choiceQueue;

	// public AnnimationStrategy(List<TransitionDTO> waitingChoices,
	// SynchronousQueue<TransitionDTO> choiceQueue)
	// {
	// this.waitingChoices = waitingChoices;
	// this.choiceQueue = choiceQueue;
	// }

	private boolean isSystemSelect(CmlTransitionSet availableChannelEvents)
	{
		return availableChannelEvents.getSilentTransitionsAsSet().size() > 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CmlTransition systemSelect()
	{
		this.rndSelect.choices(new CmlTransitionSet((SortedSet) this.availableChannelEvents.getSilentTransitionsAsSet()));
		return rndSelect.resolveChoice();
	}

	private CmlTransition userSelect() throws InterruptedException
	{
		// //Wait for a transition choice taken by the user
		TransitionDTO choice = this.choiceQueue.take();

		CmlTransition selectedEvent = null;
		// For now we just search naively to find the event
		for (CmlTransition transition : this.availableChannelEvents.getAllEvents())
		{
			if (System.identityHashCode(transition) == choice.getTransitionObjectId())
			{
				selectedEvent = transition;
			}
		}

		if (selectedEvent instanceof LabelledTransition
				&& !((LabelledTransition) selectedEvent).getChannelName().isPrecise())
		{
			Console.readChannelNameValues((LabelledTransition) selectedEvent);
		}

		return selectedEvent;
	}

	@Override
	public void choices(CmlTransitionSet availableTransitions)
	{
		this.availableChannelEvents = availableTransitions;

		// We only convert the CmlTransitions into DTO's if no silent transitions
		// can occur, since we only send the transitions to Eclipse if so.
		if (!isSystemSelect(this.availableChannelEvents))
		{
			this.waitingChoices.clear();
			this.waitingChoices.addAll(convertTransitionsToChoices(this.availableChannelEvents, this.waitingChoices));
		}
	}

	@Override
	public CmlTransition resolveChoice()
	{

		// At this point we don't want the internal transition to propagate
		// to the user, so we randomly choose all the possible internal transitions
		// before we let anything through to the user
		if (isSystemSelect(this.availableChannelEvents))
		{
			return systemSelect();
		} else
		{

			try
			{
				return userSelect();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				return null;
			} finally
			{
				this.waitingChoices.clear();
			}
		}

	}

	private List<TransitionDTO> convertTransitionsToChoices(
			CmlTransitionSet availableTransitions, List<TransitionDTO> choices)
	{
		List<TransitionDTO> convertedTransitionObjs = new LinkedList<TransitionDTO>();

		for (CmlTransition transition : this.availableChannelEvents.getAllEvents())
		{
			// First find all the locations of the transition sources
			List<ILexLocation> locations = new LinkedList<ILexLocation>();
			for (CmlBehaviour source : transition.getEventSources())
			{
				INode node = source.getNextState().first;
				locations.add(LocationExtractor.extractLocation(node));
			}

			convertedTransitionObjs.add(new TransitionDTO(System.identityHashCode(transition), transition.toString(), locations));
		}

		return convertedTransitionObjs;
	}

	/**
	 * Initializes the strategy
	 */
	public void initialize(CmlInterpreter interpreter, CmlDebugger debugger)
	{
		interpreter.setSuspendBeforeTermination(true);

		if (debugger instanceof SocketServerCmlDebugger)
		{
			SocketServerCmlDebugger s = (SocketServerCmlDebugger) debugger;
			this.waitingChoices = s.waitingChoices;
			this.choiceQueue = s.choiceQueue;
		}

	}

}
