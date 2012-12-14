package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlSpecialEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableCmlEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlAlphabet {

	private final Set<ObservableCmlEvent> observableEvents;
	private final Set<CmlSpecialEvent> specialEvents;
	
	public CmlAlphabet()
	{
		this.observableEvents = new HashSet<ObservableCmlEvent>();
		this.specialEvents = new HashSet<CmlSpecialEvent>();
	}
	
	public CmlAlphabet(Set<ObservableCmlEvent> comms, Set<CmlSpecialEvent> specialEvents)
	{
		this.observableEvents = comms;
		this.specialEvents = specialEvents;
	}
	
	public CmlAlphabet(Set<CmlEvent> events)
	{
		this.observableEvents = new HashSet<ObservableCmlEvent>();
		this.specialEvents = new HashSet<CmlSpecialEvent>();
		
		for(CmlEvent e : events)
		{
			if(e instanceof ObservableCmlEvent)
				this.observableEvents.add((ObservableCmlEvent)e);
			else if(e instanceof CmlSpecialEvent)
				this.specialEvents.add((CmlSpecialEvent)e);
		}
	}
	
	public Set<ObservableCmlEvent> getObservableEvents()
	{
		return new HashSet<ObservableCmlEvent>(observableEvents);
	}
	
	public Set<CmlSpecialEvent> getSpecialEvents()
	{
		return new HashSet<CmlSpecialEvent>(specialEvents);
	}
	
	public Set<CmlEvent> getAllEvents()
	{
		HashSet<CmlEvent> allEvents = new HashSet<CmlEvent>();
		
		allEvents.addAll(observableEvents);
		allEvents.addAll(specialEvents);
		
		return allEvents;
	}
	
	public CmlAlphabet union(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.addAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
	}
	
	public CmlAlphabet union(CmlEvent event)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.add(event);
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * Calculates the intersection between this and other.
	 * @param other The CmlAlphabet that this will be intersected with
	 * @return the intersection of this and other
	 */
	public CmlAlphabet intersect(CmlAlphabet other)
	{
		Set<CmlEvent> resultSet = this.getAllEvents();
		resultSet.retainAll(other.getAllEvents());
		
		return new CmlAlphabet(resultSet);
		
//		Set<CmlEvent> resultSet = new HashSet<CmlEvent>();
//		for(CmlEvent e : this.getAllEvents())
//		{
//			if((e instanceof CmlCommunicationEvent && other.containsCommunication((CmlCommunicationEvent)e)) || 
//				(e instanceof CmlSpecialEvent && other.containsSpecialEvent((CmlSpecialEvent)e)))
//				resultSet.add(e);
//		}
//		
//		return new CmlAlphabet(resultSet);
		
		
	}
	
	/**
	 * Discards event that are not in the intersection of this and other but combines the
	 * the same events with different sources. 
	 * E.g. if we have 
	 * 
	 * a -> Skip ||| a -> Skip
	 * 
	 * then we have 'a' from the first (refered to as a_1) and 'a' from the second (refered to as a_2) action so
	 * alpha(a -> Skip).discardAndCombine(alpha(a -> Skip)) == alpha(a_1,a_2)
	 * @return
	 */
	public CmlAlphabet discardAndCombine(CmlAlphabet other, boolean mergeEvents)
	{
		Set<CmlEvent> resultSet = new HashSet<CmlEvent>();
		for(CmlEvent e : this.getAllEvents())
		{
			if(e instanceof CmlSpecialEvent && other.containsSpecialEvent((CmlSpecialEvent)e))
				resultSet.add(e);
			else
			{
				for(Iterator<ObservableCmlEvent> it = other.observableEvents.iterator() ; it.hasNext();)		
				{
					ObservableCmlEvent listComEvent = it.next();
					ObservableCmlEvent comEvent = (ObservableCmlEvent)e;
					if(listComEvent.equals(comEvent))
					{
						if(mergeEvents)
						{
							//resultSet.add(comEvent.createSyncEventWith(null,listComEvent,null));
						}
						else
						{
							resultSet.add(e);
							resultSet.add(listComEvent);
						}
					}
				}
			}
		}
		
		return new CmlAlphabet(resultSet);
	}
	
	/**
	 * This determines whether the alphabet contains a communication event where the
	 * ChannelValue of this is equal to the ChannelValue of comevent. 
	 * The source of the event has no influence here
	 * Communication events are both read, write and signalling (or synchronisation) channel events 
	 * @return true if the communication event is contained else false
	 */
	public boolean containsCommunication(ObservableCmlEvent comevent)
	{
		return (findCommunicationsByChannel(comevent.getChannel()).isEmpty() ? false : true);
	}
	
//	public boolean containsCommunication(CmlCommunicationEvent comevent,boolean iqnoreSource)
//	{
//		Set<CmlCommunicationEvent> events = findCommunicationsByChannel(comevent.getChannel());
//		if(iqnoreSource)
//			return !events.isEmpty();
//		else 
//		{
//			return events.contains(comevent);
//		}
//	}
	
	
//	public boolean containsCommunication(CmlCommunicationEvent comevent)
//	{
//		Set<CmlCommunicationEvent> found = findCommunicationsByChannel(comevent.getChannel());
//
//	}
	
	private Set<ObservableCmlEvent> findCommunicationsByChannel(CMLChannelValue channelValue)
	{
		Set<ObservableCmlEvent> resultSet = new HashSet<ObservableCmlEvent>();
		for(ObservableCmlEvent listComEvent : observableEvents)		
		{
			if(listComEvent.getChannel().equals(channelValue))
				resultSet.add(listComEvent);
		}
		
		return resultSet;
	}
	
//	/**
//	 * This determines whether the alphabet contains a specific communication event originating 
//	 * from a specific process source. So this Checks the channel value for equality and checks if 
//	 * the source of comevent exists in this.
//	 * Communication events are both read, write and signalling (or synchronisation) channel events 
//	 * @return true if the communication event is contained else false
//	 */
//	public boolean containsCommunicationFromSpecificSource(CmlCommunicationEvent comevent)
//	{
//		return this.communicationEvents.contains(comevent);
//	}
	
//	/**
//	 * This determines whether the alphabet contains a specific communication event. Where Both the channel value
//	 * and the CmlProcess sources are check for equality.Communication events are both read, write and 
//	 * signalling (or synchronisation) channel events. 
//	 * @return True if the communication event there exist a communication event with the same name and sources 
//	 * 		   else false
//	 */
//	public boolean containsCommunication(CmlCommunicationEvent com)
//	{
//		return communicationEvents.contains(com);
//	}
	
	/**
	 * This determines whether the alphabet contains a specific special event like e.g. the tau event
	 * @return true if the special event is contained else false
	 */
	public boolean containsSpecialEvent(CmlSpecialEvent specialEvent)
	{
		return specialEvents.contains(specialEvent);
	}
	
	public boolean isEmpty()
	{
		return observableEvents.isEmpty() && specialEvents.isEmpty();
	}
	
}
