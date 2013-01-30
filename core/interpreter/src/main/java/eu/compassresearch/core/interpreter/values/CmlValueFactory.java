package eu.compassresearch.core.interpreter.values;

import org.overture.ast.expressions.PExp;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AFunctionType;
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
			PExp preExp = def.getPrecondition();
			AFunctionType ftype = new AFunctionType(node.getLocation(), true, false);
			ftype.setResult(new ABooleanBasicType());
			
			//ftype.setParameters(	)
			
			
			FunctionValue preFunc = null;
//					new FunctionValue(node.getLocation(), 
//					def.getName().getPreName(preExp.getLocation()),  
//					ftype, 
//					paramPatterns, 
//					def.getPrecondition(),  
//					freeVariables); 
	
			FunctionValue postFunc = null;
			ret = new CmlOperationValue(def, preFunc, postFunc, null);
		}
		
		
		return ret;
	}
}
