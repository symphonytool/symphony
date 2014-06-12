package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCTypeWrapper;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class IntroduceCommand {

	private MCPCMLType realType;
	private int numberOfInstances;
	private boolean infinite;
	
	public IntroduceCommand(MCPCMLType realType, int numberOfInstances, boolean infinite) {
		super();
		this.realType = realType;
		this.numberOfInstances = numberOfInstances;
		this.infinite = infinite;
	}
	
	public IntroduceCommand(MCPCMLType realType) {
		super();
		this.realType = realType;
		this.numberOfInstances = NewCMLModelcheckerContext.getInstance().getNumberOfInstances();
		this.infinite = false;
	}
	
	public String toFormula(String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		result.append("Introduce(");
		String wrapperString = MCTypeWrapper.getTypeWrapperString(realType.getClass());
		if(wrapperString == null){
			wrapperString = MCTypeWrapper.getWrapperForType(realType.getTypeAsName());
		}
		result.append(wrapperString);
		result.append(",");
		result.append(this.numberOfInstances);
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

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	
	

}
