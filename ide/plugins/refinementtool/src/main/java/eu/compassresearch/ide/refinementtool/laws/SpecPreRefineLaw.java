package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.ide.refinementtool.CmlPExprPrettyPrinter;
import eu.compassresearch.ide.refinementtool.CmlRefineProvisoObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefUtils;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class SpecPreRefineLaw implements IRefineLaw {

	public static String NEWPRE = "New Precondition";
	
	@Override
	public String getName() {
		return "Weaken Precondition";
	}

	@Override
	public String getDetail() {
		return "";
	}	
		
	@Override
	public boolean isApplicable(INode node) {
		if (node instanceof ASpecificationStm) {
			ASpecificationStm spec =  (ASpecificationStm) node;
			return (spec.getPrecondition() != null);
		}
	    return false;
	}

	@Override
	public Refinement apply(Map<String, INode> metas, INode node, int offset) {
		ASpecificationStm spec =  (ASpecificationStm) node.clone();
		
		RefinePrettyPrinter cmlpp = new RefinePrettyPrinter();
		
		ASpecificationStm newspec = new ASpecificationStm();
		
		newspec.setExternals(spec.getExternals());

		PExp newpre = (PExp) metas.get(NEWPRE);
		
		newspec.setPrecondition(newpre.clone());
		newspec.setPostcondition(spec.getPostcondition().clone());
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
				
		AImpliesBooleanBinaryExp e = new AImpliesBooleanBinaryExp();
		e.setLeft(spec.getPrecondition().clone());
		e.setRight(newpre);
		
		CmlRefineProvisoObligation po = null;
		try {
			po = new CmlRefineProvisoObligation(e);
		} catch (AnalysisException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		pos.add(po);

		String output = null;
		try {
			output = newspec.apply(cmlpp, 0);
		} catch (AnalysisException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return new Refinement(output, pos);
	}

	@Override
	public Map<String, String> getMetas() {
		Map<String,String> m = new HashMap<String, String>();
		m.put(NEWPRE, "expression");
		return m;
	}		

}
