package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;
import org.overture.pog.pub.IPOContextStack;

public class CmlNonZeroTimeObligation extends CmlProofObligation
{
//	public final PExp exp; 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmlNonZeroTimeObligation(PExp exp, IPOContextStack ctxt)
	{
		//TODO : Need to change Proof Oblig Type to allow new PO types - this is not a Non-Zero PO
		super(exp, CmlPOType.NON_ZERO_TIME, ctxt);
		//FIXME implement ast based PO predicate

	}

}
