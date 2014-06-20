package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;

import eu.compassresearch.ast.actions.PAction;

public class CmlActionRefineObligation extends CmlProofObligation {

	private PAction fromAction = null;
    private PAction toAction = null;	


	public CmlActionRefineObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location, IPogAssistantFactory af) throws AnalysisException {
		super(node, kind, ctxt, location, af);
		// TODO Auto-generated constructor stub
	}

	public CmlActionRefineObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location,
			PAction f, PAction t, IPogAssistantFactory af) throws AnalysisException {
		super(node, kind, ctxt, location, af);
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
