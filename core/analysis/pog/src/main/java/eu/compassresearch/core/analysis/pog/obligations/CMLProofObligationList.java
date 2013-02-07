/******************
 *
 * Inspired by Proof Obligation List from Overture
 * Class as complex list Datatype, stores collection 
 * of Proof Obligation objects
 *
 *******************/

package eu.compassresearch.core.analysis.pog.obligations;

import java.util.Vector;

@SuppressWarnings("serial")
public class CMLProofObligationList extends Vector<CMLProofObligation>{

    @Override
    // override superclass method
    public String toString() {
    	StringBuilder sb = new StringBuilder();

    	for (CMLProofObligation po : this) {
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