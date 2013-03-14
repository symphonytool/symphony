package eu.compassresearch.core.interpreter.util;

import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.types.AClassType;

import eu.compassresearch.ast.definitions.AProcessDefinition;

public class CmlToVdmConverter {
	
	public static AClassType createClassType(AProcessDefinition processDefinition)
	{
		AClassType classType = new AClassType();
		classType.setResolved(true);
		classType.setName(processDefinition.getName());
		classType.setClassdef(createClassClassDefinition(processDefinition));
		return classType;
	}
	
	public static AClassClassDefinition createClassClassDefinition(AProcessDefinition processDefinition)
	{
		AClassClassDefinition surrogateProcessDef = new AClassClassDefinition();
		surrogateProcessDef.setLocation(processDefinition.getLocation());
		surrogateProcessDef.setName(processDefinition.getName());
		
		return surrogateProcessDef;
	}
	
}
