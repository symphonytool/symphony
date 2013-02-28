package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.core.interpreter.eval.CmlValueEvaluator;

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
	
	public static LexNameToken extractNameFromStateDesignator(PExp exp, Context context)
	{
		LexNameToken name = null;
		
		if(exp instanceof AVariableExp)
		{
			name = ((AVariableExp)exp).getName();
		}
		
		return name;
	}
	
	public static List<ANonDeterministicAltStatementAction> findAllTrueAlts(
			List<ANonDeterministicAltStatementAction> alts,
			Context question,CmlValueEvaluator cmlEvaluator) throws AnalysisException
	{
		List<ANonDeterministicAltStatementAction> availableAlts = new LinkedList<ANonDeterministicAltStatementAction>();
		
		for(ANonDeterministicAltStatementAction alt :  alts)		
			if(alt.getGuard().apply(cmlEvaluator,question).boolValue(question))
				availableAlts.add(alt);
		
		return availableAlts;
	}
}
