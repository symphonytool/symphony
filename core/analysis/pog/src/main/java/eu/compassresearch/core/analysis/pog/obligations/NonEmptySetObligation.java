/*******************************************************************************
 *
 * Class to represent act a PO for a  Non Empty Set;
 * With thanks to the Overture POG
 * @author Luis D Couto
 ******************************************************************************/

package eu.compassresearch.core.analysis.pog.obligations;


import org.overture.ast.expressions.PExp;

public class NonEmptySetObligation extends ProofObligation{


    public NonEmptySetObligation (PExp pexp, POContextStack contextStack){
	super(pexp.getLocation(), POType.NON_EMPTY_SET, contextStack);
	value = contextStack.getObligation(pexp + " <> {}");
    }

}
