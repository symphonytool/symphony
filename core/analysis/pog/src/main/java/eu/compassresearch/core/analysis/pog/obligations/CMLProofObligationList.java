/******************
 *
 * Inspired by Proof Obligation List from Overture
 * Class as complex list Datatype, stores collection 
 * of Proof Obligation objects
 *
 *******************/

package eu.compassresearch.core.analysis.pog.obligations;

/**
 * Java libraries 
 */
import java.util.Vector;

@SuppressWarnings("serial") //msg to compiler to ignore 'serial' warnings
public class CMLProofObligationList extends Vector<org.overture.pog.obligation.ProofObligation>
{

	@Override //override superclass method
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (org.overture.pog.obligation.ProofObligation po: this)
		{
			sb.append(po.toString());
		}
		
		return sb.toString();
	}

}
