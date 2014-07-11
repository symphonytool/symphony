package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class ClassDefinition extends NamedUmlNode
{
	public final List<Property> properties = new Vector<Property>();

	public final List<Operation> operations = new Vector<Operation>();

	public final List<Type> types = new Vector<Type>();

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Class " + name + "\n\n");

		if (!type.isEmpty())
		{
			sb.append("\n");

			for (Type o : types)
			{
				sb.append(o + "\n");
			}
		}

		for (Property p : properties)
		{
			sb.append(p + "\n");
		}

		sb.append("\n");

		for (Operation o : operations)
		{
			sb.append(o + "\n");
		}

		return sb.toString();
	}
}
