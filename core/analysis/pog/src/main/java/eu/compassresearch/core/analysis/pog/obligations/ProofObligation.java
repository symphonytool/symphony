/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

/**
 * Core stuff needed
 */
import eu.compassresearch.ast.lex.LexLocation;

abstract public class ProofObligation implements Comparable<ProofObligation>
{
	public final LexLocation location;
	public final POType kind;
	public final String name;

	public int number;
	public String value;
	public POStatus status;

	private int var = 1;
	
	public ProofObligation(LexLocation location, POType kind, POContextStack ctxt)
	{
		this.location = location;
		this.kind = kind;
		this.name = ctxt.getName();
		this.status = POStatus.UNPROVED;
		this.number = 0;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return name + ": " + kind + " obligation " + location + "\n" + value;
	}

	protected String getVar(String root)
	{
		return root + var++;
	}

	public int compareTo(ProofObligation other)
	{
		return number - other.number;
	}
}
