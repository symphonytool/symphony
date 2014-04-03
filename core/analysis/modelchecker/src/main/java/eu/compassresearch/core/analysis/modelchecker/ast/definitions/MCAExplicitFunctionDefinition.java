package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import org.overture.ast.definitions.AExplicitFunctionDefinition;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAExplicitFunctionDefinition implements MCSFunctionDefinition {

	private String name;
	private MCPCMLType type;
	private MCPCMLExp body;
	private LinkedList<MCPCMLPattern> paramPatternList;

	public MCAExplicitFunctionDefinition(String name, MCPCMLType type,
			MCPCMLExp body, LinkedList<MCPCMLPattern> paramPatternList) {
		this.name = name;
		this.type = type;
		this.body = body;
		this.paramPatternList = paramPatternList;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp applyFunction(LinkedList<MCPCMLExp> args){
		MCPCMLExp result = null;
		
		//it gets a copy of the body expression and puts the actual args in it
		result = this.body.copy();
		
		
		return result;
	}


	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public MCPCMLType getType() {
		return type;
	}




	public void setType(MCPCMLType type) {
		this.type = type;
	}




	public MCPCMLExp getBody() {
		return body;
	}




	public void setBody(MCPCMLExp body) {
		this.body = body;
	}




	public LinkedList<MCPCMLPattern> getParamPatternList() {
		return paramPatternList;
	}




	public void setParamPatternList(LinkedList<MCPCMLPattern> paramPatternList) {
		this.paramPatternList = paramPatternList;
	}


	
		
}
