package eu.compassresearch.ide.refinementtool;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.contexts.POContextStack;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.core.analysis.pog.obligations.CmlPOType;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.core.analysis.pog.visitors.CmlPogAssistantFactory;

public class CmlActionRefineProvisoObligation extends CmlProofObligation {

	private PAction lhs;
	private PAction rhs;
	
	public CmlActionRefineProvisoObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location) throws AnalysisException {
		super(node, kind, ctxt, location, new CmlPogAssistantFactory());
		// TODO Auto-generated constructor stub
	}
	
	public CmlActionRefineProvisoObligation(PAction a1, PAction a2) throws AnalysisException {
		super(a1, CmlPOType.REFINE_PROVISO, new POContextStack(), null, new CmlPogAssistantFactory());
		lhs = a1;
		rhs = a2;
	}
	
	public String toString() {
		RefinePrettyPrinter pp = new RefinePrettyPrinter();
		try {
			return lhs.apply(pp,0) + " [= " + rhs.apply(pp,0);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getName() {
		return "Action Refinement Proviso";
	}

	@Override
	public String getFullPredString() {
		return toString();
	}

	@Override
	public ILexLocation getLocation() {
		return new LexLocation("", "", 0, 0, 0, 0, 0, 0);
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
