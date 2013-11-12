package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionNegator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;


public class MCANotUnaryExp implements MCBooleanExp {

	private MCPCMLExp exp;

	
	public MCANotUnaryExp(MCPCMLExp exp) {
		super();
		this.exp = exp;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		if(this.exp instanceof MCAApplyExp){
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			
			//obtaining the function definition
			MCAExplicitFunctionDefinition functionDef = null;
			for (MCSFunctionDefinition fDef : context.functions) {
				if(fDef instanceof MCAExplicitFunctionDefinition){
					if(((MCAApplyExp) this.exp).getRoot() instanceof MCAVariableExp){
						if(((MCAExplicitFunctionDefinition) fDef).getName().equals(((MCAVariableExp) ((MCAApplyExp) this.exp).getRoot()).getName())){
							functionDef = (MCAExplicitFunctionDefinition) fDef;
							break;
						}
					}
				}
			}
			
			if(functionDef != null){
				ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
				LinkedList<MCPCMLPattern> actualParams = new LinkedList<MCPCMLPattern>();
				actualParams.addAll(functionDef.getParamPatternList());
				
				LinkedList<PatternValue> argsMapping = evaluator.buildPatternValueList(actualParams, ((MCAApplyExp) this.exp).getArgs());
				
				MCPCMLExp bodyReady = functionDef.getBody().copy();
				bodyReady = ExpressionNegator.negate(bodyReady);
				bodyReady.replacePatternWithValue(argsMapping);
				result.append(bodyReady.toFormula(option));
			}
		} else{
			result.append(this.exp.toFormula(option));
		}

		return result.toString();
	}


	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getExp() {
		return exp;
	}


	public void setExp(MCPCMLExp exp) {
		this.exp = exp;
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		this.exp.replacePatternWithValue(mapping);
	}

	
	
}
