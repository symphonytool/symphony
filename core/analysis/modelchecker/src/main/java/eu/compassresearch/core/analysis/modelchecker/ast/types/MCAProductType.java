package eu.compassresearch.core.analysis.modelchecker.ast.types;

import java.util.LinkedList;

public class MCAProductType implements MCPCMLType {

	private LinkedList<MCPCMLType> types;
	
	public MCAProductType(LinkedList<MCPCMLType> types) {
		this.types = types;
	}
	
	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append("ProdType(");
		result.append(this.types.getFirst().toFormula(option));
		result.append(",");

		if(this.types.size() == 2){
			result.append(this.types.getLast().toFormula(option));
		} else if (this.types.size() > 2){
			LinkedList<MCPCMLType> typesCopy = new LinkedList<MCPCMLType>();
			typesCopy.addAll(types);
			typesCopy.removeFirst();
			MCAProductType rest = new MCAProductType(typesCopy);
			
			result.append(rest.toFormula(option));
		}
		result.append(")");
		
		
		return result.toString();
	}


	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<MCPCMLType> getTypes() {
		return types;
	}

	public void setTypes(LinkedList<MCPCMLType> types) {
		this.types = types;
	}

	
}
