package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NameValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAActionDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCPParametrisation> declarations = new LinkedList<MCPParametrisation>();
	private MCPAction action;

	
	public MCAActionDefinition(String name, LinkedList<MCPParametrisation> declarations,
			MCPAction action) {
		
		this.name = name;
		this.declarations = declarations;
		this.action = action;
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAActionDefinition){
			result = this.name.equals(((MCAActionDefinition) obj).getName())
					 && this.declarations.equals(((MCAActionDefinition) obj).getDeclarations());
		}
		return result;
	}



	@Override
	public String toFormula(String option) {
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		StringBuilder result = new StringBuilder();
		
		if(declarations.size() > 0){
			//for the moment we assume that processes have only one parameter
			MCPParametrisation param = declarations.getFirst();
			if(param instanceof MCAValParametrisation){
				MCALocalDefinition localDef = ((MCAValParametrisation) param).getDeclaration();
				NameValue mapping = new NameValue(localDef.getName(),null);
				TypeManipulator typeHandler = TypeManipulator.getInstance();
				LinkedList<TypeValue> values = typeHandler.getValues(localDef.getType());
				for (TypeValue typeValue : values) {
					mapping.setVariableValue(typeValue.toFormula(option));
					context.localVariablesMapping.add(mapping);
					result.append("  ProcDef(\"");
					result.append(this.name);
					result.append("\",");
					result.append(typeValue.toFormula(option));
					result.append(",");
					result.append(this.getAction().toFormula(option));
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
			}
			
			
			
		}else{
			result.append("  ProcDef(");
			result.append("\"" + this.name + "\",");
			
			// parameters
			ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance(); 
			MCPCMLType paramType  = evaluator.instantiateMCTypeFromParams(this.declarations);
	
			result.append(paramType.toFormula(option));
			result.append(",");
			result.append(this.getAction().toFormula(option));
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

	

	@Override
	public String toString() {
		
		return this.name;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}
