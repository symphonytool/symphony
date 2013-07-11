package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.pog.IPOContextStack;

public class CMLStateInvariantObligation  extends CMLProofObligation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CMLStateInvariantObligation(AClassInvariantDefinition node, 
			IPOContextStack ctxt)
	{
		super(node, CMLPOType.STATE_INVARIANT, ctxt);
		//FIXME implement ast based PO predicate

	}

	
}
