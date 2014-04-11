package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASpecificationStm;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;

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
	public Refinement apply(INode node) {
		ASpecificationStm spec = (ASpecificationStm) node;
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
		AImpliesBooleanBinaryExp e = new AImpliesBooleanBinaryExp();
		
		if (spec.getPrecondition() != null) 
			e.setLeft(spec.getPrecondition().clone());
		else {
			e.setLeft(AstFactory.newABooleanConstExp(new LexBooleanToken(true, null)));
		}
		
		e.setRight(spec.getPostcondition().clone());
		
		CmlRefineProvisoObligation po = new CmlRefineProvisoObligation(e);
		
		pos.add(po);
		
		return new Refinement("Skip", pos);
	}

}
