/*******************************************************************************
 *
 *	Inspired by Proof Obligation class from Overture
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.proofobligationgenerator;

import org.overture.ast.lex.LexLocation;

abstract public class ProofObligation implements Comparable<ProofObligation>
{
	//public final LexLocation location;
	public final POType kind;
	public final String name;
	public POStatus status;

	public ProofObligation(String name, POType kind)//, LexLocation location, POContextStack ctxt)
	{
		//this.location = location;
		this.kind = kind;
		this.name = name;
		//this.name = ctxt.getName();
		this.status = POStatus.UNPROVED;
	}

	@Override
	public String toString()
	{
		return name + ": " + kind + " obligation "+ "\n" + status;
	}
	
	public int compareTo(ProofObligation other)
	{
		return 0;
	}
}
