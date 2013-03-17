package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;

public class CmlValueFactory {

	public static ProcessObjectValue createProcessObjectValue(AProcessDefinition processDefinition, ObjectValue creator)
	{
		return new ProcessObjectValue(processDefinition,new NameValuePairMap(),creator);
	}
	
	public static ActionValue createActionValue(AActionDefinition actionDefinition)
	{
		return new ActionValue(actionDefinition);
	}
	
	public static CmlOperationValue createOperationValue(SCmlOperationDefinition node, Context question)
	{
		CmlOperationValue ret = null;
		
		if(node instanceof AExplicitCmlOperationDefinition)
		{
			AExplicitCmlOperationDefinition def = (AExplicitCmlOperationDefinition)node;
			
			FunctionValue prefunc =
					(((AExplicitCmlOperationDefinition) node).getPredef() == null) ? null : new FunctionValue(((AExplicitCmlOperationDefinition) node).getPredef(), null, null, null);

			FunctionValue postfunc =
					(((AExplicitCmlOperationDefinition) node).getPostdef() == null) ? null : new FunctionValue(((AExplicitCmlOperationDefinition) node).getPostdef(), null, null, null);
			
			ret = new CmlOperationValue(def, prefunc, postfunc, null);
			
		}
		
		
		return ret;
	}
}
