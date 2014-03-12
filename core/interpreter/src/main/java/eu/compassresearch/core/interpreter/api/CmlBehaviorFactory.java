package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.CmlBehaviour.BehaviourName;

public interface CmlBehaviorFactory
{
	public CmlBehaviour newCmlBehaviour(INode action, Context context,
			CmlBehaviour parent) throws AnalysisException;

	public CmlBehaviour newCmlBehaviour(INode action, Context context,
			BehaviourName name, CmlBehaviour parent) throws AnalysisException;
}
