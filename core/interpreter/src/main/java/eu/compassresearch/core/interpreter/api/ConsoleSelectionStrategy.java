package eu.compassresearch.core.interpreter.api;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.api.transitions.AbstractSilentTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;

public class ConsoleSelectionStrategy implements SelectionStrategy
{

	Scanner scanIn = new Scanner(System.in);
	private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
	private boolean hideSilentTransitions;
	CmlTransitionSet availableChannelEvents;

	public ConsoleSelectionStrategy()
	{
		setHideSilentTransitions(true);
	}

	public ConsoleSelectionStrategy(boolean hideSilentTransitions)
	{
		this.setHideSilentTransitions(hideSilentTransitions);
	}

	private boolean isSystemSelect(CmlTransitionSet availableChannelEvents)
	{

		Set<AbstractSilentTransition> silentTransitions = availableChannelEvents.getSilentTransitionsAsSet();
		// don't let the system run the divergent processes since it will never stop
		int count = 0;

		for (AbstractSilentTransition st : silentTransitions)
		{
			count = st.getEventSources().iterator().next().isDivergent() ? count + 1
					: count;
		}

		return silentTransitions.size() > count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CmlTransition systemSelect(CmlTransitionSet availableChannelEvents)
	{
		rndSelect.choices(new CmlTransitionSet((SortedSet) availableChannelEvents.getSilentTransitionsAsSet()));
		return rndSelect.resolveChoice();
	}

	private void printTransitions(List<CmlTransition> events)
	{
		for (int i = 0; i < events.size(); i++)
		{
			CmlTransition obsEvent = events.get(i);
			System.out.println("[" + i + "]" + obsEvent.toString());
		}
	}

	private CmlTransition userSelect(CmlTransitionSet availableChannelEvents)
	{
		List<CmlTransition> events = new ArrayList<CmlTransition>(availableChannelEvents.getAllEvents());

		int choiceNumber = -1;
		while (choiceNumber < 0 || choiceNumber >= events.size())
		{
			printTransitions(events);
			boolean retry = false;
			try
			{
				choiceNumber = scanIn.nextInt();
			} catch (InputMismatchException e)
			{
				scanIn.next();
				retry = true;
			}
			if (choiceNumber < 0 || choiceNumber >= events.size())
			{
				retry = true;
			}

			if (retry)
			{
				choiceNumber = -1;
				System.out.println("Please try again!");
			}
		}

		CmlTransition chosenEvent = events.get(choiceNumber);

		if (chosenEvent instanceof LabelledTransition
				&& !((LabelledTransition) chosenEvent).getChannelName().isPrecise())
		{
			Console.readChannelNameValues((LabelledTransition) chosenEvent);
		}

		return chosenEvent;
	}

	public boolean isHideSilentTransitions()
	{
		return hideSilentTransitions;
	}

	public void setHideSilentTransitions(boolean hideSilentTransitions)
	{
		this.hideSilentTransitions = hideSilentTransitions;
	}

	@Override
	public void choices(CmlTransitionSet availableTransitions)
	{
		this.availableChannelEvents = availableTransitions;
	}

	@Override
	public CmlTransition resolveChoice()
	{
		// At this point we don't want the internal transition to propagate
		// to the user, so we randomly choose all the possible internal transitions
		// before we let anything through to the user
		if (isSystemSelect(availableChannelEvents) && isHideSilentTransitions())
		{
			CmlTransition t = systemSelect(availableChannelEvents);
			System.out.println("The system picked: " + t);
			return t;
		} else
		{
			CmlTransition t = userSelect(availableChannelEvents);
			System.out.println("The environment picked: " + t);
			return t;
		}
	}

}
