package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.ANotUnaryExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlPExprPrettyPrinter;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class SpecIterRefineLaw implements IRefineLaw {
	final static String LOOPVARIANT = "Loop Variant";
	private String eol = System.getProperty("line.separator");
	
	private PExp unbracket(PExp e) {
		while (e instanceof ABracketedExp) {
			e = ((ABracketedExp)e).getExpression();
		}
		return e;
	}
	
	@Override
	public String getName() {
		return "Iteration Introduction";
	}

	@Override
	public String getDetail() {
		return "";
	}	
	
	@Override
	public boolean isApplicable(INode node) {
		CmlPExprPrettyPrinter cmlpp = new CmlPExprPrettyPrinter();
		if (node instanceof ASpecificationStm) {
			ASpecificationStm spec =  (ASpecificationStm) node;
			PExp pre = spec.getPrecondition();
			PExp post = spec.getPostcondition();
			
			if (pre != null && post != null) {
				if (post instanceof AAndBooleanBinaryExp) {
					PExp post_l = ((AAndBooleanBinaryExp) post).getLeft();
					PExp post_r = unbracket(((AAndBooleanBinaryExp) post).getRight());
					try {
						if (post_l.apply(cmlpp).equals(pre.apply(cmlpp))
								&& post_r instanceof ANotUnaryExp) {
							return true;
						}
					} catch (AnalysisException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		    
		}
			
		return false;
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {

		CmlPExprPrettyPrinter cmlpp = new CmlPExprPrettyPrinter();		
		
		ASpecificationStm spec =  (ASpecificationStm) node;
		PExp inv = spec.getPrecondition();
		PExp post = spec.getPostcondition();
		List<AExternalClause> w = spec.getExternals();
				
		PExp e = unbracket(((ANotUnaryExp) unbracket(((AAndBooleanBinaryExp) post).getRight())).getExp());

		List<PExp> guards = new LinkedList<PExp>();
		
		while (e instanceof AOrBooleanBinaryExp) {
			guards.add(((AOrBooleanBinaryExp)e).getLeft());
			e = ((AOrBooleanBinaryExp)e).getRight();
		} 		
		guards.add(e);
		
		StringBuilder sb = new StringBuilder();
		
		
		
		sb.append("do" + eol);
		
		boolean b = false;
		
		for (PExp g : guards) {			
			for (int i = 0; i < offset + 5; i++) {
				sb.append(" ");
			}
			if (b) sb.append("| "); else { sb.append("  "); b = true; }
			
			try {
				sb.append(g.apply(cmlpp));
			} catch (AnalysisException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			sb.append(" -> ");

			sb.append("[");
			
			sb.append(RefinePrettyPrinter.printFrame(w));

			
			sb.append("pre ");
			try {
				sb.append(inv.apply(cmlpp));
				sb.append(" and ");
				sb.append(g.apply(cmlpp));
			} catch (AnalysisException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sb.append(" ");
			
			sb.append("post ");

			try {
				sb.append(inv.apply(cmlpp));
				sb.append(" and (0 <= " + metas.get(LOOPVARIANT) + " and " + metas.get(LOOPVARIANT) + " < " + metas.get(LOOPVARIANT) + ")");
			} catch (AnalysisException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			sb.append("]" + eol);
		}

		for (int i = 0; i < offset + 3; i++) {
			sb.append(" ");
		}
		
		sb.append("end");
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
		return new Refinement(sb.toString(), pos);
	}

	@Override
	public Map<String, String> getMetas() {
		Map<String,String> m = new HashMap<String, String>();
		m.put(LOOPVARIANT, "expression");
		return m;
	}	
	
}
