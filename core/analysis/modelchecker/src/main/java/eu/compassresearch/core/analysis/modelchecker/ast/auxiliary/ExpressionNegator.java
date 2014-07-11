package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAAndBooleanBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAApplyExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAGreaterNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessEqualNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCALessNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANotUnaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAOrBooleanBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASeqEnumSeqExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class ExpressionNegator {

	public static MCPCMLExp negate(MCPCMLExp expression){
		MCPCMLExp result = null;
		
		if(expression instanceof MCAEqualsBinaryExp){
			result = new MCANotEqualsBinaryExp(((MCAEqualsBinaryExp) expression).getLeft(),((MCAEqualsBinaryExp) expression).getRight());
		} else if(expression instanceof MCAGreaterEqualNumericBinaryExp){
			result = new MCALessNumericBinaryExp(((MCAGreaterEqualNumericBinaryExp)expression).getLeft(), ((MCAGreaterEqualNumericBinaryExp)expression).getRight());
		} else if(expression instanceof MCALessNumericBinaryExp){
			result = new MCAGreaterEqualNumericBinaryExp(((MCALessNumericBinaryExp)expression).getLeft(), ((MCALessNumericBinaryExp)expression).getRight());
		} else if(expression instanceof MCANotEqualsBinaryExp){
			result = new MCAEqualsBinaryExp(((MCANotEqualsBinaryExp) expression).getLeft(),((MCANotEqualsBinaryExp) expression).getRight());
		} else if(expression instanceof MCALessEqualNumericBinaryExp){
			result = new MCAGreaterNumericBinaryExp(((MCALessEqualNumericBinaryExp)expression).getLeft(), ((MCALessEqualNumericBinaryExp)expression).getRight());
		} else if(expression instanceof MCAGreaterNumericBinaryExp){
			result = new MCALessEqualNumericBinaryExp(((MCAGreaterNumericBinaryExp)expression).getLeft(), ((MCAGreaterNumericBinaryExp)expression).getRight());
		} else if(expression instanceof MCANotUnaryExp){
			result = ((MCANotUnaryExp) expression).getExp();
		} else if(expression instanceof MCAApplyExp){
			result = new MCANotUnaryExp(expression);
		} else if(expression instanceof MCAInSetBinaryExp){
			result = new MCANotInSetBinaryExp(((MCAInSetBinaryExp) expression).getLeft(), ((MCAInSetBinaryExp) expression).getRight());
		} else if(expression instanceof MCAVariableExp){
			result = new MCANotUnaryExp(expression);
		} else if(expression instanceof MCASeqEnumSeqExp){
			result = negate(((MCASeqEnumSeqExp) expression).getMembers().getFirst());
		} else if(expression instanceof MCAAndBooleanBinaryExp){
			MCPCMLExp negLeft = negate(((MCAAndBooleanBinaryExp) expression).getLeft());
			MCPCMLExp negRight = negate(((MCAAndBooleanBinaryExp) expression).getRight());
			result = new MCAOrBooleanBinaryExp(negLeft,negRight);
		} else if(expression instanceof MCAOrBooleanBinaryExp){
			MCPCMLExp negLeft = negate(((MCAOrBooleanBinaryExp) expression).getLeft());
			MCPCMLExp negRight = negate(((MCAOrBooleanBinaryExp) expression).getRight());
			result = new MCAAndBooleanBinaryExp(negLeft,negRight);
		}
		
		return result;
	}
}
