package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.FunctionValue;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;

public class CmlValueFactory {

	public static ProcessObjectValue createProcessObjectValue(AProcessDefinition processDefinition)
	{
		return new ProcessObjectValue(processDefinition,null);
	}
	
	public static ActionValue createActionValue(AActionDefinition actionDefinition)
	{
		return new ActionValue(actionDefinition);
	}
	
	public static CmlOperationValue createOperationValue(SCmlOperationDefinition node)
	{
		CmlOperationValue ret = null;
		
		if(node instanceof AExplicitCmlOperationDefinition)
		{
			AExplicitCmlOperationDefinition def = (AExplicitCmlOperationDefinition)node;
			FunctionValue preFunc = null;
			FunctionValue postFunc = null;
			ret = new CmlOperationValue(def, preFunc, postFunc, null);
		}
		
		
		return ret;
	}
}
