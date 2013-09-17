package eu.compassresearch.core.interpreter.api.behaviour;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.Pair;

public interface CmlCalculationStep {

	Pair<INode,Context> execute(CmlTransition selectedTransition) throws AnalysisException;
}
