package eu.compassresearch.core.interpreter;

import java.util.List;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.runtime.DefaultSupervisorEnvironment;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.scheduler.Scheduler;
import eu.compassresearch.core.interpreter.scheduler.SchedulingPolicy;

public final class VanillaInterpreterFactory {

	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSources
	 *            - List of parsed and type-checked CML source to interpret
	 * @throws InterpreterException 
	 */
	public static CmlInterpreter newInterpreter(List<PSource> cmlSources) throws InterpreterException {
		return new VanillaCmlInterpreter(cmlSources);
	}
	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSource
	 *            - A single parsed and type-checked CML source to interpret
	 * @throws InterpreterException 
	 */
	public static CmlInterpreter newInterpreter(PSource cmlSource) throws InterpreterException {
		return new VanillaCmlInterpreter(cmlSource);
	}
	
	/**
	 * Creates new CmlSupervisorEnvironment
	 * @param selectStrategy
	 * @return
	 */
	public static CmlSupervisorEnvironment newCmlSupervisorEnvironment(CmlCommunicationSelectionStrategy selectStrategy, Scheduler cmlScheduler)
	{
		return new DefaultSupervisorEnvironment(selectStrategy,cmlScheduler);
	}
	
	/**
	 * Create a scheduler with a specified Scheduling policy
	 * @param policy
	 * @return
	 */
	public static Scheduler newScheduler(SchedulingPolicy policy)
	{
		return new CmlScheduler(policy);
	}
}
