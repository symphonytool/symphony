/******************
 *
 * Inspired by Proof Obligation List from Overture
 * Class as complex list Datatype, stores collection 
 * of Proof Obligation objects
 *
 *******************/

package eu.compassresearch.core.analysis.pog.obligations;

import java.util.Vector;

import org.overture.pog.obligation.ProofObligation;

import eu.compassresearch.core.common.AnalysisArtifact;

@SuppressWarnings("serial")
public class CMLProofObligationList extends Vector<ProofObligation> implements AnalysisArtifact {

    @Override
    // override superclass method
    public String toString() {
    	StringBuilder sb = new StringBuilder();

    	for (ProofObligation po : this) {
    		sb.append(po.toString());
    	}

	return sb.toString();
    }
   
    //Method to output a collection of Proof Obligations for Isabelle
//	public String toIsabelle() {
//
//		for (CMLProofObligation po : this) {
//		    
//		}
//	}
}