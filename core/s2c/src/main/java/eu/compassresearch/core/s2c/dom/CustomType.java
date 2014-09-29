package eu.compassresearch.core.s2c.dom;


public class CustomType extends Type
{
	public String definition;

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Customtype " + name + " = "+definition);
		return sb.toString();
	}
}
