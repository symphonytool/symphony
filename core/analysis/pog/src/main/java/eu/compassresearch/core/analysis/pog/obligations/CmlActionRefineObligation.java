package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.actions.PAction;

public class CmlActionRefineObligation extends CmlProofObligation {

	private PAction fromAction = null;
    private PAction toAction = null;	


	public CmlActionRefineObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location) {
		super(node, kind, ctxt, location);
		// TODO Auto-generated constructor stub
	}

	public CmlActionRefineObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location,
			PAction f, PAction t) {
		super(node, kind, ctxt, location);
		fromAction = f;
		toAction = t;
	}
	
	public String toString() {
		return fromAction.toString() + " [= " + toAction.toString();
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
