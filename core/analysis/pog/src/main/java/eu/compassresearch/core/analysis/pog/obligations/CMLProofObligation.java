/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;


import org.overture.ast.node.INode;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IProofObligation;

abstract public class CMLProofObligation extends ProofObligation implements
		IProofObligation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final CMLPOType cmltype;

	public CMLProofObligation(INode node, CMLPOType kind, IPOContextStack ctxt)
	{
		super(node, kind.toOverturePOType(), ctxt);
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
				+ valuetree.toString();
	}
}