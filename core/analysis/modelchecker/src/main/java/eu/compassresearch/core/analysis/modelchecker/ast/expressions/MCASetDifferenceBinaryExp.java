package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NamedSet;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NonEmptySet;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Set;

public class MCASetDifferenceBinaryExp extends MCASBinaryExp {

	private String newVarName; 
	
	public MCASetDifferenceBinaryExp(MCPCMLExp left, MCPCMLExp right) {
		super(left, right);
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		/*
		result.append("union(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(",");
		result.append(this.newVarName);
		result.append(")");
		*/
		result.append(this.newVarName);
		result.append(" = ");
		
		MCPCMLExp setNameExp = this.getLeft();
		MCPCMLExp setExp = this.getRight();
		Set setRightExp = null;
		if(! (setNameExp instanceof MCAVariableExp) ){
			setNameExp = this.getRight();
			setExp = this.getRight();
		}
		setRightExp = new NamedSet(((MCAVariableExp) setNameExp).getName());
		if(setExp instanceof MCASetEnumSetExp){
			for (MCPCMLExp member : ((MCASetEnumSetExp) setExp).getMembers()) {
				setRightExp = setRightExp.addElement(member);
			}
		}
		result.append(setRightExp.toFormula(option)); 
		
		
		return result.toString();
	}

	public String getNewVarName() {
		return newVarName;
	}

	public void setNewVarName(String newVarName) {
		this.newVarName = newVarName;
	}

	
}
