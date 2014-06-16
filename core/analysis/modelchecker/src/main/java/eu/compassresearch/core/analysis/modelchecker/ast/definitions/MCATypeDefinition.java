package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import org.overture.ast.definitions.AExplicitFunctionDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABracketType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAQuoteType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAUnionType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCATypeDefinition implements MCPCMLDefinition {
	
	private String name;
	//private MCAExplicitFunctionDefinition invDef;
	private MCPCMLExp invExpression;
	private MCPCMLType type;

	
	

	public MCATypeDefinition(String name, MCPCMLExp invExpression,
			MCPCMLType type) {
		super();
		this.name = name;
		this.invExpression = invExpression;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append(this.name + " ::= ");
		if(this.invExpression != null){
			if (invExpression instanceof MCAInSetBinaryExp){
				result.append(((MCAInSetBinaryExp) this.invExpression).getRight().toFormula(MCNode.EXTENSION));
			}else{
				result.append(this.invExpression.toFormula(option));
			}
		} else if (this.type != null){
			if(this.type instanceof MCAQuoteType){
				result.append("{");
			}
			result.append(this.type.toFormula(option));
			if(this.type instanceof MCAQuoteType){
				result.append("}");
			}
		}
		result.append(".");
		
		return result.toString();
	}

	public boolean hasValues(){
		boolean result = false;
		
		if(this.invExpression != null){
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			result = evaluator.getValueSet(invExpression).size() > 0;
		} else if (this.type != null){
			if(this.type instanceof MCAQuoteType || this.type instanceof MCABracketType || this.type instanceof MCAUnionType){
				result = true;
			}
		}
		
		return result;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	public MCPCMLExp getInvExpression() {
		return invExpression;
	}


	public void setInvExpression(MCPCMLExp invExpression) {
		this.invExpression = invExpression;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}

	

	
}
