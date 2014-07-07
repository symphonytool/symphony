package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class ClassDefinition extends NamedUmlNode
{
	public final List<Property> properties = new Vector<Property>();

	public final List<Operation> operations = new Vector<Operation>();

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Class " + name + "\n\n");

		for (Property p : properties)
		{
			sb.append(p + "\n");
		}

		sb.append("\n\n");

		for (Operation o : operations)
		{
			sb.append(o + "\n");
		}

		return sb.toString();
	}
}
