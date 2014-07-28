package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
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
	public String getDetail() {
		return "P [= DUMMY";
	}	
		
	@Override
	public boolean isApplicable(INode node) {
		return true;
	}

	@Override
	public Refinement apply(Map<String, INode> metas, INode node, int offset) {
		return new Refinement("DUMMY", new LinkedList<CmlProofObligation>());
	}

	@Override
	public Map<String, String> getMetas() {
		return new HashMap<String, String>();
	}
	
}
