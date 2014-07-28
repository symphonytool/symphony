package eu.compassresearch.ide.refinementtool.laws;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.ide.refinementtool.CmlPExprPrettyPrinter;
import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefUtils;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;
import eu.compassresearch.ide.refinementtool.Refinement;

public class LetPreRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		return "Let Insert Precondition";
	}

	@Override
	public String getDetail() {
		return "";
	}	
		
	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof ALetStm); 
	}

	@Override
	public Refinement apply(Map<String, INode> metas, INode node, int offset) {
		
		ALetStm ls = ((ALetStm) node).clone();
		List<PDefinition> defs = ls.getLocalDefs();
		
		CmlPExprPrettyPrinter cmlpp = new CmlPExprPrettyPrinter();
		RefinePrettyPrinter rpp = new RefinePrettyPrinter();
		
		ABlockSimpleBlockStm block = new ABlockSimpleBlockStm();
		
		ASpecificationStm assm = new ASpecificationStm();
		
		PExp pre = null, aux = null;
        boolean cont = false;

		for (PDefinition def : defs) {
			AValueDefinition av = (AValueDefinition) def;
			PExp exp = av.getExpression().clone();
			ILexNameToken name = av.getDefs().get(0).getName().clone();
			
			AVariableExp var = new AVariableExp();
			var.setName(name);
			var.setOriginal(name.toString());
			
			AEqualsBinaryExp eq = new AEqualsBinaryExp();
			eq.setLeft(var);
			eq.setRight(exp);
		
		    if (cont) {
		    	aux = pre;
		    	pre = new AAndBooleanBinaryExp();
		    	((AAndBooleanBinaryExp) pre).setLeft(aux);
		    	((AAndBooleanBinaryExp) pre).setRight(eq);
		    } else {
		    	pre = eq;
		    	cont = true;
		    }
		}

		assm.setPostcondition(RefUtils.parsePExp("true"));
		assm.setPrecondition(pre);

		List<PStm> stms = new LinkedList<PStm>();
				
		stms.add(assm);
		stms.add(ls.getStatement());
		block.setStatements(stms);

		AStmAction sa = new AStmAction();
		sa.setStatement(block);
		
		AActionStm as = new AActionStm();
		as.setAction(sa);
		
		ls.setStatement(as);

		String output = "";
		try {
			output = ls.apply(rpp, 0);
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<CmlProofObligation> pos = new LinkedList<CmlProofObligation>();
		
		return new Refinement(output, pos);
	}

	@Override
	public Map<String, String> getMetas() {
		return new HashMap<String, String>();
	}

}
