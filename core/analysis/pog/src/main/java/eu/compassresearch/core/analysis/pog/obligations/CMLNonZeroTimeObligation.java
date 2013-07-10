package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;

public class CMLNonZeroTimeObligation extends CMLProofObligation
{
//	public final PExp exp; 
	
	public CMLNonZeroTimeObligation(PExp exp, CMLPOContextStack ctxt)
	{
		//TODO : Need to change Proof Oblig Type to allow new PO types - this is not a Non-Zero PO
		super(exp, CMLPOType.NON_ZERO_TIME, ctxt);
		//FIXME implement ast based PO predicate

	}

}
