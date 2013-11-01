package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASingleGeneralAssignmentStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Binding;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPreOpTrue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCStringType;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCPCMLPattern;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAExplicitCmlOperationDefinition implements
		MCSCmlOperationDefinition {

	private String name;
	private MCPAction body;
	private MCPCMLExp precondition;
	private MCPCMLExp postcondition;
	private MCAExplicitFunctionDefinition predef;
	private MCAExplicitFunctionDefinition postdef;
	private LinkedList<MCPCMLPattern> paramPatterns = new LinkedList<MCPCMLPattern>();
	private MCAStateDefinition state;
	private MCPCMLType actualResult;
	
	private MCPAction parentAction;
	
	public MCAExplicitCmlOperationDefinition(String name, MCPAction body,
			MCPCMLExp precondition, MCPCMLExp postcondition,
			MCAExplicitFunctionDefinition predef,
			MCAExplicitFunctionDefinition postdef,
			LinkedList<MCPCMLPattern> paramPatterns,
			MCAStateDefinition state, MCPCMLType actualResult, MCPAction parentAction) {
		super();
		this.name = name;
		this.body = body;
		this.precondition = precondition;
		this.postcondition = postcondition;
		this.predef = predef;
		this.postdef = postdef;
		this.paramPatterns = paramPatterns;
		this.state = state;
		this.actualResult = actualResult;
		this.parentAction = parentAction;
	}


	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		
		result.append("operationDef(");
		result.append("\"" + this.name + "\"");
		result.append(",");
		if(this.paramPatterns.size()==0){
			result.append("void");
		}else if(this.paramPatterns.size()==1){
			result.append(this.paramPatterns.getFirst().toFormula(option));
		} else if (this.paramPatterns.size() > 1){
			//TODO
			//this can be similar to a list of expressions transformed in ProdType 
		}
		result.append(",");
		result.append("st");
		result.append(",");
		result.append("st_");
		result.append(") :- ");
		result.append("State(l,st,_,");
		if(this.parentAction != null){
			result.append(this.parentAction.toFormula(option));
		}else{
			result.append("action invoking the assignment");
		}
		result.append(")");
		result.append(",");
		result.append("st = ");
		result.append(context.maximalBinding.toFormula(MCNode.NAMED));
		result.append(",");
		result.append("st_ = ");
		Binding maximalCopy = context.maximalBinding.copy();
		MCPAction body = this.body;
		MCASingleGeneralAssignmentStatementAction actionBody = null;
		if(body instanceof MCASingleGeneralAssignmentStatementAction){
			actionBody = (MCASingleGeneralAssignmentStatementAction) body;
		}
		
		String newValueVarName = "";
		
		if(actionBody != null){
			MCPCMLExp stateDesignator = actionBody.getStateDesignator();
			if(stateDesignator instanceof MCAVariableExp){
				String varName = stateDesignator.toFormula(MCNode.NAMED);
				newValueVarName = varName + "_";
				MCPCMLType newVarValue = new MCANamedInvariantType(newValueVarName);
				maximalCopy.updateBinding(varName,newVarValue); 
				result.append(maximalCopy.toFormula(MCNode.DEFAULT)); 
			}
		}
		
		//THE EXPRESSION OF THE ASSIGNMENT
		if(actionBody.getExpression() != null){
			result.append(", ");
			result.append(newValueVarName + " = " + actionBody.getExpression().toFormula(option)); //expression assignment
		}
		result.append(".");
		result.append("\n");
		
		if(this.precondition != null){
			//convert precondition
		}else{
			//we assume that all preconditions are true for the moment
			MCPreOpTrue preOp = new MCPreOpTrue(name, paramPatterns);
			result.append(preOp.toFormula(option));
		}
		result.append("\n");
		return result.toString();
	}


	public MCPAction getBody() {
		return body;
	}


	public void setBody(MCPAction body) {
		this.body = body;
	}


	public MCPCMLExp getPrecondition() {
		return precondition;
	}


	public void setPrecondition(MCPCMLExp precondition) {
		this.precondition = precondition;
	}


	public MCPCMLExp getPostcondition() {
		return postcondition;
	}


	public void setPostcondition(MCPCMLExp postcondition) {
		this.postcondition = postcondition;
	}


	public MCAExplicitFunctionDefinition getPredef() {
		return predef;
	}


	public void setPredef(MCAExplicitFunctionDefinition predef) {
		this.predef = predef;
	}


	public MCAExplicitFunctionDefinition getPostdef() {
		return postdef;
	}


	public void setPostdef(MCAExplicitFunctionDefinition postdef) {
		this.postdef = postdef;
	}


	
	public LinkedList<MCPCMLPattern> getParamPatterns() {
		return paramPatterns;
	}


	public void setParamPatterns(LinkedList<MCPCMLPattern> paramPatterns) {
		this.paramPatterns = paramPatterns;
	}


	public MCAStateDefinition getState() {
		return state;
	}


	public void setState(MCAStateDefinition state) {
		this.state = state;
	}


	public MCPCMLType getActualResult() {
		return actualResult;
	}


	public void setActualResult(MCPCMLType actualResult) {
		this.actualResult = actualResult;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MCPAction getParentAction() {
		return parentAction;
	}


	public void setParentAction(MCPAction parentAction) {
		this.parentAction = parentAction;
	}
	
	

}
