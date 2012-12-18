package eu.compassresearch.core.interpreter.util;

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
}
