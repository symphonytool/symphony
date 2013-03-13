package eu.compassresearch.core.interpreter.cml.events;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;
import eu.compassresearch.core.interpreter.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.values.AnyValue;

public class CmlCommunicationEvent extends ObservableEvent {

	final protected List<CommunicationParameter> params;
	private Value value;
	
	public CmlCommunicationEvent(CmlBehaviourThread source, CmlChannel channel, List<CommunicationParameter> params)
	{
		super(source,channel);
		this.params = params;
		
		//TODO: this have to be expanded to all of them
		if(this.params != null)
			value = this.params.get(0).getValue();
		else
			value = new AnyValue();
	}
	
	private CmlCommunicationEvent(CmlBehaviourThread source, CmlChannel channel,List<CommunicationParameter> params, Value value)
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
		return new CmlCommunicationEvent(null, channel, params,value);
	}

	@Override
	public Value getValue() {

		return value;
	}
	
	@Override
	public void setValue(Value value) {
		this.value = value;
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
		return new SynchronizedCommunicationEvent(source, channel, this, syncEvent);
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

	
	@Override
	public List<ObservableEvent> expand() {
		
		if(isValuePrecise())
			return Arrays.asList((ObservableEvent)this);
		else
			try {
				return ((AChannelType)channel.getType()).getType().apply(new EventExpander());
			} catch (AnalysisException e) {
				e.printStackTrace();
				return new LinkedList<ObservableEvent>();
			}
	}
	
	class EventExpander extends AnswerCMLAdaptor<List<ObservableEvent> >
	{
		@Override
		public List<ObservableEvent> defaultPType(PType node)
				throws AnalysisException {
			
			return Arrays.asList((ObservableEvent)CmlCommunicationEvent.this);
		}
		
		@Override
		public List<ObservableEvent> caseAIntNumericBasicType(AIntNumericBasicType node)
				throws AnalysisException {

			return Arrays.asList((ObservableEvent)CmlCommunicationEvent.this);
		}
		
		@Override
		public List<ObservableEvent> caseANamedInvariantType(ANamedInvariantType node)
				throws AnalysisException {
			//TODO remove unwanted onces
			return node.getType().apply(this);
		}
		
		@Override
		public List<ObservableEvent> caseAUnionType(AUnionType node) throws AnalysisException {
			
			List<ObservableEvent> events = new LinkedList<ObservableEvent>();
			
			if(!node.getInfinite())
			{
				for(PType type : node.getTypes())
				{
					events.addAll(type.apply(this));
				}
			}
			else
				events.add(CmlCommunicationEvent.this);
			
			return events;
		}
		
		@Override
		public List<ObservableEvent> caseAQuoteType(AQuoteType node)
				throws AnalysisException {
			
			return Arrays.asList((ObservableEvent)new CmlCommunicationEvent(
					CmlCommunicationEvent.this.getEventSource(), 
					CmlCommunicationEvent.this.channel, CmlCommunicationEvent.this.params, 
					new QuoteValue(node.getValue().value)));
		}
	}
	
	
}
