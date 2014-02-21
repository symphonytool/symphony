package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> localState = new LinkedList<MCPParametrisation>();
	private MCPProcess process;
	
	
	public MCAProcessDefinition(String name,
			LinkedList<MCPParametrisation> localState, MCPProcess process) {
		
		this.name = name;
		this.localState = localState;
		this.process = process;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAProcessDefinition){
			result = this.name.equals(((MCAProcessDefinition) obj).getName())
					 && this.localState != null && ((MCAProcessDefinition)obj).getLocalState() != null
					 && this.localState.equals(((MCAProcessDefinition) obj).getLocalState());
		}
		return result;

	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		//probably we have to generate more than one procdef because of the arguments
		//the argument has a type. for all values of such a type we generate a procdef
		if(this.localState.size() > 0){
			ExpressionEvaluator expEvaluator = ExpressionEvaluator.getInstance();
			TypeManipulator typeHandler = TypeManipulator.getInstance();
			
			//for the moment we assume that processes have only one parameter
			MCPCMLType paramType = //expEvaluator.instantiateMCType(this.getLocalState().getFirst());
			    ((MCAValParametrisation)this.getLocalState().getFirst()).getDeclaration().getType();
			LinkedList<TypeValue> values = typeHandler.getValues(paramType);
			String variableName = ((MCAValParametrisation)this.getLocalState().getFirst()).getDeclaration().getName(); 
			NameValue mapping = new NameValue(variableName,null);
			for (TypeValue typeValue : values) {
				mapping.setVariableValue(typeValue.toFormula(option));
				context.localVariablesMapping.add(mapping);
				result.append("  ProcDef(\"");
				result.append(this.name);
				result.append("\",");
				result.append(typeValue.toFormula(option));
				result.append(",");
				result.append(this.process.toFormula(option));
				result.append(")");
				context.localVariablesMapping.remove(mapping);
				
				//if the action has dependencies we get them from the context
				LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
				if(dependencies.size() > 0){
					result.append(" :- ");
					for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
						ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
						result.append(actionChannelDependency.toFormula(option));
						if(iterator.hasNext()){
							result.append(",");
						}
					}
				}
				result.append(".\n");
			}
			
		} else {		
			result.append("  ProcDef(\"");
			result.append(this.name);
			result.append("\",");
			//parameters (local state)
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
			MCPCMLType decls =  evaluator.instantiateMCTypeFromParams(this.localState);
			result.append(decls.toFormula(MCNode.NAMED));
			result.append(",");
			
			result.append(this.process.toFormula(option));
			result.append(")");
			
			//if the action has dependencies we get them from the context
			LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendies(this.name);
			if(dependencies.size() > 0){
				result.append(" :- ");
				for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
					ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
					result.append(actionChannelDependency.toFormula(option));
					if(iterator.hasNext()){
						result.append(",");
					}
				}
			}
			
			result.append(".\n");
			}
		
		
		return result.toString();
	}

	

	


	public LinkedList<MCPParametrisation> getLocalState() {
		return localState;
	}

	public void setLocalState(LinkedList<MCPParametrisation> localState) {
		this.localState = localState;
	}

	public MCPProcess getProcess() {
		return process;
	}



	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}
