package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;

public class SpecSeqRefineLaw implements IRefineLaw {

	final static String MIDCONDITION = "Intermediate Condition";
	
	@Override
	public String getName() {
		return "Sequential Introduction";
	}

	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof ASpecificationStm);
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node) {
		
		CmlPExprPrettyPrinter cmlpp = new CmlPExprPrettyPrinter();
		ASpecificationStm spec = (ASpecificationStm) node;
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
//		PExp pre = spec.getPrecondition().clone();
		PExp post = spec.getPostcondition().clone();
		List<AExternalClause> w = (List<AExternalClause>) spec.getExternals().clone();
		
		String mid = metas.get(MIDCONDITION);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		sb.append(RefinePrettyPrinter.printFrame(w));

		if (spec.getPrecondition() != null) {
			sb.append("pre ");
			try {
				sb.append(spec.getPrecondition().apply(cmlpp));
			} catch (AnalysisException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sb.append(" ");
		}

		sb.append("post " + mid);

		sb.append("] ; [");
		sb.append(RefinePrettyPrinter.printFrame(w));
		
		sb.append("pre " + mid);
		sb.append(" ");
		
		if (post != null) {
			sb.append("post ");
			try {
				sb.append(post.apply(cmlpp));
			} catch (AnalysisException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			sb.append("post true");
		}
		
		sb.append("]");		
		
		return new Refinement(sb.toString(), pos);
	}

	@Override
	public List<String> getMetaNames() {
		List<String> l = new LinkedList<String>();
		l.add(MIDCONDITION);
		return l;
	}

}
