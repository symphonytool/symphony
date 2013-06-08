/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.pog.obligation.ProofObligation;

abstract public class CMLProofObligation extends ProofObligation
{

	public final ILexLocation location;
	public final CMLPOType cmltype;
	public String name;
	public POStatus status;
	public String guiString;
	public String isabelleString;
	
    public CMLProofObligation(ILexLocation location, CMLPOType kind,
	    CMLPOContextStack ctxt) {
    	// FIXME: Need proper inheritance between CML and Overture POs: shouldn't be null
    	super(location, kind.toOverturePOType(), null);
    	this.location = location;
		this.name = ctxt.getName();
		this.status = POStatus.UNPROVED;
		this.cmltype = kind;
    }

	abstract public String toGUIString(CMLPOContextStack ctxt);
	
	abstract public String toIsabelleString(CMLPOContextStack ctxt);

	public String getName() {
	  return name;
	}
	
	// FIXME : Add behaviour to call visitor
	public String getIsabelleString() {
		return "True";
	}
	
	@Override
	public String toString()
	{
		return name + ": " + cmltype + " obligation " + location + "\n" + guiString;
	}
}