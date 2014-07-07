package eu.compassresearch.core.s2c.dom;

public class Property extends NamedUmlNode
{
	public String type;
	public String visibility;
	public Integer lower;
	public Integer upper;

	@Override
	public String toString()
	{
		return visibility + " " + name + " : " + type + " [" + lower + "..."
				+ upper + "]";
	}
}
