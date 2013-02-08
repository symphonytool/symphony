package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;

public class ConsoleSelectionStrategy implements
CmlCommunicationSelectionStrategy {
	
	Scanner scanIn = new Scanner(System.in);

	@Override
	public ObservableEvent select(CmlAlphabet availableChannelEvents) {

		System.out.println("Available events : ");
		List<ObservableEvent> events = new ArrayList<ObservableEvent>(availableChannelEvents.getObservableEvents());

		for(int i = 0; i <  events.size();i++)
		{
			ObservableEvent obsEvent = events.get(i);
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		ObservableEvent chosenEvent = events.get(scanIn.nextInt());
		

		if(!chosenEvent.isValuePrecise())
		{
			System.out.println("Enter value : "); 
			Value val = new IntegerValue(scanIn.nextInt());
			chosenEvent.setValue(val);
		}
		
		return chosenEvent;
	}

}
