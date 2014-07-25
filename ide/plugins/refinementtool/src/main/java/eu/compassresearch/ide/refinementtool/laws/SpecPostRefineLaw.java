package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlRefineProvisoObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefUtils;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class SpecPostRefineLaw implements IRefineLaw {

	public static String NEWPOST = "New Postcondition";
	
	@Override
	public String getName() {
		return "Strengthen Postcondition";
	}

	@Override
	public String getDetail() {
		return "";
	}	
		
	@Override
	public boolean isApplicable(INode node) {
		if (node instanceof ASpecificationStm) {
			ASpecificationStm spec =  (ASpecificationStm) node;
			return (spec.getPostcondition() != null);
		}
	    return false;
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {
		ASpecificationStm spec =  (ASpecificationStm) node.clone();
		
		RefinePrettyPrinter cmlpp = new RefinePrettyPrinter();
		
		ASpecificationStm newspec = new ASpecificationStm();
		
		newspec.setExternals(spec.getExternals());

		PExp newpost = RefUtils.parsePExp(metas.get(NEWPOST));
		
		newspec.setPrecondition(spec.getPrecondition().clone());
		newspec.setPostcondition(newpost.clone());
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
				
		AImpliesBooleanBinaryExp e = new AImpliesBooleanBinaryExp();
		e.setLeft(newpost);
		e.setRight(spec.getPrecondition().clone());
		
		
		CmlRefineProvisoObligation po=null;
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
		m.put(NEWPOST, "expression");
		return m;
	}	
	
}
