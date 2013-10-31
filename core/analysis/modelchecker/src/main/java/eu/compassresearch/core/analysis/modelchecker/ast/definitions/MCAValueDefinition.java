package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCValueDef;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAIntLiteralExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCATimesNumericBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAValueDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPCMLDefinition> definitions;
	private MCPCMLExp expression;
	private MCPCMLType type;
	private LinkedList<MCValueDef> valueDefs;
	
	public MCAValueDefinition(String name,
			LinkedList<MCPCMLDefinition> definitions, MCPCMLExp expression, MCPCMLType type) {
		super();
		this.name = name;
		this.definitions = definitions;
		this.expression = expression;
		this.type = type;
		this.valueDefs = new LinkedList<MCValueDef>();
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		//MCValueDef valueDef = new MCValueDef(this.name, this.type, this.expression);
		
		//result.append(valueDef.toFormula(option));
		//if(this.expression instanceof MCAIntLiteralExp){ //other cases should also be included here
		//	result.append("  primitive  ");
		//}else{
		//	result.append("  ");
		//}
		result.append("  " + this.name);
		result.append(" ::= {");
		//ExpressionEvaluator expEvaluator = ExpressionEvaluator.getInstance();
		//expEvaluator.obtainValue(expression)
		result.append(this.expression.toFormula(option));
		result.append("}.");
		
		return result.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<MCPCMLDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(LinkedList<MCPCMLDefinition> definitions) {
		this.definitions = definitions;
	}

	public MCPCMLExp getExpression() {
		return expression;
	}

	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	public LinkedList<MCValueDef> getValueDefs() {
		return valueDefs;
	}

	public void setValueDefs(LinkedList<MCValueDef> valueDefs) {
		this.valueDefs = valueDefs;
	}

	public MCPCMLType getType() {
		return type;
	}

	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
	
}
