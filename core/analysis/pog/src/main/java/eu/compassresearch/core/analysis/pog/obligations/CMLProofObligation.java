/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.lex.LexLocation;
import org.overture.pog.obligation.POContextStack;

/**
 * This class is used to extend and wrap the overture {@link org.overture.pog.obligation.ProofObligation ProofObligation} class.
 * It does so by converting the {@link eu.compassresearch.core.analysis.pog.obligation.CMLPOType CMLPOType} enums to the appropriate overture ones.
 */

// Not being used much yet. Will probably only be needed when we get to the CML bits.
abstract public class CMLProofObligation extends org.overture.pog.obligation.ProofObligation 
{
    
   CMLPOType cmltype;
   
   

    /**
     * 
     */
    private static final long serialVersionUID = 3530006044901617326L;

    public CMLProofObligation(LexLocation location, CMLPOType kind,
	    POContextStack ctxt) {
	super(location, kind.toOverturePOType(), ctxt);
	this.cmltype=kind;
    }



}
