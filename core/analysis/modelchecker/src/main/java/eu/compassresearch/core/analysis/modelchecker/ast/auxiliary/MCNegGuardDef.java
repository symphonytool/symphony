package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCNegGuardDef implements MCNode{

	private Binding max;
	private MCCondition cond;
	
	public MCNegGuardDef(MCCondition condition) {
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.cond = condition;
	}
	
	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("  guardNDef(l,"+ this.cond.counter +","+ max.toFormula()+")");
		result.append(" :- State(l," + max.toFormula() + "," + max.getProcName()+ "," + cond.getParentAction().toFormula(option) + "))");
		result.append(",");
		result.append(cond.expression.toFormula(option)+".\n");
		return result.toString();

	}

}
