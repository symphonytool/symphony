package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlRefineProvisoObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class LetIntroRefineLaw implements IRefineLaw {

	public static String NEWVARNAME = "New Variable Name";
	public static String NEWVARVAL = "New Variable Value";
	
	@Override
	public String getName() {
		return "Let Variable Introduction";
	}

	@Override
	public String getDetail() {
		return "";
	}	
	
	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof PStm || node instanceof PAction);
	}

	@Override
	public Refinement apply(Map<String, INode> metas, INode node, int offset) {
		String nodeString = "";
		// FIXME: The variable given must be fresh in node for this law to be applicable,
		// need to write a visitor to check this
		RefinePrettyPrinter cmlpp = new RefinePrettyPrinter();
		try {
			nodeString = node.apply(cmlpp, 0);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
		String newvarname = "";
		String newvarval = "";
		
		try {
			newvarname = metas.get(NEWVARNAME).apply(cmlpp, 0);
			newvarval = metas.get(NEWVARVAL).apply(cmlpp, 0);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Refinement("let " + newvarname + " = " + newvarval + " in (" + nodeString + ")", pos);
	}

	@Override
	public Map<String, String> getMetas() {
		Map<String, String> m = new HashMap<String, String>();
		m.put(NEWVARNAME, "expression");
		m.put(NEWVARVAL, "expression");
		return m;
	}

}
