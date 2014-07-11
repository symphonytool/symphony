package eu.compassresearch.core.s2c.dom;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Operation extends NamedUmlNode
{
	public String visibility;

	public List<Parameter> parameters = new Vector<Parameter>();

	public OpaqueBehavior body;

	public boolean isStatic;

	public List<Parameter> getParameters()
	{
		List<Parameter> list = new Vector<Parameter>();

		for (Parameter parameter : parameters)
		{
			if (!parameter.isReturn)
			{
				list.add(parameter);
			}
		}
		return list;
	}

	public Parameter getReturn()
	{
		for (Parameter parameter : parameters)
		{
			if (parameter.isReturn)
			{
				return parameter;
			}
		}
		return null;
	}
	
	public String getVisibility()
	{
		if (visibility == null)
		{
			return "";
		}
		return visibility;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(visibility + " " + name + " (");

		for (Iterator<Parameter> iterator = getParameters().iterator(); iterator.hasNext();)
		{
			Parameter p = iterator.next();
			sb.append(p);
			if (iterator.hasNext())
			{
				sb.append(", ");
			}
		}

		if (getReturn() != null)
		{
			sb.append(") " + getReturn().type);
		}
		return sb.toString();
	}
}
