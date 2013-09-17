package eu.compassresearch.core.interpreter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class ConsoleSelectionStrategy implements SelectionStrategy {
	
	Scanner scanIn = new Scanner(System.in);
	private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
	private boolean hideSilentTransitions;
	
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
		return availableChannelEvents.getSilentTransitions().size() > 0;
	}
	
	private CmlTransition systemSelect(CmlTransitionSet availableChannelEvents)
	{
		return rndSelect.select(new CmlTransitionSet((Set)availableChannelEvents.getSilentTransitions()));
	}
	
	private CmlTransition userSelect(CmlTransitionSet availableChannelEvents)
	{
		System.out.println("Available events : ");
		List<CmlTransition> events = new ArrayList<CmlTransition>(availableChannelEvents.getAllEvents());

		for(int i = 0; i <  events.size();i++)
		{
			CmlTransition obsEvent = events.get(i);
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		CmlTransition chosenEvent = events.get(scanIn.nextInt());

		if(chosenEvent instanceof LabelledTransition && !((LabelledTransition)chosenEvent).getChannelName().isPrecise())
		{
			LabelledTransition chosenChannelEvent = (LabelledTransition)chosenEvent;
			ChannelNameValue channnelName = chosenChannelEvent.getChannelName(); 
			
			for(int i = 0 ; i < channnelName.getValues().size() ; i++ )
			{
				Value currentValue = channnelName.getValues().get(i);
				
				if(!AbstractValueInterpreter.isValueMostPrecise(currentValue))
				{
					System.out.println("Enter value : "); 
					Value val;
					try {
						val = channnelName.getChannel().getValueTypes().get(i).apply(new ValueParser());
						channnelName.updateValue(i, val);
					} catch (AnalysisException e) {
						e.printStackTrace();
						System.exit(-1);
					}
				}
			}
		}
		
		return chosenEvent;
	}

	@Override
	public CmlTransition select(CmlTransitionSet availableChannelEvents) {

		//At this point we don't want the internal transition to propagate 
		//to the user, so we randomly choose all the possible internal transitions
		//before we let anything through to the user
		if(isSystemSelect(availableChannelEvents) && isHideSilentTransitions())
			return systemSelect(availableChannelEvents);
		else
			return userSelect(availableChannelEvents);
	}


	public boolean isHideSilentTransitions()
	{
		return hideSilentTransitions;
	}


	public void setHideSilentTransitions(boolean hideSilentTransitions)
	{
		this.hideSilentTransitions = hideSilentTransitions;
	}
	
}
