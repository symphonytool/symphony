package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import java.util.LinkedList;

import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCommEv;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAFatEnumVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewSetStack;
import eu.compassresearch.core.analysis.modelchecker.visitors.SetStack;

public class MCACommunicationAction implements MCPAction {

	private String identifier;
	private LinkedList<MCPCommunicationParameter> communicationParameters = new LinkedList<MCPCommunicationParameter>();
	private MCPAction action;
	
	
	
	public MCACommunicationAction(String identifier,
			LinkedList<MCPCommunicationParameter> communicationParameters,
			MCPAction action) {
		super();
		this.identifier = identifier;
		this.communicationParameters = communicationParameters;
		this.action = action;
	}



	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		StringBuilder result = new StringBuilder();
		if(this.communicationParameters.size() == 0){
			result.append("Prefix(CommEv(\"" + this.identifier + "\",\"\", void),");
			result.append(this.action.toFormula(option));
			result.append(")");
			
			//if there is some set of event in the context we must generate lieIn events.
			NewSetStack<MCPVarsetExpression> chanSetStack = context.setStack.copy();
			while(!chanSetStack.isEmpty()){
				MCPVarsetExpression setExp = (MCPVarsetExpression)chanSetStack.pop();
				LinkedList<MCANameChannelExp> chanNames = null;
				if(setExp instanceof AEnumVarsetExpression){
					chanNames = ((MCAEnumVarsetExpression) setExp).getChannelNames();
				}
				if(setExp instanceof MCAFatEnumVarsetExpression){
					chanNames = ((MCAFatEnumVarsetExpression) setExp).getChannelNames();
				}
				if(chanNames != null){
					boolean generateLieIn = false;
					for (MCANameChannelExp aNameChannelExp : chanNames) {
						if(aNameChannelExp.getIdentifier().toString().equals(this.identifier.toString())){
							generateLieIn = true;
							break;
						}
					}
					if(!generateLieIn && chanSetStack.size()==0){
						break;
					}else{
						
						MCCommEv commEv = new MCCommEv(this.identifier,this.communicationParameters, new MCVoidType());
						MCLieInFact lieIn = new MCLieInFact(commEv,setExp); 
						if(!context.lieIn.contains(lieIn)){
							context.lieIn.add(lieIn);
						}
					}
				}
				
			}
		}
		
		return result.toString();
	}



	public String getIdentifier() {
		return identifier;
	}



	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}



	public LinkedList<MCPCommunicationParameter> getCommunicationParameters() {
		return communicationParameters;
	}



	public void setCommunicationParameters(
			LinkedList<MCPCommunicationParameter> communicationParameters) {
		this.communicationParameters = communicationParameters;
	}



	public MCPAction getAction() {
		return action;
	}



	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
