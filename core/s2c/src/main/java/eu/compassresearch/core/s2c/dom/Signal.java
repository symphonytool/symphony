package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class Signal extends NamedUmlNode
{
	public final List<Property> property= new Vector<Property>();
	
	@Override
	public String toString()
	{
	return "Signal: "+name+ " "+property;
	}
}
