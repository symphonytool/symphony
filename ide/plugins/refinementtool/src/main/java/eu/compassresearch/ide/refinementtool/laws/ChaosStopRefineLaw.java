package eu.compassresearch.ide.refinementtool.laws;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.Refinement;

public class ChaosStopRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		return "Chaos to Stop";
	}

	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof AChaosAction);
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {
		// TODO Auto-generated method stub
		return new Refinement("Stop", new LinkedList<CmlProofObligation>());
	}

	@Override
	public List<String> getMetaNames() {
		return new LinkedList<String>();
	}

}
