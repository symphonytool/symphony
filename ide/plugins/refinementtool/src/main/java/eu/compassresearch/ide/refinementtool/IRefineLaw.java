package eu.compassresearch.ide.refinementtool;

import java.util.List;
import java.util.Map;

import org.overture.ast.node.INode;

public interface IRefineLaw {

	public String getName();
	public String getDetail();
	public boolean isApplicable(INode node);
	public Refinement apply(Map<String, String> metas, INode node, int offset);
	public List<String> getMetaNames();
}
