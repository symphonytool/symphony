package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.interpreter.eval.DelegateStatementEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

public class CmlStatementEvaluator extends DelegateStatementEvaluator
{
	/*
	 * We need to override this case since overture expects the name of an AIdentifierStateDesignator to contain the
	 * module (this is added by the vdm SyntaxReader l352) of the containing class. This is not added by the cml parser
	 * so we need to remove the explicit true to be able to find in the vdm intepreter
	 */
	@Override
	public Value caseAIdentifierStateDesignator(
			AIdentifierStateDesignator node, Context ctxt)
			throws AnalysisException
	{
		// We lookup the name in a context comprising only state...
		// return ctxt.lookup(node.getName().getExplicit(true));
		return ctxt.lookup(node.getName());
	}

}
