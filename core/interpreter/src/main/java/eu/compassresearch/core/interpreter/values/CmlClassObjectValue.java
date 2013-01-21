package eu.compassresearch.core.interpreter.values;

import java.util.List;
import java.util.Vector;

import org.overture.ast.assistant.pattern.PTypeList;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.ast.util.Utils;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

public class CmlClassObjectValue extends CmlObjectValue {

	private final List<CmlClassObjectValue> superobjects;
	
	public CmlClassObjectValue(PType type, NameValuePairMap members,
			List<CmlClassObjectValue> superobjects, CmlObjectValue creator) {
		super(type, members, creator);
		this.superobjects = superobjects;
	}

	@Override
	protected void setSelf(CmlObjectValue self)
	{
		super.setSelf(self);

		for (CmlObjectValue obj: superobjects)
		{
			obj.setSelf(self);
		}
	}
	
	public PTypeList getBaseTypes()
	{
		PTypeList basetypes = new PTypeList();

		if (superobjects.isEmpty())
		{
			basetypes.add(type);
		}
		else
		{
    		for (CmlClassObjectValue sup: superobjects)
    		{
    			basetypes.addAll(sup.getBaseTypes());
    		}
		}

		return basetypes;
	}
	
	@Override
	public Value get(LexNameToken field, boolean explicit)
	{
		Value rv = super.get(field, explicit);
		
		if (rv != null)
		{
			return rv;
		}

		for (CmlObjectValue svalue: superobjects)
		{
			rv = svalue.get(field, explicit);

			if (rv != null)
			{
				return rv;
			}
		}
		
		return null;
	}
	
	@Override
	public ValueList getOverloads(LexNameToken field)
	{
		ValueList list = super.getOverloads(field);

		if (!list.isEmpty())
		{
			return list;	// Only names from one level
		}

		for (CmlObjectValue svalue: superobjects)
		{
			list = svalue.getOverloads(field);

			if (!list.isEmpty())
			{
				return list;
			}
		}

		return list;
	}
	
	@Override
	public NameValuePairMap getMemberValues()
	{
		NameValuePairMap nvpm = super.getMemberValues();

		for (CmlObjectValue svalue: superobjects)
		{
			nvpm.putAll(svalue.getMemberValues());
		}
		return nvpm;
	}
	
	@Override
	public String toString()
	{
		if (inToString)
		{
			return "{#" + objectReference + " recursive}";
		}

		inToString = true;
		StringBuilder sb = new StringBuilder();
		sb.append(type.toString());
		sb.append("{#" + objectReference);

		for (LexNameToken name: members.keySet())
		{
			Value ov = members.get(name);
			Value v = ov.deref();

			if (!(v instanceof FunctionValue) &&
				!(v instanceof OperationValue))
			{
				sb.append(", ");
				sb.append(name.name);

				if (ov instanceof UpdatableValue)
				{
					sb.append(":=");
				}
				else
				{
					sb.append("=");
				}

				sb.append(v.toString());
			}
		}

		if (!superobjects.isEmpty())
		{
			sb.append(", ");
			sb.append(Utils.listToString(superobjects));
		}

		sb.append("}");
		inToString = false;
		return sb.toString();
	}
	
	private CmlClassObjectValue mycopy = null;
	
	@Override
	public CmlObjectValue shallowCopy()
	{
		if (mycopy != null)
		{
			return mycopy;
		}

		mycopy = new CmlClassObjectValue(type,
					new NameValuePairMap(), new Vector<CmlClassObjectValue>(), creator);

		List<CmlClassObjectValue> supers = mycopy.superobjects;
		NameValuePairMap memcopy = mycopy.members;

   		for (CmlClassObjectValue sobj: superobjects)
   		{
   			supers.add(	// Type skeleton only...
   				new CmlClassObjectValue(sobj.type,
   					new NameValuePairMap(), new Vector<CmlClassObjectValue>(), creator));
   		}

		for (LexNameToken name: members.keySet())
		{
			Value mv = members.get(name);

			if (mv.deref() instanceof ObjectValue)
			{
				ObjectValue om = (ObjectValue)mv.deref();
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
	
}
