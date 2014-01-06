package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour.BehaviourName;

public class DefaultCmlBehaviorFactory implements CmlBehaviorFactory
{

	@Override
	public CmlBehaviour newCmlBehaviour(INode action, Context context,
			CmlBehaviour parent) throws AnalysisException
	{
		return new ConcreteCmlBehaviour(action, context, parent, this);
	}

	@Override
	public CmlBehaviour newCmlBehaviour(INode action, Context context,
			BehaviourName name, CmlBehaviour parent) throws AnalysisException
	{
		return new ConcreteCmlBehaviour(action, context, name, parent, this);
	}

}
