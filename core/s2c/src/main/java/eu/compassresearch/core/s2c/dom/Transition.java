package eu.compassresearch.core.s2c.dom;

public class Transition extends UmlNode
{
	public State source;
	public State target;
	public Constraint constraint;
	public Trigger trigger;
	public OpaqueBehavior effect;

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Transition\n");
		sb.append("\tSource: " + source + "\n");
		sb.append("\tTarget: " + target + "\n");
		if (constraint != null)
		{
			sb.append("\t" + constraint + "\n");
		}
		if (trigger != null)
		{
			sb.append("\t" + trigger + "\n");
		}
		if (effect != null)
		{
			sb.append("\tEffect: " + effect + "\n");
		}
		return sb.toString();
	}
}
