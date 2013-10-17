package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAFieldField;

public class MCAStateDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCAFieldField> fields;
	private MCAExplicitFunctionDefinition initDef;
	private MCPCMLExp initExpression;
	private MCAExplicitFunctionDefinition invDef;
	private MCPCMLExp invExpression;

	
	public MCAStateDefinition(String name, LinkedList<MCAFieldField> fields,
			MCAExplicitFunctionDefinition initDef, MCPCMLExp initExpression,
			MCAExplicitFunctionDefinition invDef, MCPCMLExp invExpression) {
		super();
		this.name = name;
		this.fields = fields;
		this.initDef = initDef;
		this.initExpression = initExpression;
		this.invDef = invDef;
		this.invExpression = invExpression;
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


	public LinkedList<MCAFieldField> getFields() {
		return fields;
	}


	public void setFields(LinkedList<MCAFieldField> fields) {
		this.fields = fields;
	}


	public MCAExplicitFunctionDefinition getInitDef() {
		return initDef;
	}


	public void setInitDef(MCAExplicitFunctionDefinition initDef) {
		this.initDef = initDef;
	}


	public MCPCMLExp getInitExpression() {
		return initExpression;
	}


	public void setInitExpression(MCPCMLExp initExpression) {
		this.initExpression = initExpression;
	}


	public MCAExplicitFunctionDefinition getInvDef() {
		return invDef;
	}


	public void setInvDef(MCAExplicitFunctionDefinition invDef) {
		this.invDef = invDef;
	}


	public MCPCMLExp getInvExpression() {
		return invExpression;
	}


	public void setInvExpression(MCPCMLExp invExpression) {
		this.invExpression = invExpression;
	}

	
}
