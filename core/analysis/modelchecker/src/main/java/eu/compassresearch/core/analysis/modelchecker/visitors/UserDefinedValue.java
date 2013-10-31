package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Arrays;
import java.util.LinkedList;

import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;

public class UserDefinedValue {
	protected AValueDefinition valueDef;

	public UserDefinedValue(AValueDefinition valueDef) {
		super();
		this.valueDef = valueDef;
	}

	public AValueDefinition getValueDef() {
		return valueDef;
	}

	public void setValueDef(AValueDefinition valueDef) {
		this.valueDef = valueDef;
	}
	
	public String toFormula(){
		StringBuilder result = new StringBuilder();
		if(!valueDef.getDefs().isEmpty()){
			while(!valueDef.getDefs().isEmpty()){
				result = new StringBuilder();
				result.append("primitive ");
				PDefinition currDef = valueDef.getDefs().pollFirst();
				result.append(currDef.getName());
				result.append(" ::= (" + valueDef.getType().toString() + ").\n");
			}
		}
		
		
			
		return result.toString();
	}

	@Override
	public String toString() {
		LinkedList<PDefinition> defs = new LinkedList<PDefinition>(valueDef.getDefs());
		
		return Arrays.toString(defs.toArray());
	}
	
	
}
