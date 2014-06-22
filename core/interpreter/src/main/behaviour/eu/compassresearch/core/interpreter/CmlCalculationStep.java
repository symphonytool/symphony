package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.Pair;

interface CmlCalculationStep
{

	Pair<INode, Context> execute(CmlTransition selectedTransition)
			throws AnalysisException;
}
