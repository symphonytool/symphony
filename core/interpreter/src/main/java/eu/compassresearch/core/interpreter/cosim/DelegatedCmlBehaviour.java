package eu.compassresearch.core.interpreter.cosim;

import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.TraceObserver;
import eu.compassresearch.core.interpreter.api.transitions.AbstractSilentTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
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
	public void execute(CmlTransition selectedTransition)
			throws AnalysisException
	{
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
			throw new InterpreterRuntimeException("Failed to invoke execute on delegate", e);
		}
	}

	@Override
	public CmlTransitionSet inspect() throws AnalysisException
	{
		try
		{
			CmlTransitionSet transitions = this.delegate.inspect();

			for (CmlTransition t : transitions.getAllEvents())
			{
				setEventSources(t, this);
			}

			return transitions;
		} catch (Exception e)
		{
			throw new InterpreterRuntimeException("Failed to invoke inspect on delegate", e);
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

		eventSourcesField.set(t, eventSources);
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
	public boolean finished()
	{
		try
		{
			return this.delegate.isFinished();
		} catch (Exception e)
		{
			throw new InterpreterRuntimeException("Failed to invoke isFinished on delegate", e);
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
