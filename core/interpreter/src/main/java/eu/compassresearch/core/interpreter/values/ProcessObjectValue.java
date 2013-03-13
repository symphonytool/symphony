package eu.compassresearch.core.interpreter.values;

import java.util.LinkedList;

import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.interpreter.util.CmlToVdmConverter;

public class ProcessObjectValue extends ObjectValue {

	private AProcessDefinition processDefinition = null;
	
	public ProcessObjectValue(AProcessDefinition processDefinition, NameValuePairMap members, ObjectValue creator) {
		super(CmlToVdmConverter.createClassType(processDefinition), members, new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.processDefinition = processDefinition;
	}
	
	
	public AProcessDefinition getProcessDefinition()
	{
		return processDefinition;
	}

}
