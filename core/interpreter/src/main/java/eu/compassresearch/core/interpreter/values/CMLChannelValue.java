package eu.compassresearch.core.interpreter.values;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationType;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannelSignal;
import eu.compassresearch.core.interpreter.cml.channels.CmlInputChannel;
import eu.compassresearch.core.interpreter.cml.channels.CmlOutputChannel;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.EventObserver;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CMLChannelValue extends Value implements CmlChannel, CmlChannelSignal,CmlOutputChannel<Value> ,CmlInputChannel<Value> 
{

	private LexNameToken 					name;
	private PType 							channelType;
	private Value							value = null; 

	//
	private EventSourceHandler<CmlChannelEvent> signalObservers = new EventSourceHandler<CmlChannelEvent>(this);
	private EventSourceHandler<CmlChannelEvent> readObservers = new EventSourceHandler<CmlChannelEvent>(this);
	private EventSourceHandler<CmlChannelEvent> writeObservers = new EventSourceHandler<CmlChannelEvent>(this);

	public CMLChannelValue(PType channelType, LexNameToken name)
	{
		this.channelType = channelType;
		this.name = name;
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
		
		return otherValue.getName().equals(getName());
		//FIXME This fails after the typechecker has been updated		
		//&&
		//otherValue.getType().equals(getType());
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
		return null;
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
	
	private void notifyObservers(EventSourceHandler<CmlChannelEvent> source, CmlCommunicationType eventType)
	{
		source.fireEvent(new CmlChannelEvent(this, eventType));
	}
	
	@Override
	public EventSource<CmlChannelEvent> onChannelRead()
	{
		return readObservers;
	}
	
	@Override
	public EventSource<CmlChannelEvent> onChannelWrite()
	{
		return writeObservers;
	}
	
	@Override
	public EventSource<CmlChannelEvent> onChannelSignal()
	{
		return signalObservers;
	}

}
