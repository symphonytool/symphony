package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			
			System.out.println(obsEvent.getEventSource().getExecutionState().second.getSelf().toString());
			
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		int choiceIndex = -1;
		//while(!scanIn.hasNextInt())
		choiceIndex = scanIn.nextInt();

		//scanIn.close();            

		return events.get(choiceIndex);
	}

}
