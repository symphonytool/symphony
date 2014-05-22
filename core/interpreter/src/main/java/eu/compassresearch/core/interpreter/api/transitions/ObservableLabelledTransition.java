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
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class ObservableLabelledTransition extends AbstractCmlTransition
		implements LabelledTransition

{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2217645151439301812L;
	final protected ChannelValue channelName;

	/**
	 * Added for json construction
	 */
	protected ObservableLabelledTransition()
	{
		channelName = null;
	}

	public ObservableLabelledTransition(CmlBehaviour source,
			ChannelValue channelName)
	{
		super(source);
		this.channelName = channelName;

	}

	private ObservableLabelledTransition(SortedSet<CmlBehaviour> sources,
			ChannelValue channelName)
	{
		super(sources);
		this.channelName = channelName;
	}

	/**
	 * Synchronized constructor
	 * 
	 * @param baseEvent
	 * @param syncEvent
	 * @param meetValue
	 */
	public ObservableLabelledTransition(CmlTransition baseEvent,
			CmlTransition otherComEvent, ChannelValue meetValue)
	{
		super(baseEvent, otherComEvent);
		this.channelName = meetValue;
	}

	@Override
	public ChannelValue getChannelName()
	{
		return channelName;
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

		LabelledTransition other = null;

		if (!(obj instanceof LabelledTransition))
		{
			return false;
		}

		other = (LabelledTransition) obj;

		return other.getChannelName().equals(getChannelName())
				&& super.equals(obj);
	}

	private boolean isChannelsComparable(ObservableTransition other)
	{

		if (!(other instanceof LabelledTransition))
		{
			return false;
		}

		LabelledTransition otherChannelEvent = (LabelledTransition) other;
		return channelName.isComparable(otherChannelEvent.getChannelName());
	}

	@Override
	public boolean isSynchronizedBy(ObservableTransition other)
	{
		return isChannelsComparable(other) && this.isSourcesSubset(other);
	}

	@Override
	public boolean isSynchronizableWith(ObservableTransition other)
	{
		LabelledTransition otherLT = other instanceof LabelledTransition ? (LabelledTransition) other
				: null;

		if (isChannelsComparable(otherLT)
				&& (this.getChannelName().isGTEQPrecise(otherLT.getChannelName()) || otherLT.getChannelName().isGTEQPrecise(this.getChannelName())))
		{
			ChannelValue meetValue = this.getChannelName().meet(otherLT.getChannelName());
			try
			{
				return meetValue != null && meetValue.isConstraintValid();
			} catch (AnalysisException e)
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	@Override
	public ObservableTransition synchronizeWith(ObservableTransition syncEvent)
	{
		ObservableLabelledTransition otherComEvent = (ObservableLabelledTransition) syncEvent;
		ChannelValue meetValue = this.getChannelName().meet(((LabelledTransition) otherComEvent).getChannelName());

		// if (meetValue == null)
		// {
		// throw new
		// CmlInterpreterException(InterpretationErrorMessages.SYNC_OF_NONCOMPARABLE_EVENTS.customizeMessage(this.toString(),
		// syncEvent.toString()));
		// }

		try
		{
			if (meetValue.isConstraintValid())
			{
				return new ObservableLabelledTransition(this, otherComEvent, meetValue);
			}
		} catch (AnalysisException e)
		{
		}

		return null;
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
	public LabelledTransition rename(ChannelValue value)
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
