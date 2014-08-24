package eu.compassresearch.ide.refinementtool.maude;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.AVariableExp;

import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ide.refinementtool.CmlPExprPrettyPrinter;

public class MaudePExprPrettyPrinter extends CmlPExprPrettyPrinter {
	
	public MaudePExprPrettyPrinter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String caseABooleanConstExp(ABooleanConstExp node)
			throws AnalysisException {
		return "#b(" + node.getValue().toString() + ")";
	}


	@Override
	public String caseAIntLiteralExp(AIntLiteralExp node)
			throws AnalysisException {
		return "#n(" + node.getValue().toString() + ")";
	}

	@Override
	public String caseAVariableExp(AVariableExp node) throws AnalysisException {
		return "#nm(\"" + node.getName().toString() + "\")";
	}

	@Override
	public String caseAEqualsBinaryExp(AEqualsBinaryExp node)
			throws AnalysisException {
		return node.getLeft().apply(this)+" == "+node.getRight().apply(this);
	}
	
	
	
}
