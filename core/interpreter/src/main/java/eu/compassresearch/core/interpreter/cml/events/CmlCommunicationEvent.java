package eu.compassresearch.core.interpreter.cml.events;

import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.List;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlIOChannel;
import eu.compassresearch.core.interpreter.util.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.values.AnyValue;

public class CmlCommunicationEvent extends ObservableValueEvent {

	final protected List<CommunicationParameter> params;
	private Value value;
	
	public CmlCommunicationEvent(CmlBehaviourThread source, CmlIOChannel<Value> channel, List<CommunicationParameter> params)
	{
		super(source,channel);
		this.params = params;
		
		//TODO: this have to be expanded to all of them
		if(this.params != null)
			value = this.params.get(0).getValue();
		else
			value = new AnyValue();
	}
	
	private CmlCommunicationEvent(CmlBehaviourThread source, CmlIOChannel<Value> channel,List<CommunicationParameter> params, Value value)
	{
		super(source,channel);
		this.params = params;
		this.value = value;
	}
	
	@Override 
	public String toString() 
	{
		StringBuilder strBuilder = new StringBuilder(channel.getName());
		//for(CommunicationParameter param : params)
			strBuilder.append("." + value);
		
		return strBuilder.toString();
	};
	
	@Override 
	public int hashCode() {
		
		StringBuilder strBuilder = new StringBuilder(channel.getName());
		strBuilder.append(((AChannelType)channel.getType()).getType());
//		for(CommunicationParameter param : params)
//			strBuilder.append(param.getClass().getSimpleName());
		
		
		return strBuilder.toString().hashCode() + (this.eventSource != null ? this.eventSource.hashCode() : "null".hashCode());
	}
	
	public boolean hasSource()
	{
		return this.eventSource != null;
	}
	
	public boolean equalsIqnoreSource(CmlCommunicationEvent other)
	{
		return other.getChannel().equals(getChannel());
	}
	
	@Override
	public boolean equals(Object obj) {

		CmlCommunicationEvent other = null;
		
		if(!(obj instanceof CmlCommunicationEvent))
			return false;
		
		other = (CmlCommunicationEvent)obj;
		
		return other.getChannel().equals(getChannel()) && 
				other.getEventSource() == getEventSource() &&
				(other.getValue().equals(this.getValue()) );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObservableEvent getReferenceEvent() {
		return new CmlCommunicationEvent(null, (CmlIOChannel<Value>)channel, params,value);
	}

	@Override
	public Value getValue() {

		return value;
	}
	
	@Override
	public void setMostPreciseValue(Value value) {
		this.value = AbstractValueInterpreter.meet(this.value, value);
	}
	
	@Override
	public boolean isValuePrecise() {
		return AbstractValueInterpreter.isValuePrecise(value);
	}
	
	@Override
	public CmlAlphabet getAsAlphabet() {

		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(CmlBehaviourThread source,
			ObservableEvent syncEvent) {
		return new SynchronizedCommunicationEvent(source, channel, this, (ObservableValueEvent)syncEvent);
	}

	@Override
	public ObservableEvent meet(ObservableEvent obj) {
		
		CmlCommunicationEvent other = null;
		
		if(!(obj instanceof CmlCommunicationEvent))
			return this;
		
		other = (CmlCommunicationEvent)obj;
		
		if(AbstractValueInterpreter.isMorePrecise(getValue(), other.getValue()))
			return this;
		else
			return other;
	}

}
