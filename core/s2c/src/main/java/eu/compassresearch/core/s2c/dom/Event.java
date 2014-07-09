package eu.compassresearch.core.s2c.dom;

public class Event extends NamedUmlNode
{
	public Signal signal;
	
	
	@Override
	public String toString()
	{
	return name+" "+signal;
	}
}
