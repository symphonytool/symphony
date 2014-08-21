package eu.compassresearch.core.interpreter;

import eu.compassresearch.core.interpreter.cosim.IProcessBehaviourDelegationManager;

/**
 * Interpreter factory for the coordinator. This factory changes the instantiation of behaviors to allow for delegated
 * behaviors.
 * 
 * @author kel
 */
public final class CoSimCoordinatorInterpreterFactory extends
		VanillaInterpreterFactory
{

	public CoSimCoordinatorInterpreterFactory(
			IProcessBehaviourDelegationManager server)
	{
		super();
		this.cmlBehaviorFactory = new CoSimCmlBehaviorFactory(server, delegatedListener);
	}

}
