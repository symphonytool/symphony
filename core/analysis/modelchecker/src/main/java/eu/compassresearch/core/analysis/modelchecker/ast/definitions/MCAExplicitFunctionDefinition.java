package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAExplicitFunctionDefinition implements MCPCMLDefinition {

	private String name;
	private MCPCMLType actualResult;
	private MCPCMLExp body;
	private LinkedList<MCPCMLDefinition> paramDefinitionList;
	private MCPCMLExp preCondition;
	private MCPCMLExp postCondition;

	
	public MCAExplicitFunctionDefinition(String name, MCPCMLType actualResult,
			MCPCMLExp body, LinkedList<MCPCMLDefinition> paramDefinitionList,
			MCPCMLExp preCondition, MCPCMLExp postCondition) {
		super();
		this.name = name;
		this.actualResult = actualResult;
		this.body = body;
		this.paramDefinitionList = paramDefinitionList;
		this.preCondition = preCondition;
		this.postCondition = postCondition;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MCPCMLType getActualResult() {
		return actualResult;
	}


	public void setActualResult(MCPCMLType actualResult) {
		this.actualResult = actualResult;
	}


	public MCPCMLExp getBody() {
		return body;
	}


	public void setBody(MCPCMLExp body) {
		this.body = body;
	}


	public LinkedList<MCPCMLDefinition> getParamDefinitionList() {
		return paramDefinitionList;
	}


	public void setParamDefinitionList(
			LinkedList<MCPCMLDefinition> paramDefinitionList) {
		this.paramDefinitionList = paramDefinitionList;
	}


	public MCPCMLExp getPreCondition() {
		return preCondition;
	}


	public void setPreCondition(MCPCMLExp preCondition) {
		this.preCondition = preCondition;
	}


	public MCPCMLExp getPostCondition() {
		return postCondition;
	}


	public void setPostCondition(MCPCMLExp postCondition) {
		this.postCondition = postCondition;
	}

	
}
