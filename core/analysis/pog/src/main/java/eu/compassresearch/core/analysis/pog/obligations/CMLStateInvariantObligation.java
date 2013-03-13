package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.StateInvariantObligation;

public class CMLStateInvariantObligation extends StateInvariantObligation
{

	

	
	
	//TODO We need to have a node for process invariants!
	
	public CMLStateInvariantObligation(AClassInvariantDefinition def,
			POContextStack ctxt)
	{
		
		super(def, ctxt);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
