package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;

public class MCAActionDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> declarations = new LinkedList<MCPParametrisation>();
	private MCPAction action;

	
	public MCAActionDefinition(String name, LinkedList<MCPParametrisation> declarations,
			MCPAction action) {
		super();
		this.name = name;
		this.declarations = declarations;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("  ProcDef(");
		result.append("\"" + this.name + "\",");
		// parameters
		
		LinkedList<MCPParametrisation> parameters = getDeclarations();
		
		if(parameters.size()==0){
			result.append("nopar");
			result.append(",");
			
			// it converts the internal action (body)
			result.append(this.getAction().toFormula(option));
			result.append(")");
			//if(!question.info.containsKey(Utilities.STATES_KEY)){
			//	result.append(").\n");
			//}
		} else if(parameters.size()==1){
			result.append("SPar(");
			result.append(getDeclarations().getFirst().toFormula(option));
			result.append("),");
			result.append(this.getAction().toFormula(option));
			result.append(")");
		}
		
		//if the action has dependencies we get them from the context
		/*if(question.channelDependencies.size() > 0){
			result.append(" :- ");
			Iterator<String> it = question.channelDependencies.iterator(); 
			do{
				String channelDep = it.next();
				result.append(channelDep);
				if(it.hasNext()){
					result.append(", ");
				}
			}while(it.hasNext());
			
		}*/
		
		result.append(".\n");
		return result.toString();
	}


	public LinkedList<MCPParametrisation> getDeclarations() {
		return declarations;
	}


	public void setDeclarations(LinkedList<MCPParametrisation> declarations) {
		this.declarations = declarations;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
