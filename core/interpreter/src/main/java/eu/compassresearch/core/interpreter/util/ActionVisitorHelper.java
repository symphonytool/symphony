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

public class ActionVisitorHelper {

	public static List<ANonDeterministicAltStatementAction> findAllTrueAlternatives(
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
