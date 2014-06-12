package eu.compassresearch.ide.refinementtool.laws;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.node.INode;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.Refinement;

public class DummyRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		return "Dummy Law";
	}

	@Override
	public boolean isApplicable(INode node) {
		return true;
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {
		return new Refinement("DUMMY", new LinkedList<CmlProofObligation>());
	}

	@Override
	public List<String> getMetaNames() {
		return new LinkedList<String>();
	}	
	
}
