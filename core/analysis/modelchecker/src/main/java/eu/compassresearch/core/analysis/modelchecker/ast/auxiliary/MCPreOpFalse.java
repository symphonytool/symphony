package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCPreOpFalse implements MCNode {

	private String name;
	private LinkedList<MCPCMLDefinition> paramDefinitions = new LinkedList<MCPCMLDefinition>();
	
	
	public MCPreOpFalse(String name, LinkedList<MCPCMLDefinition> paramDefinitions) {
		super();
		this.name = name;
		this.paramDefinitions = paramDefinitions;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		result.append("preOpNOk(\"" + this.name + "\",");
		if(this.paramDefinitions.size()==0){
			result.append("void");
		}else if(this.paramDefinitions.size()==1){
			result.append(this.paramDefinitions.getFirst().toFormula(option));
		}else if(this.paramDefinitions.size() > 1){
			//TODO
		}
		result.append(",");
		result.append(context.maximalBinding.toFormula(MCNode.NAMED));
		result.append(")");
		result.append(" :- ");
		result.append("State(l," + context.maximalBinding.toFormula(MCNode.NAMED) + ",np,_).");
		result.append("\n");

		return result.toString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LinkedList<MCPCMLDefinition> getParamDefinitions() {
		return paramDefinitions;
	}


	public void setParamDefinitions(LinkedList<MCPCMLDefinition> paramDefinitions) {
		this.paramDefinitions = paramDefinitions;
	}

	
}
