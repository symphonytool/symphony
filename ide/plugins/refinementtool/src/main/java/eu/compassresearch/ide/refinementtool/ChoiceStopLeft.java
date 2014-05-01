package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;

public class ChoiceStopLeft implements IRefineLaw {

	@Override
	public String getName() {
		return "Choice Stop Left";
	}

	@Override
	public boolean isApplicable(INode node) {
		// TODO Auto-generated method stub
		if (node instanceof AExternalChoiceAction) {
			return ((AExternalChoiceAction) node).getLeft() instanceof AStopAction;
		}
		return false;
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node) {
		return new Refinement(
				((AExternalChoiceAction) node)
						.getRight().toString(), new LinkedList<CmlProofObligation>());
	}

	@Override
	public List<String> getMetaNames() {
		return new LinkedList<String>();
	}	
	
}
