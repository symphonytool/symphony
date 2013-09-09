package eu.compassresearch.core.interpreter.api.transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

class CommunicationEvent extends AbstractChannelEvent implements ObservableEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2217645151439301812L;
	
	public CommunicationEvent(CmlBehaviour source, ChannelNameValue channelName)
	{
		super(source,channelName);
	}
	
	public CommunicationEvent(ChannelNameValue channelName)
	{
		super(channelName);
	}
			
	private CommunicationEvent(Set<CmlBehaviour> sources, ChannelNameValue channelName)
	{
		super(sources,channelName);
	}
	
	@Override 
	public String toString() 
	{
		return channelName.toString();
	};
	
	@Override 
	public int hashCode() {
		
		StringBuilder strBuilder = new StringBuilder(channelName.getChannel().getName());
		strBuilder.append(((AChannelType)channelName.getChannel().getType()).getType());
		
		return strBuilder.toString().hashCode();
	}
			
	@Override
	public boolean equals(Object obj) {

		CommunicationEvent other = null;
		
		if(!(obj instanceof CommunicationEvent))
			return false;
		
		other = (CommunicationEvent)obj;
		
		return super.equals(other) &&
				(other.getChannelName().equals(this.getChannelName()) );
	}
	
	@Override
	public boolean isComparable(ObservableEvent other) {
		return super.equals(other);
	}
	
	@Override
	public boolean isPrecise() {
		return channelName.isPrecise();
	}
	
	@Override
	public CmlAlphabet getAsAlphabet() {

		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent) throws AnalysisException
	{
		CommunicationEvent otherComEvent = (CommunicationEvent)syncEvent;
		ChannelNameValue meetValue = this.getChannelName().meet(((ChannelEvent)otherComEvent).getChannelName());
		
		if(meetValue != null && meetValue.isConstraintValid())
		{
			Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
			sources.addAll(this.getEventSources());
			sources.addAll(otherComEvent.getEventSources());
			return new CommunicationEvent(sources, meetValue);
		}
		else
			//the events are not comparable!
			return null;
	}
	
	@Override
	public ObservableEvent meet(ObservableEvent obj) {
		
		CommunicationEvent other = null;
		
		if(!(obj instanceof CommunicationEvent))
			return this;
		
		other = (CommunicationEvent)obj;
		
		if(AbstractValueInterpreter.isEquallyOrMorePrecise(this.channelName, other.channelName))
			return this;
		else
			return other;
	}

	
	@Override
	public List<ChannelEvent> expand() {
		
		if(isPrecise())
			return Arrays.asList((ChannelEvent)this);
		else
			try {
				return ((AChannelType)channelName.getChannel().getType()).getType().apply(new EventExpander());
			} catch (AnalysisException e) {
				e.printStackTrace();
				return new LinkedList<ChannelEvent>();
			}
	}
	
	class EventExpander extends AnswerCMLAdaptor<List<ChannelEvent> >
	{
		@Override
		public List<ChannelEvent> defaultPType(PType node)
				throws AnalysisException {
			
			return Arrays.asList((ChannelEvent)CommunicationEvent.this);
		}
		
		@Override
		public List<ChannelEvent> caseAIntNumericBasicType(AIntNumericBasicType node)
				throws AnalysisException {

			return Arrays.asList((ChannelEvent)CommunicationEvent.this);
		}
		
		@Override
		public List<ChannelEvent> caseANamedInvariantType(ANamedInvariantType node)
				throws AnalysisException {
			//TODO remove unwanted onces
			return node.getType().apply(this);
		}
		
		@Override
		public List<ChannelEvent> caseAUnionType(AUnionType node) throws AnalysisException {
			
			List<ChannelEvent> events = new LinkedList<ChannelEvent>();
			
			if(!node.getInfinite())
			{
				for(PType type : node.getTypes())
				{
					events.addAll(type.apply(this));
				}
			}
			else
				events.add(CommunicationEvent.this);
			
			return events;
		}
		
		@Override
		public List<ChannelEvent> caseAQuoteType(AQuoteType node)
				throws AnalysisException {
			
			return Arrays.asList((ChannelEvent)new CommunicationEvent(
					CommunicationEvent.this.getEventSources(), 
					CommunicationEvent.this.channelName));
//			return Arrays.asList((ChannelEvent)new CommunicationEvent(
//					CommunicationEvent.this.getEventSources(), 
//					CommunicationEvent.this.channelName, new QuoteValue(node.getValue().getValue())));
		}
	}
	
	
}
