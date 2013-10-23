package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ExpressionNegator {

	public static MCPCMLExp negate(MCPCMLExp expression){
		MCPCMLExp result = null;
		
		if(expression instanceof MCAEqualsBinaryExp){
			result = new MCANotEqualsBinaryExp(((MCAEqualsBinaryExp) expression).getLeft(),((MCAEqualsBinaryExp) expression).getRight());
		}
		
		return result;
	}
}
