package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;

import org.overture.ast.types.AClassType;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.definitions.ACmlClassDefinition;

public class CmlClassObjectValue extends ObjectValue {

	private ACmlClassDefinition classDefinition = null;
	
	public CmlClassObjectValue(ACmlClassDefinition classDefinition, NameValuePairMap members, ObjectValue creator) {
		super((AClassType)classDefinition.getType(), members, new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.classDefinition = classDefinition;
	}
	
	public ACmlClassDefinition getClassDefinition()
	{
		return classDefinition;
	}
}
