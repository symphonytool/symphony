package eu.compassresearch.core.interpreter.debug;

import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FieldValue;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.MapValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;

public class VariableDTO
{

	private final String name;

	private final String fullName;

	private final String type;

	private final String value;

	private final boolean constant;

	private final int childrenCount;

	private final List<VariableDTO> availableChildren;

	private final boolean hasChildren;

	/**
	 * Dummy constructor for serialization
	 */
	@SuppressWarnings("unused")
	private VariableDTO()
	{
		this.name = null;
		this.fullName = null;
		this.type = null;
		this.value = null;
		this.childrenCount = -1;
		this.availableChildren = null;
		this.hasChildren = false;
		this.constant = false;
	}

	public VariableDTO(String name, String fullName, String type, String value,
			int childrenCount, boolean hasChildren, boolean constant,
			List<VariableDTO> availableChildren)
	{
		this.name = name;
		this.fullName = fullName;
		this.type = type;
		this.value = value;
		this.childrenCount = childrenCount;
		this.availableChildren = availableChildren;
		this.hasChildren = hasChildren;
		this.constant = constant;
	}

	public String getName()
	{
		return name;
	}

	public String getFullName()
	{
		return fullName;
	}

	public String getType()
	{
		return type;
	}

	public String getValue()
	{
		return value;
	}

	public boolean isConstant()
	{
		return constant;
	}

	public int getChildrenCount()
	{
		return childrenCount;
	}

	public List<VariableDTO> getAvailableChildren()
	{
		return availableChildren;
	}

	public boolean hasChildren()
	{
		return hasChildren;
	}

	public static List<VariableDTO> extractVariables(Context context)
	{
		List<VariableDTO> variables = new Vector<VariableDTO>();

		for (Entry<ILexNameToken, Value> var : context.entrySet())
		{
			ILexNameToken name = var.getKey();
			Value val = var.getValue();

			if (!showValue(val))
			{
				continue;
			}

			variables.add(extractVariable(name.getName(), name.getFullName(), val));
		}

		//
		ObjectValue selfVal = context.getSelf();
		if (selfVal != null)
		{
			for (NameValuePair nvp : selfVal.getMemberValues().asList())
			{
				if (!showValue(nvp.value))
				{
					continue;
				}

				variables.add(extractVariable(nvp.name.getName(), nvp.name.getFullName(), nvp.value));
			}
		}

		return variables;
	}

	private static boolean showValue(Value value)
	{
		if (value instanceof FunctionValue || value instanceof OperationValue
				|| value instanceof ActionValue)
		{
			return false;
		} else
		{
			return true;
		}

	}

	public static VariableDTO extractVariable(String name, String fullName,
			Value val)
	{
		String kind = val.kind();
		List<VariableDTO> children = new Vector<VariableDTO>();
		Value derefedVal = val.deref();
		if (derefedVal instanceof SetValue)
		{
			SetValue v = (SetValue) derefedVal;
			int i = 0;
			for (Value vv : v.values)
			{
				i++;
				children.add(extractVariable("[" + i + "]", "[" + i + "]", vv));
			}
		} else if (derefedVal instanceof SeqValue)
		{
			SeqValue v = (SeqValue) derefedVal;
			int i = 0;
			for (Value vv : v.values)
			{
				i++;
				children.add(extractVariable("[" + i + "]", "[" + i + "]", vv));
			}
		} else if (derefedVal instanceof MapValue)
		{
			MapValue v = (MapValue) derefedVal;
			int i = 0;
			for (Entry<Value, Value> vv : v.values.entrySet())
			{
				i++;
				VariableDTO dom = extractVariable("dom", "dom", vv.getKey());
				VariableDTO rng = extractVariable("rng", "rng", vv.getValue());

				List<VariableDTO> childrenMaplet = new Vector<VariableDTO>();
				childrenMaplet.add(dom);
				childrenMaplet.add(rng);

				VariableDTO maplet = new VariableDTO("Maplet " + i, fullName, val.kind(), "{"
						+ vv.getKey() + " |-> " + vv.getValue() + "}", childrenMaplet.size(), !childrenMaplet.isEmpty(), !(val instanceof UpdatableValue), childrenMaplet);
				children.add(maplet);
			}
		}else if(derefedVal instanceof RecordValue)
		{
			RecordValue v = (RecordValue) derefedVal;
			for (FieldValue vv : v.fieldmap)
			{
				VariableDTO fieldDto = extractVariable(vv.name, vv.name, vv.value);

				List<VariableDTO> childrenMaplet = new Vector<VariableDTO>();
				childrenMaplet.add(fieldDto);

				children.add(fieldDto);
			}
		}else if(derefedVal instanceof ObjectValue)
		{
			ObjectValue v = (ObjectValue) derefedVal;
			if(v instanceof ProcessObjectValue)
			{
				ProcessObjectValue vp = (ProcessObjectValue) derefedVal;
				kind = vp.getProcessDefinition().getName().getName();
			}if(v.type.getClassdef() instanceof AActionClassDefinition)
			{
				AProcessDefinition d = v.type.getClassdef().getAncestor(AProcessDefinition.class);
				if(d!=null)
				{
					kind = d.getName().getName();
				}
			}
			for (Entry<ILexNameToken, Value> vv : v.members.entrySet())
			{
				if(!showValue(vv.getValue()))
				{
					continue;
				}
				
				VariableDTO fieldDto = extractVariable(vv.getKey().getName(), vv.getKey().getName(), vv.getValue());

				List<VariableDTO> childrenMaplet = new Vector<VariableDTO>();
				childrenMaplet.add(fieldDto);

				children.add(fieldDto);
			}
		}

		return new VariableDTO(name, fullName, kind, val.toString(), children.size(), !children.isEmpty(), !(val instanceof UpdatableValue), children);
	}
}
