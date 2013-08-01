package eu.compassresearch.core.analysis.pog.obligations;

import java.util.Stack;

import org.overture.ast.expressions.PExp;
import org.overture.ast.types.PType;
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.pub.IPOContext;
import org.overture.pog.pub.IPOContextStack;

@SuppressWarnings("serial")
public class CmlPOContextStack extends Stack<IPOContext> implements
		IPOContextStack
{
	public String getName()
	{
		StringBuilder result = new StringBuilder();
		String prefix = "";
		for (IPOContext ctxt : this)
		{
			String name = ctxt.getName();
			if (name.length() > 0)
			{
				result.append(prefix);
				result.append(name);
				prefix = ", ";
			}
		}
		return result.toString();
	}


	@Override
	public PType checkType(PExp exp, PType expected)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void noteType(PExp exp, PType PType)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getObligation(String root)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PExp getPredWithContext(PExp initialPredicate)
	{
		for (int i = this.size() - 1; i >= 0; i--)
		{
			IPOContext ctxt = this.get(i);
			if (!(ctxt instanceof PONameContext))
			{
				initialPredicate = ctxt.getContextNode(initialPredicate);
			}
		}
		return initialPredicate;

	}


}
