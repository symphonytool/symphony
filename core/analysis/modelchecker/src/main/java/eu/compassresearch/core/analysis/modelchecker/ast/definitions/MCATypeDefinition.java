package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.definitions.AExplicitFunctionDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAInSetBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABracketType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAQuoteType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAUnionType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

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
			if(this.type instanceof MCANamedInvariantType){
				TypeManipulator handler = TypeManipulator.getInstance();
				LinkedList<TypeValue> values = handler.getValues(this.type);
				result.append("{");
				for (Iterator iterator = values.iterator(); iterator.hasNext();) {
					TypeValue typeValue = (TypeValue) iterator.next();
					result.append(typeValue.toFormula(option));
					if(iterator.hasNext()){
						result.append(",");
					}
				}
				result.append("}");
			} else{
				result.append(this.type.toFormula(option));
			}
			
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
			}else if (this.type instanceof MCANamedInvariantType){
				NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
				MCAValueDefinition valueDef = context.getValueDefinition(((MCANamedInvariantType) this.type).getName());
				if(valueDef != null){
					result = true;
				}else{
					MCATypeDefinition finalTypeDef = context.getTypeDefinition(((MCANamedInvariantType) this.type).getName());
					result = finalTypeDef.hasValues();
				}
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
