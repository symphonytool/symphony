package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.LinkedList;

import org.overture.ast.expressions.AApplyExp;

import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAApplyExp implements MCPCMLExp {

	private LinkedList<MCPCMLExp> args;
	private MCPCMLExp root;
	
	
	public MCAApplyExp(LinkedList<MCPCMLExp> args, MCPCMLExp root) {
		this.args = args;
		this.root = root;
	}

	@Override
	public String toFormula(String option) {
		//this expression represents function apply.So we need to write its application code in formula  
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		//obtaining the function definition
		MCAExplicitFunctionDefinition functionDef = null;
		for (MCSFunctionDefinition fDef : context.functions) {
			if(fDef instanceof MCAExplicitFunctionDefinition){
				if(this.root instanceof MCAVariableExp){
					if(((MCAExplicitFunctionDefinition) fDef).getName().equals(((MCAVariableExp) this.root).getName())){
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
			
			LinkedList<PatternValue> argsMapping = evaluator.buildPatternValueList(actualParams, this.args);
			int i = 0;
			MCPCMLExp bodyReady = functionDef.getBody().copy();
			bodyReady.replacePatternWithValue(argsMapping);
			result.append(bodyReady.toFormula(option));
		}
		
		
		
		return result.toString();
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<MCPCMLExp> getArgs() {
		return args;
	}

	public void setArgs(LinkedList<MCPCMLExp> args) {
		this.args = args;
	}

	public MCPCMLExp getRoot() {
		return root;
	}

	public void setRoot(MCPCMLExp root) {
		this.root = root;
	}

	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		
	}

	
}
