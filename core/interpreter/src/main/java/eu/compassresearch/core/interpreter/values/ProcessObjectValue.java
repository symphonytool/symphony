package eu.compassresearch.core.interpreter.values;

import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.interpreter.runtime.CmlContext;

public class ProcessObjectValue extends CmlObjectValue {

	private AProcessDefinition processDefinition = null;
	
	public ProcessObjectValue(AProcessDefinition processDefinition, CmlObjectValue creator) {
		super(processDefinition.getType(), creator);
		this.processDefinition = processDefinition;
	}

	public void setMembers(NameValuePairMap members)
	{
		this.members = members;
	}
	
	public AProcessDefinition getProcessDefinition()
	{
		return processDefinition;
	}
	
	private CmlObjectValue mycopy = null;

	@Override
	public CmlObjectValue shallowCopy()
	{
		if (mycopy != null)
		{
			return mycopy;
		}

		mycopy = new ProcessObjectValue(processDefinition,creator);

		NameValuePairMap memcopy = mycopy.members;

		for (LexNameToken name: members.keySet())
		{
			Value mv = members.get(name);

			if (mv.deref() instanceof ObjectValue)
			{
				ObjectValue om = (ObjectValue)mv.deref();
				memcopy.put(name, om.shallowCopy());
			}
			else if (mv.deref() instanceof CmlObjectValue)
			{
				CmlObjectValue om = (CmlObjectValue)mv.deref();
				memcopy.put(name, om.shallowCopy());
			}
			else
			{
				memcopy.put(name, (Value)mv.clone());
			}
		}

		mycopy.setSelf(mycopy);

		CmlObjectValue rv = mycopy;
		mycopy = null;
		return rv;
	}
	
	@Override
	public ProcessObjectValue processObjectValue(CmlContext ctxt)
			throws ValueException {
		return this;
	}
}
