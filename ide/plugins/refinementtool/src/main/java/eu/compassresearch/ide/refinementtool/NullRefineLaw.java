package eu.compassresearch.ide.refinementtool;

import org.overture.ast.node.INode;

public class NullRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Null Refine Law";
	}

	@Override
	public boolean isApplicable(INode node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Refinement apply(INode node) {
		// TODO Auto-generated method stub
		return null;
	}

}
