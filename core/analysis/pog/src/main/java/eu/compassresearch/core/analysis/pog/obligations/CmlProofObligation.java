/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IProofObligation;

abstract public class CmlProofObligation extends ProofObligation implements
		IProofObligation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final CmlPOType cmltype;

	public CmlProofObligation(INode node, CmlPOType kind, IPOContextStack ctxt,
			ILexLocation location)
	{
		super(node, kind.toOverturePOType(), ctxt, location);
		cmltype = kind;

	}

	public String toGUIString()
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public String toIsabelleString()
	{
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public String toString()
	{
		return name + ": " + cmltype + " obligation " + getLocation() + "\n"
				+ getValue();
	}
}