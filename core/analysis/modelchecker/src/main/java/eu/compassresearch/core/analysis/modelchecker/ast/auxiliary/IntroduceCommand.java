package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCTypeWrapper;

public class IntroduceCommand {

	private MCPCMLType realType;
	private int numberOfInstances;
	
	public IntroduceCommand(MCPCMLType realType, int numberOfInstances) {
		super();
		this.realType = realType;
		this.numberOfInstances = numberOfInstances;
	}
	
	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		result.append("Introduce(");
		result.append(MCTypeWrapper.getTypeWrapperString(realType.getClass()));
		result.append(",");
		result.append(numberOfInstances);
		result.append(")");
		return result.toString();
	}
	

	@Override
	public String toString() {
		
		return toFormula(MCNode.DEFAULT);
	}

	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof IntroduceCommand){
			result = realType.getClass().equals(((IntroduceCommand) obj).getRealType().getClass());
		}
		return result;
	}

	public MCPCMLType getRealType() {
		return realType;
	}

	public void setRealType(MCPCMLType realType) {
		this.realType = realType;
	}

	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	public void setNumberOfInstances(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
	
	

}
