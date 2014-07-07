package eu.compassresearch.core.s2c.dom;

public class Parameter extends NamedUmlNode
{
	// optional name
	public boolean isReturn;
	public String type;

	@Override
	public String toString()
	{
		return (isReturn ? "return " : name) + " : " + type;
	}
}
