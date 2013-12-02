package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCASetUnionBinaryExp extends MCASBinaryExp {

	private String newVarName; 
	
	public MCASetUnionBinaryExp(MCPCMLExp left, MCPCMLExp right) {
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
		result.append("union(");
		result.append(this.getLeft().toFormula(option));
		result.append(",");
		result.append(this.getRight().toFormula(option));
		result.append(",");
		result.append(this.newVarName);
		result.append(")");
		
		return result.toString();
	}

	public String getNewVarName() {
		return newVarName;
	}

	public void setNewVarName(String newVarName) {
		this.newVarName = newVarName;
	}

	
}
