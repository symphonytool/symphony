package eu.compassresearch.core.interpreter.cosim;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.TraceObserver;
import eu.compassresearch.core.interpreter.api.transitions.AbstractLabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.AbstractSilentTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.api.values.NoConstraint;
import eu.compassresearch.core.interpreter.api.values.ValueConstraint;
import eu.compassresearch.core.interpreter.utility.Pair;

/**
 * Custom behavior that delegates control to a remote client {@link CoSimulationClient}
 * 
 * @author kel
 */
public class DelegatedCmlBehaviour implements CmlBehaviour
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BehaviourName name;
	private IProcessDelegate delegate;

	private Pair<INode, Context> next;

	private boolean finished = false;

	public DelegatedCmlBehaviour(INode node, Context context,
			CmlBehaviour parent, BehaviourName name,
			CmlBehaviorFactory cmlBehaviorFactory, IProcessDelegate delegate)
	{
		this.name = name;
		this.delegate = delegate;
		this.next = new Pair<INode, Context>(node, context);
	}

	@Override
	public int compareTo(CmlBehaviour o)
	{
		return 0;
	}

	@Override
	public synchronized void execute(CmlTransition selectedTransition)
			throws AnalysisException
	{
		if (finished)
		{
			return;
		}
		try
		{
			if (selectedTransition instanceof AbstractSilentTransition
					|| selectedTransition instanceof TimedTransition)
			{
				return;
			}
			this.delegate.execute(selectedTransition);
		} catch (Exception e)
		{
			throw new InterpreterRuntimeException("Failed to invoke execute on delegate \""+delegate.getProcessName()+"\"", e);
		}
	}

	@Override
	public synchronized CmlTransitionSet inspect() throws AnalysisException
	{
		if (finished)
		{
			return new CmlTransitionSet();
		}
		try
		{
			CmlTransitionSet transitions = this.delegate.inspect();

			for (CmlTransition t : transitions.getAllEvents())
			{
				setEventSources(t, this);

				typeSetChannelValue(t);
			}

			return transitions;
		} catch (Exception e)
		{
			throw new InterpreterRuntimeException("Failed to invoke inspect on delegate \""+delegate.getProcessName()+"\"", e);
		}
	}

	private void typeSetChannelValue(CmlTransition t)
	{
		if (t instanceof AbstractLabelledTransition)
		{
			ChannelNameValue channelNameValue = ((AbstractLabelledTransition) t).getChannelName();

			updateConstraints(channelNameValue);

			Value channel = next.second.lookup(channelNameValue.getChannel().name);
			if (channel instanceof CMLChannelValue)
			{
				Field channelField = null;

				List<Field> fields = Node.getAllFields(new Vector<Field>(), channelNameValue.getClass());
				for (Field field : fields)
				{
					if (field.getName().equals("channel"))
					{
						field.setAccessible(true);
						channelField = field;
						break;
					}
				}

				try
				{
					if (channelField == null)
					{
						throw new InterpreterRuntimeException("Unable to access field channel on CmlTransition");
					}
					channelField.set(channelNameValue, (CMLChannelValue) channel);
				} catch (IllegalArgumentException | IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void updateConstraints(ChannelNameValue channelNameValue)
	{
		Field constraintsField = null;

		List<Field> fields = Node.getAllFields(new Vector<Field>(), channelNameValue.getClass());
		for (Field field : fields)
		{
			if (field.getName().equals("constraints"))
			{
				field.setAccessible(true);
				constraintsField = field;
				break;
			}
		}

		try
		{
			if (constraintsField == null)
			{
				throw new InterpreterRuntimeException("Unable to access field constraints on "
						+ channelNameValue.getClass().getName());
			}
			@SuppressWarnings("unchecked")
			List<ValueConstraint> constraints = (List<ValueConstraint>) constraintsField.get(channelNameValue);

			if (constraints == null)
			{

				constraints = new LinkedList<ValueConstraint>();
				for (int i = 0; i < channelNameValue.getValues().size(); i++)
				{
					constraints.add(new NoConstraint());
				}
			}

			constraintsField.set(channelNameValue, constraints);
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setEventSources(CmlTransition t,
			DelegatedCmlBehaviour delegatedCmlBehaviour)
			throws IllegalAccessException
	{
		TreeSet<CmlBehaviour> eventSources = new TreeSet<CmlBehaviour>();
		eventSources.add(delegatedCmlBehaviour);
		setEventSources(t, eventSources);
	}

	public static void setEventSources(CmlTransition t,
			SortedSet<CmlBehaviour> eventSources) throws IllegalAccessException
	{
		Field eventSourcesField = null;

		List<Field> fields = Node.getAllFields(new Vector<Field>(), t.getClass());
		for (Field field : fields)
		{
			if (field.getName().equals("eventSources"))
			{
				field.setAccessible(true);
				eventSourcesField = field;
				break;
			}
		}

		if (eventSourcesField == null)
		{
			throw new InterpreterRuntimeException("Unable to access field eventSources on CmlTransition");
		}

		eventSourcesField.set(t, eventSources);
	}

	public static void setTransitionId(CmlTransition t, int id)
			throws IllegalAccessException
	{
		Field activeTransitionIdField = null;

		List<Field> fields = Node.getAllFields(new Vector<Field>(), t.getClass());
		for (Field field : fields)
		{
			if (field.getName().equals("activeTransitionsId"))
			{
				field.setAccessible(true);
				activeTransitionIdField = field;
				break;
			}
		}

		if (activeTransitionIdField == null)
		{
			throw new InterpreterRuntimeException("Unable to access field activeTransitionsId on CmlTransition");
		}

		activeTransitionIdField.set(t, id);
	}

	@Override
	public Pair<INode, Context> getNextState()
	{
		return next;
	}

	@Override
	public void replaceState(Context context) throws ValueException
	{

	}

	@Override
	public BehaviourName getName()
	{
		return this.name;
	}

	@Override
	public int getId()
	{
		return 0;
	}

	@Override
	public String nextStepToString()
	{
		return null;
	}

	@Override
	public long level()
	{
		return 0;
	}

	@Override
	public CmlBehaviour parent()
	{
		return null;
	}

	@Override
	public List<CmlBehaviour> children()
	{
		return null;
	}

	@Override
	public CmlBehaviour getLeftChild()
	{
		return null;
	}

	@Override
	public CmlBehaviour getRightChild()
	{
		return null;
	}

	@Override
	public boolean hasChildren()
	{
		return false;
	}

	@Override
	public boolean started()
	{
		return false;
	}

	@Override
	public synchronized boolean finished()
	{
		if (finished)
		{
			return finished;
		}
		try
		{
			this.finished = this.delegate.isFinished();
			return finished;
		} catch (Exception e)
		{
			throw new InterpreterRuntimeException("Failed to invoke isFinished on delegate \""+delegate.getProcessName()+"\"", e);
		}
	}

	@Override
	public boolean isDivergent()
	{
		return false;
	}

	@Override
	public boolean waiting()
	{
		return false;
	}

	@Override
	public boolean deadlocked() throws AnalysisException
	{
		return false;
	}

	@Override
	public CmlBehaviorState getState()
	{
		return null;
	}

	@Override
	public EventSource<CmlBehaviorStateObserver> onStateChanged()
	{
		return null;
	}

	@Override
	public CmlTrace getTraceModel()
	{
		return null;
	}

	@Override
	public long getCurrentTime()
	{
		return 0;
	}

	@Override
	public EventSource<TraceObserver> onTraceChanged()
	{
		return null;
	}
}
