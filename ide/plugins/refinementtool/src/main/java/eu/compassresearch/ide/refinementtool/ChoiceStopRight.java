package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;

import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.statements.AActionStm;

public class ChoiceStopRight implements IRefineLaw {

	@Override
	public String getName() {
		return "Choice Stop Right";
	}

	@Override
	public boolean isApplicable(INode node) {
		// TODO Auto-generated method stub
		if (node instanceof AActionStm) {
			AActionStm n1 = (AActionStm) node;
			if (n1.getAction() instanceof AExternalChoiceAction) {
				return ((AExternalChoiceAction) n1.getAction()).getRight() instanceof AStopAction;			
			}
		}
		return false;
	}

	@Override
	public Refinement apply(INode node) {		
		return new Refinement(((AExternalChoiceAction) (((AActionStm) node).getAction())).getLeft().toString(), new LinkedList<PExp>());
	}

}
