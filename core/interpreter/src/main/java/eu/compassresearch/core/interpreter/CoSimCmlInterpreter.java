package eu.compassresearch.core.interpreter;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.cosim.CoSimulationClient;
import eu.compassresearch.core.interpreter.cosim.communication.Utils;

/**
 * This is a custom interpreter implementation that changes the  VanillaCmlInterpreter such that a co-simulation
 * client can delegate the control flow to a remote coordinator.
 * 
 * @author kel
 */
public class CoSimCmlInterpreter extends VanillaCmlInterpreter
{
	private Thread executionThread = null;
	private CoSimulationClient client;

	boolean runningInternalExecution = false;

	/**
	 * The active (currently executing) behaviour
	 */
	private CmlBehaviour activeBehaviour;

	public CoSimCmlInterpreter(List<PDefinition> definitions, Config config,
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
		transitions = extractSilentTransitions(availableEvents, transitions);

		if (transitions == null || transitions.isEmpty())
		{
			// no silent transitions are available to we must sync with the coordinator
			try
			{
				SortedSet<CmlTransition> set = new TreeSet<CmlTransition>();
				set.add(client.getExecutableTransition());
				transitions = new CmlTransitionSet(set);
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
			CmlTransitionSet availableEvents, CmlTransitionSet transitions)
	{
		for (CmlTransition transition : availableEvents.filterByType(TauTransition.class))
		{
			runningInternalExecution = true;
			transitions = new CmlTransitionSet(transition);
			break;
		}
		return transitions;
	}

	public CmlTransitionSet inspect() throws AnalysisException
	{
		CmlTransitionSet tmp = internalInspect();
		while (runningInternalExecution)
		{
			Utils.milliPause(10);

			tmp = internalInspect();
			if (tmp != null)
			{
				break;
			}
		}

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

	public void stop()
	{
		setNewState(CmlInterpreterState.FINISHED);
		if (executionThread != null)
		{
			executionThread.interrupt();
		}
	}
}
