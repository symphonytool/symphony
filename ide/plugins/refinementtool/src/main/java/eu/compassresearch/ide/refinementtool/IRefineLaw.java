package eu.compassresearch.ide.refinementtool;

import org.overture.ast.node.INode;

public interface IRefineLaw {

	public String getName();
	public boolean isApplicable(INode node);
	public IRefinement apply(INode node);
	
}
