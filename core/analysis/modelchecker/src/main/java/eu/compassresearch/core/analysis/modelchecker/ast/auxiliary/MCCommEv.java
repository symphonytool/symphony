package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCCommEv implements MCNode {

	private String name;
	private LinkedList<MCPCommunicationParameter> parameters;
	private MCPCMLType value;
	private boolean literal;
	
	
	public MCCommEv(String name, LinkedList<MCPCommunicationParameter> parameters, MCPCMLType value) {
		this.name = name;
		this.parameters = parameters;
		this.value = value;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("CommEv(");
		result.append("\"");
		result.append(this.name);
		result.append("\"");
		result.append(",");
		result.append("\"");
		result.append(this.parametersToString(option));
		result.append("\"");
		result.append(",");
		result.append(this.value.toFormula(option));
		result.append(")");
		
		return result.toString();
	}
	
	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCCommEv){
			result = this.name.equals(((MCCommEv) obj).getName())
					&& this.parameters.equals(((MCCommEv) obj).getParameters());
		}
		return result;
	}


	private String parametersToString(String option){
		StringBuilder result = new StringBuilder();
		Iterator<MCPCommunicationParameter> it = this.parameters.iterator();
		while (it.hasNext()) {
			MCPCommunicationParameter item = (MCPCommunicationParameter) it.next();
			result.append(item.toFormula(option));
			//if(it.hasNext()){
			//	result.append(".");
			//}
		}
		
		return result.toString();
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LinkedList<MCPCommunicationParameter> getParameters() {
		return parameters;
	}


	public void setParameters(LinkedList<MCPCommunicationParameter> parameters) {
		this.parameters = parameters;
	}


	public MCPCMLType getValue() {
		return value;
	}


	public void setValue(MCPCMLType value) {
		this.value = value;
	}


	public boolean isLiteral() {
		return literal;
	}


	public void setLiteral(boolean literal) {
		this.literal = literal;
	}

	
	
}
