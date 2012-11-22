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

import org.overture.pog.obligation.ProofObligation;

@SuppressWarnings("serial")
// msg to compiler to ignore 'serial' warnings
public class CMLProofObligationList extends Vector<ProofObligation> {

    @Override
    // override superclass method
    public String toString() {
	StringBuilder sb = new StringBuilder();

	for (ProofObligation po : this) {
	    sb.append(po.toString());
	}

	return sb.toString();
    }

}
