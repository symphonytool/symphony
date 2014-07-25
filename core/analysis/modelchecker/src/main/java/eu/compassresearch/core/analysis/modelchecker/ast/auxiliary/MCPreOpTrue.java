package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCPreOpTrue implements MCNode {

	private String name;
	private LinkedList<MCPCMLPattern> paramPatterns = new LinkedList<MCPCMLPattern>();
		
	
	public MCPreOpTrue(String name, LinkedList<MCPCMLPattern> paramPatterns) {
		super();
		this.name = name;
		this.paramPatterns = paramPatterns;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		result.append("  preOpOk(\"" + this.name + "\",");
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		MCPCMLType paramType = evaluator.instantiateMCTypeFromPatterns(this.paramPatterns);
		result.append(paramType.toFormula(option));
		result.append(",");
		result.append(context.maximalBinding.toFormula(MCNode.NAMED));
		result.append(")");
		result.append(" :- ");
		result.append("State(" + context.maximalBinding.toFormula(MCNode.NAMED));
		result.append(",");
		
		MCSCmlOperationDefinition opDef = context.getOperationDefinition(this.name);
		if(opDef instanceof MCAExplicitCmlOperationDefinition){
			MCOperationCall opCall = new MCOperationCall(this.name, null, ((MCAExplicitCmlOperationDefinition) opDef).getParamPatterns(),((MCAExplicitCmlOperationDefinition) opDef).getOperationType());
			result.append(opCall.toFormula(NAMED));
		}
		result.append(")");
		result.append(".");

		return result.toString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LinkedList<MCPCMLPattern> getParamPatterns() {
		return paramPatterns;
	}


	public void setParamPatterns(LinkedList<MCPCMLPattern> paramPatterns) {
		this.paramPatterns = paramPatterns;
	}


		
}
