package eu.compassresearch.core.interpreter.util;

import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.actions.ACommunicationAction;

public class CmlActionAssistant {

	/**
	 * Determines if the communication AST node is a simple prefix
	 * @param node
	 * @return
	 */
	public static boolean isPrefixEvent(ACommunicationAction node)
	{
		return node.getCommunicationParameters().isEmpty();
	}
	
	public static LexNameToken extractNameFromStateDesignator(PExp exp)
	{
		LexNameToken name = null;
		
		if(exp instanceof AVariableExp)
			name = ((AVariableExp)exp).getName();
		
		return name;
	}
}
