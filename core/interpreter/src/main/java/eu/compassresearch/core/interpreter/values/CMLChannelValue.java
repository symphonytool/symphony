package eu.compassresearch.core.interpreter.values;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CMLChannel;
import eu.compassresearch.core.interpreter.cml.CMLChannelEvent;
import eu.compassresearch.core.interpreter.cml.CMLChannelSignal;
import eu.compassresearch.core.interpreter.cml.CMLCommunicationType;
import eu.compassresearch.core.interpreter.cml.CMLInputChannel;
import eu.compassresearch.core.interpreter.cml.CMLOutputChannel;
import eu.compassresearch.core.interpreter.cml.ChannelObserver;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CMLChannelValue extends Value implements CMLChannel, CMLChannelSignal,CMLOutputChannel<Value> ,CMLInputChannel<Value> 
{

	private LexNameToken 					name;
	private PType 							channelType;
	private Value							value = null; 
	private List<ChannelObserver> 			signalObservers = new LinkedList<ChannelObserver>();
	private List<ChannelObserver> 			readObservers = new LinkedList<ChannelObserver>();
	private List<ChannelObserver> 			writeObservers = new LinkedList<ChannelObserver>();
	
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
		
		return otherValue.getName().equals(getName()) &&
				otherValue.getType().equals(getType());
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
		notifyObservers(readObservers,CMLCommunicationType.READ);
		return value;
	}

	@Override
	public void write(Value value) {
		this.value= value; 
		notifyObservers(writeObservers,CMLCommunicationType.WRITE);
	}

	@Override
	public void signal() {
		notifyObservers(signalObservers,CMLCommunicationType.SIGNAL);
	}
	
	private void notifyObservers(List<ChannelObserver> observers, CMLCommunicationType eventType)
	{
		for(ChannelObserver observer : observers)
		{
			observer.onChannelEvent(new CMLChannelEvent(this, eventType));
		}
	}

	@Override
	public void registerOnChannelRead(ChannelObserver observer) {
		readObservers.add(observer);
		CmlRuntime.logger().finest(observer.name() + " registered on "+ this.toString() + " for onChannelRead events");
	}

	@Override
	public void unregisterOnChannelRead(ChannelObserver observer) {
		readObservers.remove(observer);		
		CmlRuntime.logger().finest(observer.name() + " unregistered on "+ this.toString() + " for onChannelRead events");
	}

	@Override
	public void registerOnChannelWrite(ChannelObserver observer) {
		writeObservers.add(observer);
		CmlRuntime.logger().finest(observer.name() + " registered on "+ this.toString() + " for onChannelWrite events");
	}

	@Override
	public void unregisterOnChannelWrite(ChannelObserver observer) {
		writeObservers.remove(observer);
		CmlRuntime.logger().finest(observer.name() + " unregistered on "+ this.toString() + " for onChannelWrite events");
	}

	@Override
	public void registerOnChannelSignal(ChannelObserver observer) {
		signalObservers.add(observer);
		CmlRuntime.logger().finest(observer.name() + " registered on "+ this.toString() + " for onChannelSignal events");
	}

	@Override
	public void unregisterOnChannelSignal(ChannelObserver observer) {
		signalObservers.remove(observer);		
		CmlRuntime.logger().finest(observer.name() + " unregistered on "+ this.toString() + " for onChannelSignal events");
	}

}
