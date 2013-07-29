package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;

import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.AClassType;

import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;

class CmlToVdmConverter {
	
	public static AClassType createClassType(AProcessDefinition processDefinition)
	{
		AClassType classType = new AClassType();
		classType.setResolved(true);
		classType.setName(processDefinition.getName().clone());
		//classType.setClassdef(createClassClassDefinition(processDefinition));
		ProcessDefinitionAdaptor adaptor = createProcessDefinitionAdaptor(processDefinition);
		adaptor.setClasstype(classType);
		classType.setClassdef(adaptor);
		return classType;
	}
	
	public static AClassType createClassType(ACmlClassDefinition classDefinition)
	{
		//AClassType classType = new AClassType(null,true,new LinkedList<PDefinition>(),classDefinition.getName().clone(),classDefinition);
		//classType.setResolved(true);
		//classType.setName(classDefinition.getName().clone());
		//classType.setClassdef(createClassClassDefinition(processDefinition));
		//CmlClassDefinitionAdaptor adaptor = new CmlClassDefinitionAdaptor(classDefinition);
		//adaptor.setClasstype(classType);
		//classType.setClassdef(classDefinition);
		//return classType;
		return new AClassType(null,true,new LinkedList<PDefinition>(),classDefinition.getName().clone(),classDefinition);
	}
	
//	public static AClassClassDefinition createClassClassDefinition(AProcessDefinition processDefinition)
//	{
//		AClassClassDefinition surrogateProcessDef = new AClassClassDefinition();
//		surrogateProcessDef.setLocation(processDefinition.getLocation());
//		surrogateProcessDef.setName(processDefinition.getName());
//		
//		return surrogateProcessDef;
//	}
	
	public static ProcessDefinitionAdaptor createProcessDefinitionAdaptor(AProcessDefinition processDefinition)
	{
		return new ProcessDefinitionAdaptor(processDefinition);
	}
	
}
