package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.statements.AAltNonDeterministicStm;
import org.overture.ast.statements.PStm;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;

class ActionVisitorHelper
{

	public static List<AAltNonDeterministicStm> findAllTrueAlternatives(
			List<AAltNonDeterministicStm> alts, Context question,
			QuestionAnswerCMLAdaptor<Context, Value> cmlExpressionVisitor)
			throws AnalysisException
	{
		List<AAltNonDeterministicStm> availableAlts = new LinkedList<AAltNonDeterministicStm>();

		for (AAltNonDeterministicStm alt : alts)
		{
			Value val = alt.getGuard().apply(cmlExpressionVisitor, question);
			// if(val.isUndefined())
			// throw new CmlInterpreterException(alt,"You are trying to use an undefined value in : " + alt + " at " +
			// alt.getLocation());
			if (val.boolValue(question))
				availableAlts.add(alt);
		}

		return availableAlts;
	}
	
	public static AStmAction wrapStatement(PStm stm)
	{
		return new AStmAction(stm.getLocation(), stm);
	}
}
