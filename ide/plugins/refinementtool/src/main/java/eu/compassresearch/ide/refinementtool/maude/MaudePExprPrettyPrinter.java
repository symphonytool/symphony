package eu.compassresearch.ide.refinementtool.maude;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.AVariableExp;

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
	public String caseAVariableExp(AVariableExp node) throws AnalysisException {
		return "#nm(\"" + node.getName().toString() + "\")";
	}
	
}
