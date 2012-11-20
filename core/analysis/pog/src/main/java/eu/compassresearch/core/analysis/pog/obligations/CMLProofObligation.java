/*******************************************************************************
 *
 * Inspired by Proof Obligation class from Overture
 * Class to act as Proof Obligation Datatype
 *
 ******************************************************************************/
package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.lex.LexLocation;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POType;

/**
 * Core stuff needed
 */


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
