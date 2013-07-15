//TEMPORARILY COMPIED FROM OVERTURE UNTIL CML NON ZERO TIME CLASS IS USED

package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;
import org.overture.pog.pub.IPOContextStack;

public class NonZeroTimeObligation extends CMLProofObligation {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonZeroTimeObligation(PExp exp, IPOContextStack ctxt)
	{
		super(exp, CMLPOType.NON_ZERO, ctxt);
		
	}
}
