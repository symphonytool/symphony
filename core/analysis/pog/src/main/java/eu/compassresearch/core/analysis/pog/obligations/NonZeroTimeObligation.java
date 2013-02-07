//TEMPORARILY COMPIED FROM OVERTURE UNTIL CML NON ZERO TIME CLASS IS USED

package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POType;
import org.overture.pog.obligation.ProofObligation;

public class NonZeroTimeObligation extends ProofObligation {

	
	public NonZeroTimeObligation(PExp exp, POContextStack ctxt)
	{
		//TODO : Need to change Proof Oblig Type to allow new PO types - this is not a Non-Zero PO
		super(exp.getLocation(), POType.NON_ZERO, ctxt);
		value = ctxt.getObligation(exp + " <> 0");
	}
}
