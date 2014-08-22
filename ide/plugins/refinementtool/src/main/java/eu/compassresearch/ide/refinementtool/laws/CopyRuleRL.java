package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlActionEqualityProvisoObligation;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.Refinement;

public class CopyRuleRL implements IRefineLaw {

	@Override
	public String getName() {
		return "Copy rule RL";
	}

	@Override
	public String getDetail() {
		return "Apply the copy rule right to left";
	}

	@Override
	public boolean isApplicable(INode node) {
		return node instanceof PAction || node instanceof PStm;
	}

	public PDefinition getActionDefinition(String name, INode a) {
		AActionProcess process = null;
		INode aux = null;
		for (aux = a.parent(); aux != null && !(aux instanceof AActionProcess); aux = aux.parent()) {
			
		}
		if (aux instanceof AActionProcess) {
			process = (AActionProcess) aux;
			List<PDefinition> actions = ((AActionProcess) aux).getActionDefinition().getDefinitions();
			for (PDefinition p: actions) {
				if (p.getName().getName().equals(name)) {
					return p;
				}
			}
			return null;
		} else {
			return null;
		}
	}	
	
	@Override
	public Refinement apply(Map<String, INode> metas, INode a, int offset) {
		AActionDefinition def = (AActionDefinition) getActionDefinition(metas.get("A").toString(), a);
		
		try {
			CmlActionEqualityProvisoObligation p = new CmlActionEqualityProvisoObligation(a, def.getAction());
			List<CmlProofObligation> pp = new LinkedList<CmlProofObligation>();
			pp.add(p);
			return new Refinement(
					metas.get("A").toString(), pp);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, String> getMetas() {
		Map<String,String> m = new HashMap<String,String>();
		m.put("A", "action");
		return m;
	}

}
