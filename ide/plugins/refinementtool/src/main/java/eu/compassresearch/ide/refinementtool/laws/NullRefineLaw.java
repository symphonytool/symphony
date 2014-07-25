package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.node.INode;

import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.Refinement;

public class NullRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Null Refine Law";
	}

	@Override
	public String getDetail() {
		return "";
	}	
		
	@Override
	public boolean isApplicable(INode node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Refinement apply(Map<String, String> metas, INode node, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getMetas() {
		return new HashMap<String, String>();
	}
	
}
