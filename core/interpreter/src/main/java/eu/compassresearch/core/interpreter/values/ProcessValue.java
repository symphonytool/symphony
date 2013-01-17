package eu.compassresearch.core.interpreter.values;

import java.util.LinkedList;

import org.overture.interpreter.values.NameValuePairMap;

import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.interpreter.cml.CmlProcess;

public class ProcessValue extends CmlObjectValue {

	private CmlProcess processInstance;
	private static final long serialVersionUID = 1L;

	public ProcessValue(AProcessType type, NameValuePairMap members, CmlProcess processInstance,CmlObjectValue creator)
	{
		super(type,members,new LinkedList<CmlObjectValue>(),creator);
		this.processInstance = processInstance;
	}

}
