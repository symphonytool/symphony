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
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.CmlChannel;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.AnyValue;

class CommunicationEvent extends AbstractChannelEvent implements ObservableEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2217645151439301812L;
	private Value value;
	private List<CommunicationParameter> params = new Vector<CommunicationParameter>();
	
	public CommunicationEvent(CmlBehaviour source, CmlChannel channel, List<CommunicationParameter> params)
	{
		super(source,channel);
		this.params = params;
		initValueFromComParams();		
	}
	
	public CommunicationEvent(CmlChannel channel, List<CommunicationParameter> params)
	{
		super(channel);
		this.params = params;
		initValueFromComParams();
	}
			
	private CommunicationEvent(Set<CmlBehaviour> sources, CmlChannel channel, Value value)
	{
		super(sources,channel);
		this.value = value;
	}
	
	//Converts communication parameters into their corresponding value
	private void initValueFromComParams()
	{
		if(params != null)
		{
			//simple value
			if(params.size() == 1)
				value = (Value)params.get(0).getValue().clone(); 
			//We have a product type value
			else
			{
				ValueList argvals = new ValueList();
				for(CommunicationParameter comParam : params)
					argvals.add((Value)comParam.getValue().clone());
				value = new TupleValue(argvals);
			}
		}
		//if this is instantiated from a channelexpression and the channel type is a Tuple
		//the it must be a tuple with anyvalues
		else if(((AChannelType)channel.getType()).getType() instanceof AProductType)
		{
			ValueList argvals = new ValueList();
			AProductType productType = (AProductType)((AChannelType)channel.getType()).getType();
			for(PType t : productType.getTypes())
				argvals.add(new AnyValue(t));
			value = new TupleValue(argvals);
		}
		else
			value = new AnyValue(new AChannelType());
	}
	
	@Override 
	public String toString() 
	{
		StringBuilder strBuilder = new StringBuilder(channel.getName());
		if(value instanceof TupleValue)
			for(Value val : ((TupleValue)value).values )
				strBuilder.append("." + val);
		else
			strBuilder.append("." + value);
		//strBuilder.append(" : " + getEventSources());
		
		return strBuilder.toString();
	};
	
	@Override 
	public int hashCode() {
		
		StringBuilder strBuilder = new StringBuilder(channel.getName());
		strBuilder.append(((AChannelType)channel.getType()).getType());
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
				(other.getValue().equals(this.getValue()) );
	}
	
	@Override
	public boolean isComparable(ObservableEvent other) {
		return super.equals(other);
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
	public boolean isPrecise() {
		return AbstractValueInterpreter.isValueMostPrecise(value);
	}
	
	@Override
	public CmlAlphabet getAsAlphabet() {

		return new CmlAlphabet(this);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent){

		CommunicationEvent otherComEvent = (CommunicationEvent)syncEvent;
		Value meetValue = AbstractValueInterpreter.meet(this.getValue(), ((ChannelEvent)otherComEvent).getValue());
		
		if(meetValue != null) //&& isContraintValid(meetValue,otherComEvent))
		{
			Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
			sources.addAll(this.getEventSources());
			sources.addAll(otherComEvent.getEventSources());
			return new CommunicationEvent(sources, channel,meetValue);
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
			if(otherComEvent.params.size() == 1 && otherComEvent.params.get(0) instanceof InputParameter)
			{
				result &= ((InputParameter)otherComEvent.params.get(0)).evaluateContraint(value);
				result &= ((InputParameter)params.get(0)).evaluateContraint(value);
			}
		}
		catch(AnalysisException ex)
		{
			ex.printStackTrace();
			result = false;
		}
		
//		for(CommunicationParameter param : otherComEvent.params)
//			if(param instanceof InputParameter)
//				((InputParameter) param).getContraintExpression()
//		
		return result;
	}

	@Override
	public ObservableEvent meet(ObservableEvent obj) {
		
		CommunicationEvent other = null;
		
		if(!(obj instanceof CommunicationEvent))
			return this;
		
		other = (CommunicationEvent)obj;
		
		if(AbstractValueInterpreter.isEquallyOrMorePrecise(getValue(), other.getValue()))
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
				return ((AChannelType)channel.getType()).getType().apply(new EventExpander());
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
					CommunicationEvent.this.channel, new QuoteValue(node.getValue().getValue())));
		}
	}
	
	
}
