package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;









import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;

import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCASingleGeneralAssignmentStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPreOpTrue;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAVariableExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Str;
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
	private LinkedList<MCPCMLDefinition> paramDefinitions = new LinkedList<MCPCMLDefinition>();
	private MCAStateDefinition state;
	private MCPCMLType actualResult;
	
	private MCPAction parentAction;
	
	public MCAExplicitCmlOperationDefinition(String name, MCPAction body,
			MCPCMLExp precondition, MCPCMLExp postcondition,
			MCAExplicitFunctionDefinition predef,
			MCAExplicitFunctionDefinition postdef,
			LinkedList<MCPCMLDefinition> paramDefinitions,
			MCAStateDefinition state, MCPCMLType actualResult, MCPAction parentAction) {
		super();
		this.name = name;
		this.body = body;
		this.precondition = precondition;
		this.postcondition = postcondition;
		this.predef = predef;
		this.postdef = postdef;
		this.paramDefinitions = paramDefinitions;
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
		if(this.paramDefinitions.size()==0){
			result.append("void");
		}else if(this.paramDefinitions.size()==1){
			result.append(this.paramDefinitions.getFirst().toFormula(option));
		} else if (this.paramDefinitions.size() > 1){
			//TODO
		}
		result.append(",");
		result.append("st");
		result.append(",");
		result.append("st_");
		result.append(") :- ");
		result.append("State(l,st,_,");
		result.append(this.parentAction.toFormula(option));
		result.append(")");
		result.append(",");
		result.append("st = ");
		result.append(context.maximalBinding.toFormula(MCNode.NAMED));
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
				Str newVarValue = new Str(newValueVarName);
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
			MCPreOpTrue preOp = new MCPreOpTrue(name, paramDefinitions);
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


	public LinkedList<MCPCMLDefinition> getParamDefinitions() {
		return paramDefinitions;
	}


	public void setParamDefinitions(LinkedList<MCPCMLDefinition> paramDefinitions) {
		this.paramDefinitions = paramDefinitions;
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
