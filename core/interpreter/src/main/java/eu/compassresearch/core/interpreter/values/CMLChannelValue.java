package eu.compassresearch.core.interpreter.values;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationType;
import eu.compassresearch.core.interpreter.cml.channels.CmlIOChannel;
import eu.compassresearch.core.interpreter.cml.channels.CmlSignalChannel;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;

public class CMLChannelValue extends CmlValue implements CmlSignalChannel, CmlIOChannel<Value>
{

	private LexNameToken 					name;
	private PType 							channelType;
	private Value							value = null; 

	//
	
	private class ChannelEventMediator implements EventFireMediator<ChannelObserver,CmlChannelEvent>
	{
		@Override
		public void fireEvent(ChannelObserver observer, Object source,
				CmlChannelEvent event) {
			observer.onChannelEvent(CMLChannelValue.this,event);			
		}
	}
	
	private EventSourceHandler<ChannelObserver,CmlChannelEvent> signalObservers = 
			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
					
	private EventSourceHandler<ChannelObserver,CmlChannelEvent> readObservers =
			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
	private EventSourceHandler<ChannelObserver,CmlChannelEvent> writeObservers = 
			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
	
	private EventSourceHandler<ChannelObserver,CmlChannelEvent> selectObservers = 
			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());

	public CMLChannelValue(PType channelType, LexNameToken name)
	{
		this.channelType = channelType;
		this.name = name;
	}
	
	public CMLChannelValue(CMLChannelValue other)
	{
		this.channelType = other.channelType;
		this.name = other.name;
		signalObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.signalObservers);
		readObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.readObservers);
		writeObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.writeObservers);
		selectObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.selectObservers);
	}
	
	@Override
	public String getName() {
		return name.getName();
	}

	@Override
	public PType getType() {
		return this.channelType;
	}

	@Override
	public String toString() {
		return kind() + " " + getName() + " : " + getType();
	}

	@Override
	public boolean equals(Object other) {
		
		CMLChannelValue otherValue = null;
		
		if(!(other instanceof CMLChannelValue))
			return false;
		
		otherValue = (CMLChannelValue)other;
		
		return otherValue.getName().equals(getName()) &&
				getType().equals(otherValue.getType());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String kind() {
		return "Channel";
	}

	@Override
	public Object clone() {
		
		return new CMLChannelValue(this);
	}

	@Override
	public Value read() {
		notifyObservers(readObservers,CmlCommunicationType.READ);
		return value;
	}

	@Override
	public void write(Value value) {
		this.value= value; 
		notifyObservers(writeObservers,CmlCommunicationType.WRITE);
	}

	@Override
	public void signal() {
		notifyObservers(signalObservers, CmlCommunicationType.SIGNAL);
	}
	
	/**
	 * Helper method to fire away the channel events
	 * @param source
	 * @param eventType
	 */
	private void notifyObservers(EventSourceHandler<ChannelObserver,CmlChannelEvent> source, CmlCommunicationType eventType)
	{
		source.fireEvent(new CmlChannelEvent(this, eventType));
	}
	
	@Override
	public EventSource<ChannelObserver> onChannelRead()
	{
		return readObservers;
	}
	
	@Override
	public EventSource<ChannelObserver> onChannelWrite()
	{
		return writeObservers;
	}
	
	@Override
	public EventSource<ChannelObserver> onChannelSignal()
	{
		return signalObservers;
	}

	@Override
	public void select() {
		notifyObservers(selectObservers, CmlCommunicationType.SELECT);
	}

	@Override
	public EventSource<ChannelObserver> onSelect() {
		return selectObservers;
	}

}
