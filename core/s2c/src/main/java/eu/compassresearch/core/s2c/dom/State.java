package eu.compassresearch.core.s2c.dom;

public class State extends NamedUmlNode
{
	public NamedUmlNode entry;
	public NamedUmlNode exit;
	public String kind;

	@Override
	public String toString()
	{
		return name + (kind != null ? " kind=" + kind : "")
				+ (entry != null ? " Entry=(" + entry+")" : "")
				+ (exit != null ? " Exit=(" + exit+")" : "");
	}
}
