package eu.compassresearch.core.interpreter;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.cosim.CoSimulationClient;

/**
 * This is a custom interpreter implementation that changes the VanillaCmlInterpreter such that a co-simulation client
 * can delegate the control flow to a remote coordinator.
 * 
 * @author kel
 */
public class CoSimClientInterpreter extends VanillaCmlInterpreter implements
		ICoSimClientInterpreter
{
	private Thread executionThread = null;
	private CoSimulationClient client;

	boolean runningInternalExecution = false;

	/**
	 * The active (currently executing) behaviour
	 */
	private CmlBehaviour activeBehaviour;

	public CoSimClientInterpreter(List<PDefinition> definitions, Config config,
			CoSimulationClient client)
	{
		super(definitions, config);
		this.client = client;
		this.client.setInterpreter(this);
	}

	@Override
	protected void executeTopProcess(CmlBehaviour behaviour)
			throws AnalysisException, InterruptedException
	{
		this.activeBehaviour = behaviour;
		super.executeTopProcess(behaviour);
	}

	@Override
	public CmlTransition resolveChoice(CmlTransitionSet availableEvents)
	{
		executionThread = Thread.currentThread();

		CmlTransitionSet transitions = null;

		// let this simulator execute all non-observable
		transitions = extractSilentTransitions(availableEvents);

		if (transitions == null || transitions.isEmpty())
		{
			// no silent transitions are available to we must sync with the coordinator
			try
			{
				SortedSet<CmlTransition> set = new TreeSet<CmlTransition>();
				final CmlTransition executableTransition = client.getExecutableTransition();
				set.add(executableTransition);

				if (executableTransition instanceof TauTransition)
				{
					System.out.println("client got tau from server, and local avaliable are: "
							+ availableEvents);
					if (availableEvents.isEmpty())
					{
						throw new InterpreterRuntimeException("internal error, missing events: "
								+ availableEvents);
					}
					transitions = extractSilentTransitions(availableEvents);
				} else
				{
					transitions = new CmlTransitionSet(set);
				}

			} catch (InterruptedException e)
			{
				if (getState() == CmlInterpreterState.FINISHED)
				{
					return null;
				}
				throw new InterpreterRuntimeException("Unable to get avaliable transitions from client", e);
			}
		}
		return super.resolveChoice(transitions);
	}

	protected synchronized CmlTransitionSet extractSilentTransitions(
			CmlTransitionSet availableEvents)
	{
		CmlTransitionSet transitions = availableEvents.filterByType(TauTransition.class);
		return transitions;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.ICoSimClientInterpreter#inspect()
	 */
	@Override
	public CmlTransitionSet inspect() throws AnalysisException
	{
		CmlTransitionSet tmp = internalInspect();

		System.out.println("Client wants to reply with: " + tmp);

		if (tmp == null || tmp.hasType(TauTransition.class))
		{
			TauTransition tau = new TauTransition(activeBehaviour, new ASkipAction(), "skip");
			tmp = new CmlTransitionSet(tau);
			System.out.println("Client replies with: " + tmp);
			return tmp;
		}

		tmp = tmp.removeAllType(TauTransition.class);

		if (tmp.isEmpty())
		{
			TauTransition tau = new TauTransition(activeBehaviour, new ASkipAction(), "skip");
			System.out.println("Internal interpreter state: " + this.getState());
			tmp = new CmlTransitionSet(tau);
		}

		System.out.println("Client replies with: " + tmp);

		return tmp;

	}

	private synchronized CmlTransitionSet internalInspect()
			throws AnalysisException
	{
		if (!runningInternalExecution)
		{
			return super.inspect(this.activeBehaviour);
		}
		return null;
	}

	@Override
	protected void executeBehaviour(CmlBehaviour behaviour)
			throws AnalysisException
	{
		this.runningInternalExecution = true;
		super.executeBehaviour(behaviour);
		this.runningInternalExecution = false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.ICoSimClientInterpreter#stop()
	 */
	@Override
	public void stop()
	{
		setNewState(CmlInterpreterState.FINISHED);
		if (executionThread != null)
		{
			executionThread.interrupt();
		}
	}
}
