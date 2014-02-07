package eu.compassresearch.core.interpreter.remote;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.debug.CmlDebugger;
import eu.compassresearch.core.interpreter.utility.ValueParser;

public class RemoteInterpreter implements IRemoteInterpreter, SelectionStrategy
{

	private final CmlDebugger debugger;

	private SynchronousQueue<CmlTransition> selection = new SynchronousQueue<CmlTransition>();
	private SynchronousQueue<CmlTransitionSet> availableTransitions = new SynchronousQueue<CmlTransitionSet>();

	private CmlInterpreter interpreter;

	public RemoteInterpreter(CmlDebugger debugger)
	{
		this.debugger = debugger;
		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				RemoteInterpreter.this.debugger.start(RemoteInterpreter.this);
			}
		});
		// t.setDaemon(true);
		t.start();
	}

	@Override
	public void choices(CmlTransitionSet availableTransitions)
	{
		try
		{
			this.availableTransitions.put(availableTransitions);
		} catch (InterruptedException e)
		{
			throw new InterpreterRuntimeException("Unable to store avaliable transitions", e);
		}
	}

	@Override
	public CmlTransition resolveChoice()
	{
		try
		{
			return selection.take();
		} catch (InterruptedException e)
		{
			throw new InterpreterRuntimeException("Unable to take selection provided by selected transition", e);
		}
	}

	@Override
	public void initialize(CmlInterpreter interpreter, CmlDebugger debugger)
	{
		this.interpreter = interpreter;
	}

	public boolean isFinished()
	{
		return this.interpreter != null
				&& this.interpreter.getState() != null
				&& (this.interpreter.getState() == CmlInterpreterState.FINISHED || this.interpreter.getState() == CmlInterpreterState.FAILED);
	}

	public Set<? extends CmlTransition> getEvents()
	{
		try
		{
			CmlTransitionSet tmp = null;
			boolean userSelectableEventsFound = false;
			while (!userSelectableEventsFound)
			{
				tmp = availableTransitions.take();
				if (tmp.getAllEvents().size() == 1
						&& tmp.getAllEvents().iterator().next() instanceof TauTransition)
				{
					selection.put(tmp.getAllEvents().iterator().next());
				} else
				{
					userSelectableEventsFound = true;
				}
			}

			return tmp.getAllEvents();
		} catch (InterruptedException e)
		{
			throw new InterpreterRuntimeException(e);
		}
	}

	public void select(CmlTransition event)
	{
		if (hasArguments(event))
		{
			throw new InterpreterRuntimeException("The event has arguments. Please use the select method with arguments");
		}
		try
		{
			selection.put(event);
		} catch (InterruptedException e)
		{
			throw new InterpreterRuntimeException("Unable to store selected event in queue", e);
		}
	}

	public void select(CmlTransition event, String... arguments)
	{
		if (!(event instanceof LabelledTransition))
		{
			throw new InterpreterRuntimeException("The event does not take any arguments");
		}

		LabelledTransition labelledEvent = (LabelledTransition) event;

		ChannelNameValue channnelName = labelledEvent.getChannelName();

		if (getArgumentCount(event) != arguments.length)
		{
			String msg = "Invalid number of arguments for channel %s. expected %01d actual %01d";
			throw new InterpreterRuntimeException(String.format(msg, labelledEvent.getChannelName().toString(), channnelName.getValues().size(), arguments.length));
		}

		int argIndex = -1;

		for (int i = 0; i < channnelName.getValues().size(); i++)
		{
			Value currentValue = channnelName.getValues().get(i);

			if (!AbstractValueInterpreter.isValueMostPrecise(currentValue))
			{
				Value val;
				try
				{

					PType expectedType = channnelName.getChannel().getValueTypes().get(i);
					Context ctxt = event.getEventSources().iterator().next().getNextState().second;
					String expressionString = arguments[++argIndex];
					val = ValueParser.parse(expectedType, ctxt, expressionString);
					channnelName.updateValue(i, val);
				} catch (AnalysisException e)
				{
					throw new InterpreterRuntimeException("Analysis error in read user value", e);
				} catch (IOException e)
				{
					throw new InterpreterRuntimeException("IO error in read user value", e);
				} catch (Exception e)
				{
					if (e instanceof InterpreterRuntimeException)
					{
						throw (InterpreterRuntimeException) e;
					}
					throw new InterpreterRuntimeException("Unknown internal error in read user value", e);
				}
			}
		}
		try
		{
			selection.put(event);
		} catch (InterruptedException e)
		{
			throw new InterpreterRuntimeException("Unable to store seleceted event in queue", e);
		}
	}

	@Override
	public boolean hasArguments(CmlTransition event)
	{
		return event instanceof LabelledTransition
				&& !((LabelledTransition) event).getChannelName().getValues().isEmpty();

	}

	@Override
	public int getArgumentCount(CmlTransition event)
	{
		if (hasArguments(event))
		{
			int valuesToRead = 0;

			LabelledTransition labelledEvent = (LabelledTransition) event;

			ChannelNameValue channnelName = labelledEvent.getChannelName();
			for (Value v : channnelName.getValues())
			{
				if (!AbstractValueInterpreter.isValueMostPrecise(v))
				{
					valuesToRead++;
				}
			}

			return valuesToRead;
		}
		return 0;
	}

}
