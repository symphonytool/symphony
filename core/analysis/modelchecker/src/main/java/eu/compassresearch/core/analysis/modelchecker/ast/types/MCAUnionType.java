package eu.compassresearch.core.analysis.modelchecker.ast.types;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.types.AUnionType;

public class MCAUnionType implements MCPCMLType {

	private LinkedList<MCPCMLType> types;

	
	
	public MCAUnionType(LinkedList<MCPCMLType> types) {
		super();
		this.types = types;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		for (Iterator<MCPCMLType> iterator = types.iterator(); iterator.hasNext();) {
			MCPCMLType type = (MCPCMLType) iterator.next();
			result.append(type.toFormula(option));
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("}");
		return result.toString();
	}

	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeAsName() {
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
