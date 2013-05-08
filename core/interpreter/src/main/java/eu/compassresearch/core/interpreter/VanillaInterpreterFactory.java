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
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.CmlEventSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.ConcreteCmlBehaviour;
import eu.compassresearch.core.interpreter.cml.DefaultSupervisorEnvironment;

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
	public static CmlSupervisorEnvironment newCmlSupervisorEnvironment(CmlEventSelectionStrategy selectStrategy)
	{
		return new DefaultSupervisorEnvironment(selectStrategy);
	}
	
	public static CmlBehaviour newCmlBehaviour(INode processNode, Context context, ILexNameToken processName) throws AnalysisException
	{
		return new ConcreteCmlBehaviour(processNode, context, processName);
	}
	
//	public static CmlBehaviour newCmlBehaviour(INode node, CmlBehaviour parent, CmlBehaviour left, CmlBehaviour right)
//	{
//		return new ConcreteCmlBehaviour(node, context, processName);
//	}
	
	public static CmlBehaviour newCmlBehaviour(INode processNode, Context context, ILexNameToken processName, CmlBehaviour parent) throws AnalysisException
	{
		return new ConcreteCmlBehaviour(processNode, context, processName, parent);
	}

	public static CmlBehaviour newCmlBehaviour(INode processNode, Context context, CmlBehaviour parent) throws AnalysisException
	{
		return new ConcreteCmlBehaviour(processNode, context, new LexNameToken("", "TMP",new LexLocation()), parent);
	}
	
//	public static CmlBehaviour newCmlBehaviourTest(INode processNode, Context context, CmlBehaviour parent)
//	{
//		try {
//			return processNode.apply(new ActionFactoryVisitor(parent),context);
//		} catch (AnalysisException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
		
}
