package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AIdentifierObjectDesignator;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.pog.obligation.POContext;
import org.overture.pog.pub.IPogAssistantFactory;

public class AssignmentContext extends POContext {

	Substitution sub;

	@Override
	public String getContext() {
		return null;
	}

	public AssignmentContext(AAssignmentStm node) {
		if (node.getTarget() instanceof AIdentifierStateDesignator) {
			AIdentifierStateDesignator desi = (AIdentifierStateDesignator) node
					.getTarget();
			sub = new Substitution(desi.getName(), node.getExp());
		} else {
			sub = null;
		}
	}

	@Override
	public PExp getContextNode(PExp stitch) {
		VariableSubVisitor visitor = new VariableSubVisitor();
		PExp r;
		try {
			r = stitch.apply(visitor, sub);
			return r;
		} catch (AnalysisException e) {
			e.printStackTrace();
			return null;
		}
	}

}
