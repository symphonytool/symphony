package eu.compassresearch.core.s2c.dom;

import java.util.List;
import java.util.Vector;

public class EnumType extends Type
{
	public List<String> literals = new Vector<String>();
	
	@Override
	public String toString()
	{
	return name+" "+literals;
	}
}
