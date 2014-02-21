package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCAAssignmentStm;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAssignDef implements MCNode {

	protected Binding max;
	protected int counterId;
	protected MCPCMLExp expression;
	private MCPCMLExp stateDesignator;
	protected MCPCMLStm parentStm;
	
	
	public MCAssignDef(int counterId, MCPCMLExp expression, MCPCMLExp stateDesignator,
			MCPCMLStm parentStm) {
		super();
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.counterId = counterId;
		this.expression = expression;
		this.stateDesignator = stateDesignator;
		this.parentStm = parentStm;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		max = context.maximalBinding;

		result.append("  assignDef("+ counterId + ", st, st_)  :- State(st,assign("+ counterId +")), ");
		result.append("st = "); 
		result.append(max.toFormula(MCNode.NAMED)); //with variable names
		result.append(", st_ = ");
		Binding maxCopy = max.copy();
		if (stateDesignator == null){
			if (parentStm instanceof MCAAssignmentStm){
				stateDesignator = new MCAVariableExp(((MCAAssignmentStm) this.parentStm).getTarget().toFormula(option));
			}
		}
		String varName = stateDesignator.toFormula(MCNode.NAMED);
		String newValueVarName = varName + "_";
		MCPCMLExp newVarValue = new MCAVariableExp(newValueVarName);
		maxCopy.updateBinding(varName,newVarValue); 
		result.append(maxCopy.toFormula(MCNode.NAMED)); 
		
		//THE EXPRESSION OF THE ASSIGNMENT
		result.append(", ");
		
		result.append(newValueVarName + " = " + expression.toFormula(option)); //expression assignment
		
		result.append(".");
		
		return result.toString();
	}

	

	@Override
	public boolean equals(Object arg0) {
		boolean result = false;
		
		if(arg0 instanceof MCAssignDef){
			result = this.counterId == ((MCAssignDef) arg0).getCounterId();
		}
		return result;
	}


	public Binding getMax() {
		return max;
	}


	public void setMax(Binding max) {
		this.max = max;
	}


	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}


	public MCPCMLExp getExpression() {
		return expression;
	}


	public void setExpression(MCPCMLExp expression) {
		this.expression = expression;
	}

	public MCPCMLStm getParentStm() {
		return parentStm;
	}


	public void setParentStm(MCPCMLStm parentStm) {
		this.parentStm = parentStm;
	}


	public MCPCMLExp getStateDesignator() {
		return stateDesignator;
	}


	public void setStateDesignator(MCPCMLExp stateDesignator) {
		this.stateDesignator = stateDesignator;
	}

	
	
}
