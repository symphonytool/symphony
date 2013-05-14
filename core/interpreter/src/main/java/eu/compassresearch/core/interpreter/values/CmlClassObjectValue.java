package eu.compassresearch.core.interpreter.values;

import java.util.LinkedList;

import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.AClassDefinition;

public class CmlClassObjectValue extends ObjectValue {

	private AClassDefinition classDefinition = null;
	
	public CmlClassObjectValue(AClassDefinition classDefinition, NameValuePairMap members, ObjectValue creator) {
		super(CmlToVdmConverter.createClassType(classDefinition), members, new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.classDefinition = classDefinition;
	}
	
	
	public AClassDefinition getClassDefinition()
	{
		return classDefinition;
	}
}
