package eu.compassresearch.core.s2c.dom;

public class Trigger extends UmlNode
{
	public Event event;
	
	@Override
	public String toString()
	{
	return "Triggered by: "+event+"";
	}

}
