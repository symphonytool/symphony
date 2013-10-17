package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;


import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAExplicitCmlOperationDefinition implements
		MCSCmlOperationDefinition {

	private MCPAction body;
	private MCPCMLExp precondition;
	private MCPCMLExp postcondition;
	private MCAExplicitFunctionDefinition predef;
	private MCAExplicitFunctionDefinition postdef;
	private LinkedList<MCPCMLDefinition> paramDefinitions = new LinkedList<MCPCMLDefinition>();
	private MCAStateDefinition state;
	private MCPCMLType actualResult;
	
	
	public MCAExplicitCmlOperationDefinition(MCPAction body,
			MCPCMLExp precondition, MCPCMLExp postcondition,
			MCAExplicitFunctionDefinition predef,
			MCAExplicitFunctionDefinition postdef,
			LinkedList<MCPCMLDefinition> paramDefinitions,
			MCAStateDefinition state, MCPCMLType actualResult) {
		super();
		this.body = body;
		this.precondition = precondition;
		this.postcondition = postcondition;
		this.predef = predef;
		this.postdef = postdef;
		this.paramDefinitions = paramDefinitions;
		this.state = state;
		this.actualResult = actualResult;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPAction getBody() {
		return body;
	}


	public void setBody(MCPAction body) {
		this.body = body;
	}


	public MCPCMLExp getPrecondition() {
		return precondition;
	}


	public void setPrecondition(MCPCMLExp precondition) {
		this.precondition = precondition;
	}


	public MCPCMLExp getPostcondition() {
		return postcondition;
	}


	public void setPostcondition(MCPCMLExp postcondition) {
		this.postcondition = postcondition;
	}


	public MCAExplicitFunctionDefinition getPredef() {
		return predef;
	}


	public void setPredef(MCAExplicitFunctionDefinition predef) {
		this.predef = predef;
	}


	public MCAExplicitFunctionDefinition getPostdef() {
		return postdef;
	}


	public void setPostdef(MCAExplicitFunctionDefinition postdef) {
		this.postdef = postdef;
	}


	public LinkedList<MCPCMLDefinition> getParamDefinitions() {
		return paramDefinitions;
	}


	public void setParamDefinitions(LinkedList<MCPCMLDefinition> paramDefinitions) {
		this.paramDefinitions = paramDefinitions;
	}


	public MCAStateDefinition getState() {
		return state;
	}


	public void setState(MCAStateDefinition state) {
		this.state = state;
	}


	public MCPCMLType getActualResult() {
		return actualResult;
	}


	public void setActualResult(MCPCMLType actualResult) {
		this.actualResult = actualResult;
	}
	

}
