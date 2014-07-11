package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class DataType extends Type
{
	public final List<Property> properties = new Vector<Property>();

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Datatype " + name + "\n\n");

		for (Property p : properties)
		{
			sb.append(p + "\n");
		}

		return sb.toString();
	}
}
