package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.types.AClassType;

import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;

class CmlToVdmConverter
{

	public static AClassType createClassType(
			AProcessDefinition processDefinition)
	{
		AClassType classType = new AClassType();
		classType.setResolved(true);
		classType.setName(processDefinition.getName().clone());
		// classType.setClassdef(createClassClassDefinition(processDefinition));
		ProcessDefinitionAdaptor adaptor = createProcessDefinitionAdaptor(processDefinition);
		adaptor.setClasstype(classType);
		classType.setClassdef(adaptor);
		return classType;
	}

	public static ProcessDefinitionAdaptor createProcessDefinitionAdaptor(
			AProcessDefinition processDefinition)
	{
		return new ProcessDefinitionAdaptor(processDefinition);
	}

}
