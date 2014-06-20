package eu.compassresearch.ide.refinementtool.laws;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlRefineProvisoObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.Refinement;

public class SpecSkipRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		return "Skip Command Refinement";
	}

	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof ASpecificationStm);
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {
		ASpecificationStm spec = (ASpecificationStm) node;
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
		AImpliesBooleanBinaryExp e = new AImpliesBooleanBinaryExp();
		
		if (spec.getPrecondition() != null) 
			e.setLeft(spec.getPrecondition().clone());
		else {
			e.setLeft(AstFactory.newABooleanConstExp(new LexBooleanToken(true, null)));
		}
		
		e.setRight(spec.getPostcondition().clone());
		
		CmlRefineProvisoObligation po=null;
		try {
			po = new CmlRefineProvisoObligation(e);
		} catch (AnalysisException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pos.add(po);
		
		return new Refinement("Skip", pos);
	}

	@Override
	public List<String> getMetaNames() {
		return new LinkedList<String>();
	}	
	
}
