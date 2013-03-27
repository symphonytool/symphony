package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ConcreteBehaviourThread;
import eu.compassresearch.core.interpreter.cml.DefaultSupervisorEnvironment;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.scheduler.SchedulingPolicy;
import eu.compassresearch.core.interpreter.scheduler.VanillaScheduler;

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
	public static CmlSupervisorEnvironment newCmlSupervisorEnvironment(CmlCommunicationSelectionStrategy selectStrategy, CmlScheduler cmlScheduler)
	{
		return new DefaultSupervisorEnvironment(selectStrategy,cmlScheduler);
	}
	
	/**
	 * Create a scheduler with a specified Scheduling policy
	 * @param policy
	 * @return
	 */
	public static CmlScheduler newScheduler(SchedulingPolicy policy)
	{
		return new VanillaScheduler(policy);
	}
	
	public static CmlBehaviourThread newCmlBehaviourThread(INode processNode, Context context, LexNameToken processName)
	{
		return new ConcreteBehaviourThread(processNode, context, processName);
	}
	
	public static CmlBehaviourThread newCmlBehaviourThread(INode processNode, Context context, LexNameToken processName, CmlBehaviourThread parent)
	{
		return new ConcreteBehaviourThread(processNode, context, processName, parent);
	}
}
