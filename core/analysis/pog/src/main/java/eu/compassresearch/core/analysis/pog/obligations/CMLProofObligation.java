/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.intf.lex.ILexLocation;

abstract public class CMLProofObligation
{

	public final ILexLocation location;
	public final CMLPOType cmltype;
	public final String name;
	public POStatus status;
	public String guiString;
	public String isabelleString;
	
    public CMLProofObligation(ILexLocation location, CMLPOType kind,
	    CMLPOContextStack ctxt) {
    	this.location = location;
		this.name = ctxt.getName();
		this.status = POStatus.UNPROVED;
		this.cmltype = kind;
    }

	abstract public String toGUIString(CMLPOContextStack ctxt);
	
	abstract public String toIsabelleString(CMLPOContextStack ctxt);

	@Override
	public String toString()
	{
		return name + ": " + cmltype + " obligation " + location + "\n" + guiString;
	}
}