package eu.compassresearch.ide.refinementtool;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.contexts.POContextStack;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.core.analysis.pog.obligations.CmlPOType;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.core.analysis.pog.visitors.CmlPogAssistantFactory;

public class CmlRefineProvisoObligation extends CmlProofObligation {

	PExp proviso;
	
	public CmlRefineProvisoObligation(INode node, CmlPOType kind,
			IPOContextStack ctxt, ILexLocation location) throws AnalysisException {
		super(node, kind, ctxt, location, new CmlPogAssistantFactory());
		// TODO Auto-generated constructor stub
	}
	
	public CmlRefineProvisoObligation(PExp p) throws AnalysisException {
		super(null, CmlPOType.REFINE_PROVISO, new POContextStack(), null, new CmlPogAssistantFactory());
		proviso = p;
		
	}
	
	public String toString() {
		CmlPExprPrettyPrinter pp = new CmlPExprPrettyPrinter();
		try {
			return proviso.apply(pp);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getName() {
		return "Refinement Proviso";
	}

	@Override
	public String getFullPredString() {
		try {
			String s = proviso.apply(new CmlPExprPrettyPrinter()); 
			return s;
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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
