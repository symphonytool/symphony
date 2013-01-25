package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexLocation;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POType;
import org.overture.pog.obligation.ProofObligation;

public class CMLNonZeroTimeObligation extends ProofObligation
{
	public CMLNonZeroTimeObligation(PExp exp, POContextStack ctxt)
	{
		//TODO : Need to change Proof Oblig Type to allow new PO types - this is not a Non-Zero PO
		super(exp.getLocation(), POType.NON_ZERO, ctxt);
		value = ctxt.getObligation(getExp(exp));
	}
	
	private String getExp(PExp exp)
	{		
		return (exp + " <> 0");
	}
}

