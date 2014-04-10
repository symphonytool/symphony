package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;

import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

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
	public Refinement apply(INode node) {
		return new Refinement("DUMMY", new LinkedList<PExp>());
	}

}
