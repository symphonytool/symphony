/******************
 *
 *	Inspired by Proof Obligation List from Overture
 *
 *******************/

package eu.compassresearch.core.analysis.proofobligationgenerator;

import java.util.Vector;

public class ProofObligationList extends Vector<ProofObligation>
{
	// Convenience class to hold list of POs.
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (ProofObligation po: this)
		{
			sb.append("Proof Obligation ");
			sb.append(": (");
			sb.append(po.status);
			sb.append(")\n");
			sb.append(po);
			sb.append("\n");
		}

		return sb.toString();
	}
}
