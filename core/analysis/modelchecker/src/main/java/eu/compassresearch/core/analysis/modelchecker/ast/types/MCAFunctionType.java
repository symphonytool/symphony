package eu.compassresearch.core.analysis.modelchecker.ast.types;

import java.util.LinkedList;

import org.overture.ast.types.AFunctionType;

public class MCAFunctionType implements MCPCMLType{

	private LinkedList<MCPCMLType> parameters;
	private MCPCMLType result;
	
	
	public MCAFunctionType(LinkedList<MCPCMLType> parameters, MCPCMLType result) {
		super();
		this.parameters = parameters;
		this.result = result;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<MCPCMLType> getParameters() {
		return parameters;
	}

	public void setParameters(LinkedList<MCPCMLType> parameters) {
		this.parameters = parameters;
	}

	public MCPCMLType getResult() {
		return result;
	}

	public void setResult(MCPCMLType result) {
		this.result = result;
	}

	
}
