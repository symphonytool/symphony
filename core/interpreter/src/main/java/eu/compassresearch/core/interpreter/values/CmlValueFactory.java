package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.runtime.CmlContext;

public class CmlValueFactory {

	public static ProcessObjectValue createProcessObjectValue(AProcessDefinition processDefinition)
	{
		return new ProcessObjectValue(processDefinition,null);
	}
}
