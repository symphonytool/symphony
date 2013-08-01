package eu.compassresearch.core.analysis.pog.utility;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.obligations.CmlPOContextStack;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;

public class PogPubUtil {

	public static IProofObligationList generateProofObligations(
			List<INode> ast) throws AnalysisException {
		IProofObligationList r = new ProofObligationList();
		for (INode node : ast) {
			r.addAll(node.apply(new ProofObligationGenerator(),
					new CmlPOContextStack()));
		}
		return r;
	}

}
