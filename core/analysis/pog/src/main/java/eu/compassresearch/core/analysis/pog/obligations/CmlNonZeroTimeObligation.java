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
		super(exp, CmlPOType.NON_ZERO_TIME, ctxt, exp.getLocation());
		//FIXME implement ast based PO predicate

	}

}
