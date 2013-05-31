package eu.compassresearch.core.interpreter.api.values;

import java.rmi.RemoteException;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlChannel;
import eu.compassresearch.core.interpreter.api.events.ChannelActivity;
import eu.compassresearch.core.interpreter.api.events.ChannelEvent;
import eu.compassresearch.core.interpreter.api.events.ChannelObserver;
import eu.compassresearch.core.interpreter.utility.events.EventFireMediator;
import eu.compassresearch.core.interpreter.utility.events.EventSource;
import eu.compassresearch.core.interpreter.utility.events.EventSourceHandler;

public class CMLChannelValue extends Value implements CmlChannel //CmlIOChannel<Value>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6350630462785844551L;
	private ILexNameToken 					name;
	private PType 							channelType;
//	private Value							value = null; 

	//
	
	private class ChannelEventMediator implements EventFireMediator<ChannelObserver,ChannelEvent>
	{
		@Override
		public void fireEvent(ChannelObserver observer, Object source,
				ChannelEvent event) {
			observer.onChannelEvent(CMLChannelValue.this,event);			
		}
	}
	
//	private EventSourceHandler<ChannelObserver,CmlChannelEvent> signalObservers = 
//			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
//					
//	private EventSourceHandler<ChannelObserver,CmlChannelEvent> readObservers =
//			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
//	private EventSourceHandler<ChannelObserver,CmlChannelEvent> writeObservers = 
//			new EventSourceHandler<ChannelObserver,CmlChannelEvent>(this, new ChannelEventMediator());
	
	private EventSourceHandler<ChannelObserver,ChannelEvent> selectObservers = 
			new EventSourceHandler<ChannelObserver,ChannelEvent>(this, new ChannelEventMediator());

	public CMLChannelValue(PType channelType, ILexNameToken name)
	{
		this.channelType = channelType;
		this.name = name;
	}
	
	public CMLChannelValue(CMLChannelValue other)
	{
		this.channelType = other.channelType;
		this.name = other.name;
//		signalObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.signalObservers);
//		readObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.readObservers);
//		writeObservers = new EventSourceHandler<ChannelObserver,CmlChannelEvent>(other.writeObservers);
		selectObservers = new EventSourceHandler<ChannelObserver,ChannelEvent>(other.selectObservers);
	}
	
	@Override
	public String getName() {
		return name.getFullName();
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
		return toString().hashCode();
	}

	@Override
	public String kind() {
		return "Channel";
	}

	@Override
	public Object clone() {
		
		//return new CMLChannelValue(this);
		return this;
	}

//	@Override
//	public Value read() {
//		notifyObservers(readObservers,CmlCommunicationType.READ);
//		return value;
//	}
//
//	@Override
//	public void write(Value value) {
//		this.value= value; 
//		notifyObservers(writeObservers,CmlCommunicationType.WRITE);
//	}
//
//	@Override
//	public void signal() {
//		notifyObservers(signalObservers, CmlCommunicationType.SIGNAL);
//	}
	
	/**
	 * Helper method to fire away the channel events
	 * @param source
	 * @param eventType
	 */
	private void notifyObservers(EventSourceHandler<ChannelObserver,ChannelEvent> source, ChannelActivity eventType)
	{
		source.fireEvent(new ChannelEvent(this, eventType));
	}
	
//	@Override
//	public EventSource<ChannelObserver> onChannelRead()
//	{
//		return readObservers;
//	}
//	
//	@Override
//	public EventSource<ChannelObserver> onChannelWrite()
//	{
//		return writeObservers;
//	}
//	
//	@Override
//	public EventSource<ChannelObserver> onChannelSignal()
//	{
//		return signalObservers;
//	}

	@Override
	public void select() {
		notifyObservers(selectObservers, ChannelActivity.SELECT);
	}

	@Override
	public EventSource<ChannelObserver> onSelect() {
		return selectObservers;
	}

}
