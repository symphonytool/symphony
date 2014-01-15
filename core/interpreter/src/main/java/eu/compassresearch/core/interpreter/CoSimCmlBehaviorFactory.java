package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour.BehaviourName;
import eu.compassresearch.core.interpreter.cosim.DelegatedCmlBehaviour;
import eu.compassresearch.core.interpreter.cosim.IProcessBehaviourDelegationManager;

/**
 * Custom {@link CmlBehaviorFactory} which replaces the instantiation of behaviors from the default
 * {@link ConcreteCmlBehaviour} with a {@link DelegatedCmlBehaviour} when it is externally provided by a client. <br/>
 * This class is intended to be used for co-simulation when running as a server
 * 
 * @author kel
 */
public class CoSimCmlBehaviorFactory implements CmlBehaviorFactory
{
	private final IProcessBehaviourDelegationManager delegationManager;

	public CoSimCmlBehaviorFactory(
			IProcessBehaviourDelegationManager delegationManager)
	{
		this.delegationManager = delegationManager;
	}

	@Override
	public CmlBehaviour newCmlBehaviour(INode node, Context context,
			CmlBehaviour parent) throws AnalysisException
	{
		AProcessDefinition def = null;

		if (node instanceof AReferenceProcess)
		{
			def = ((AReferenceProcess) node).getProcessDefinition();
		} else
		{
			def = node.getAncestor(AProcessDefinition.class);
		}

		if (def != null)
		{
			String name = def.getName().getName();
			if (delegationManager.hasDelegate(name))
			{
				return new DelegatedCmlBehaviour(node, context, parent, new BehaviourName("Child of "
						+ parent.getName()), this, this.delegationManager.getDelegate(name));
			}
		}
		return new ConcreteCmlBehaviour(node, context, parent, this);
	}

	@Override
	public CmlBehaviour newCmlBehaviour(INode node, Context context,
			BehaviourName name, CmlBehaviour parent) throws AnalysisException
	{

		AProcessDefinition def = null;

		if (node instanceof AReferenceProcess)
		{
			def = ((AReferenceProcess) node).getProcessDefinition();
		} else
		{
			def = node.getAncestor(AProcessDefinition.class);
		}

		if (def != null)
		{
			String defName = def.getName().getName();
			if (delegationManager.hasDelegate(defName))
			{
				return new DelegatedCmlBehaviour(node, context, parent, new BehaviourName("Child of "
						+ parent.getName()), this, this.delegationManager.getDelegate(defName));
			}
		}

		return new ConcreteCmlBehaviour(node, context, name, parent, this);
	}

}