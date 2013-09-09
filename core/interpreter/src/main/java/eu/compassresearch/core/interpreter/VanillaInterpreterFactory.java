package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public final class VanillaInterpreterFactory {

	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSources
	 *            - List of parsed and type-checked CML source to interpret
	 * @throws CmlInterpreterException 
	 */
	public static CmlInterpreter newInterpreter(List<PSource> cmlSources) throws CmlInterpreterException {
		return new VanillaCmlInterpreter(cmlSources);
	}
	
	/**
	 * create an instance of the Vanilla interpreter.
	 * 
	 * @param cmlSource
	 *            - A single parsed and type-checked CML source to interpret
	 * @throws CmlInterpreterException 
	 */
	public static CmlInterpreter newInterpreter(PSource cmlSource) throws CmlInterpreterException {
		return new VanillaCmlInterpreter(cmlSource);
	}
	
	/**
	 * Creates a new default CmlSupervisorEnvironment
	 * @param selectStrategy
	 * @return
	 */
	public static CmlSupervisorEnvironment newDefaultCmlSupervisorEnvironment(SelectionStrategy selectStrategy)
	{
		return new DefaultSupervisorEnvironment(selectStrategy);
	}
	
}
