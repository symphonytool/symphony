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
//	private Value value;
//	private List<CommunicationParameter> params = new Vector<CommunicationParameter>();
	
	public CommunicationEvent(CmlBehaviour source, ChannelNameValue channelName)
	{
		super(source,channelName);
//		this.params = params;
//		initValueFromComParams();		
	}
	
	public CommunicationEvent(ChannelNameValue channelName, List<CommunicationParameter> params)
	{
		super(channelName);
//		this.params = params;
//		initValueFromComParams();
	}
			
	private CommunicationEvent(Set<CmlBehaviour> sources, ChannelNameValue channelName)
	{
		super(sources,channelName);
//		this.value = value;
	}
	
//	//Converts communication parameters into their corresponding value
//	private void initValueFromComParams()
//	{
//		if(params != null)
//		{
//			//simple value
//			if(params.size() == 1)
//				value = (Value)params.get(0).getValue().clone(); 
//			//We have a product type value
//			else
//			{
//				ValueList argvals = new ValueList();
//				for(CommunicationParameter comParam : params)
//					argvals.add((Value)comParam.getValue().clone());
//				value = new TupleValue(argvals);
//			}
//		}
//		//if this is instantiated from a channelexpression and the channel type is a Tuple
//		//the it must be a tuple with anyvalues
//		else if(((AChannelType)channel.getType()).getType() instanceof AProductType)
//		{
//			ValueList argvals = new ValueList();
//			AProductType productType = (AProductType)((AChannelType)channel.getType()).getType();
//			for(PType t : productType.getTypes())
//				argvals.add(new AnyValue(t));
//			value = new TupleValue(argvals);
//		}
//		else
//			value = new AnyValue(new AChannelType());
//	}
	
	@Override 
	public String toString() 
	{
//		StringBuilder strBuilder = new StringBuilder(channelName.getName());
//		if(value instanceof TupleValue)
//			for(Value val : ((TupleValue)value).values )
//				strBuilder.append("." + val);
//		else
//			strBuilder.append("." + value);
//		//strBuilder.append(" : " + getEventSources());
//		
//		return strBuilder.toString();
		return channelName.toString();
	};
	
	@Override 
	public int hashCode() {
		
		StringBuilder strBuilder = new StringBuilder(channelName.getChannel().getName());
		strBuilder.append(((AChannelType)channelName.getChannel().getType()).getType());
//		for(CommunicationParameter param : params)
//			strBuilder.append(param.getClass().getSimpleName());
		
		
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
	
//	@Override
//	public Value getValue() {
//
//		return value;
//	}
//	
//	@Override
//	public void setValue(Value value) {
//		this.value = value;
//	}
	
	@Override
	public boolean isPrecise() {
		return channelName.isPrecise();
	}
	
	@Override
	public CmlAlphabet getAsAlphabet() {

		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent){

		CommunicationEvent otherComEvent = (CommunicationEvent)syncEvent;
		ChannelNameValue meetValue = this.getChannelName().meet(((ChannelEvent)otherComEvent).getChannelName());
		
		if(meetValue != null && isContraintValid(meetValue,otherComEvent))
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
	
	private boolean isContraintValid(Value value, CommunicationEvent otherComEvent)
	{
		boolean result = true;
		try
		{
			if(otherComEvent.getChannelName().getValues().size() == 1 && 
					otherComEvent.params.get(0) instanceof InputParameter)
				result &= ((InputParameter)otherComEvent.params.get(0)).evaluateContraint(value);
			
			if(this.params.size() == 1 && 
					this.params.get(0) instanceof InputParameter)
				result &= ((InputParameter)this.params.get(0)).evaluateContraint(value);				
			
		}
		catch(AnalysisException ex)
		{
			ex.printStackTrace();
			result = false;
		}
		
		return result;
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
