package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
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
		result.append("preOpOk(\"" + this.name + "\",");
		if(this.paramPatterns.size()==0){
			result.append("void");
		}else if(this.paramPatterns.size()==1){
			result.append(this.paramPatterns.getFirst().toFormula(option));
		}else if(this.paramPatterns.size() > 1){
			//TODO
		}
		result.append(",");
		result.append(context.maximalBinding.toFormula(MCNode.NAMED));
		result.append(")");
		result.append(" :- ");
		result.append("State(l," + context.maximalBinding.toFormula(MCNode.NAMED) + ",np,_).");

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
