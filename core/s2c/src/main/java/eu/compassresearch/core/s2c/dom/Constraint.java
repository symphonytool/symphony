package eu.compassresearch.core.s2c.dom;

public class Constraint extends UmlNode
{
	public OpaqueExpression expression;

	@Override
	public String toString()
	{
		return "Constrained by: " + expression;
	}
}
