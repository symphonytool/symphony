package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;

public class CMLNonZeroTimeObligation extends CMLProofObligation
{
	public final PExp exp; 
	
	public CMLNonZeroTimeObligation(PExp exp, CMLPOContextStack ctxt)
	{
		//TODO : Need to change Proof Oblig Type to allow new PO types - this is not a Non-Zero PO
		super(exp.getLocation(), CMLPOType.NON_ZERO_TIME, ctxt);
		this.exp = exp;
		this.guiString = toGUIString(ctxt);
		this.isabelleString = toIsabelleString(ctxt);
	}

	public String toGUIString(CMLPOContextStack ctxt)
	{
		return ctxt.getGUIString(exp + " <> 0");
	}
	
	public String toIsabelleString(CMLPOContextStack ctxt)
	{
		//TODO: need to determine Isabelle format 
		return ctxt.getIsabelleString("");
	}
}
