package eu.compassresearch.core.interpreter.api.transitions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class ObservableLabelledTransition extends AbstractLabelledTransition
		
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2217645151439301812L;

	/**
	 * Added for json construction
	 */
	protected ObservableLabelledTransition()
	{
	}

	public ObservableLabelledTransition(CmlBehaviour source,
			ChannelNameValue channelName)
	{
		super(source, channelName);
	}

	private ObservableLabelledTransition(SortedSet<CmlBehaviour> sources,
			ChannelNameValue channelName)
	{
		super(sources, channelName);
	}

	/**
	 * Synchronized constructor
	 * 
	 * @param baseEvent
	 * @param syncEvent
	 * @param meetValue
	 */
	public ObservableLabelledTransition(CmlTransition baseEvent,
			CmlTransition otherComEvent, ChannelNameValue meetValue)
	{
		super(baseEvent, otherComEvent, meetValue);
	}

	@Override
	public String toString()
	{
		return channelName.toString();
	};

	@Override
	public int hashCode()
	{
		return this.eventSources.hashCode() + channelName.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{

		if (!(obj instanceof ObservableLabelledTransition))
		{
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public boolean isComparable(ObservableTransition other)
	{

		if (!(other instanceof LabelledTransition))
		{
			return false;
		}

		LabelledTransition otherChannelEvent = (LabelledTransition) other;
		return channelName.isComparable(otherChannelEvent.getChannelName());
	}

	@Override
	public ObservableTransition synchronizeWith(ObservableTransition syncEvent)
			throws AnalysisException
	{
		ObservableLabelledTransition otherComEvent = (ObservableLabelledTransition) syncEvent;
		ChannelNameValue meetValue = this.getChannelName().meet(((LabelledTransition) otherComEvent).getChannelName());

		if (meetValue == null)
		{
			throw new CmlInterpreterException(InterpretationErrorMessages.SYNC_OF_NONCOMPARABLE_EVENTS.customizeMessage(this.toString(), syncEvent.toString()));
		}

		if (meetValue.isConstraintValid())
		{
			return new ObservableLabelledTransition(this, otherComEvent, meetValue);
		} else
		{
			return null;
		}

	}

	// TODO implement the expanding!
	@Override
	public List<LabelledTransition> expand()
	{
		return Arrays.asList((LabelledTransition) this);
		// if(channelName.isPrecise())
		// return Arrays.asList((ChannelEvent)this);
		// else
		// try {
		// return channelName.getChannel().getValueTypes().apply(new EventExpander());
		// } catch (AnalysisException e) {
		// e.printStackTrace();
		// return new LinkedList<ChannelEvent>();
		// }
	}
	
	@Override
	public LabelledTransition rename(ChannelNameValue value)
	{
		return new ObservableLabelledTransition(this.eventSources, this.channelName.rename(value.getChannel()));
	}

	class EventExpander extends AnswerCMLAdaptor<List<LabelledTransition>>
	{
		@Override
		public List<LabelledTransition> defaultPType(PType node)
				throws AnalysisException
		{

			return Arrays.asList((LabelledTransition) ObservableLabelledTransition.this);
		}

		@Override
		public List<LabelledTransition> caseAIntNumericBasicType(
				AIntNumericBasicType node) throws AnalysisException
		{

			return Arrays.asList((LabelledTransition) ObservableLabelledTransition.this);
		}

		@Override
		public List<LabelledTransition> caseANamedInvariantType(
				ANamedInvariantType node) throws AnalysisException
		{
			// TODO remove unwanted onces
			return node.getType().apply(this);
		}

		@Override
		public List<LabelledTransition> caseAUnionType(AUnionType node)
				throws AnalysisException
		{

			List<LabelledTransition> events = new LinkedList<LabelledTransition>();

			if (!node.getInfinite())
			{
				for (PType type : node.getTypes())
				{
					events.addAll(type.apply(this));
				}
			} else
			{
				events.add(ObservableLabelledTransition.this);
			}

			return events;
		}

		@Override
		public List<LabelledTransition> caseAQuoteType(AQuoteType node)
				throws AnalysisException
		{

			return Arrays.asList((LabelledTransition) new ObservableLabelledTransition(ObservableLabelledTransition.this.getEventSources(), ObservableLabelledTransition.this.channelName));
			// return Arrays.asList((ChannelEvent)new CommunicationEvent(
			// CommunicationEvent.this.getEventSources(),
			// CommunicationEvent.this.channelName, new QuoteValue(node.getValue().getValue())));
		}

		@Override
		public List<LabelledTransition> createNewReturnValue(INode node)
		{
			return null;
		}

		@Override
		public List<LabelledTransition> createNewReturnValue(Object node)
		{
			return null;
		}
	}

}
