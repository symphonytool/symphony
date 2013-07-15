package eu.compassresearch.core.analysis.pog.obligations;

import java.util.Stack;

import org.overture.ast.expressions.PExp;
import org.overture.ast.types.PType;
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.pub.IPOContext;
import org.overture.pog.pub.IPOContextStack;

@SuppressWarnings("serial")
public class CMLPOContextStack extends Stack<CMLPOContext> implements
		IPOContextStack
{
	public String getName()
	{
		StringBuilder result = new StringBuilder();
		String prefix = "";
		for (CMLPOContext ctxt : this)
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

	public String getGUIString(String poValue)
	{
		StringBuilder result = new StringBuilder();
		String spacing = "  ";
		String indent = "";
		StringBuilder tail = new StringBuilder();
		for (CMLPOContext ctxt : this)
		{
			String po = ctxt.getGUIContext();
			if (po.length() > 0)
			{
				result.append(indent);
				result.append("(");
				result.append(indentNewLines(po, indent));
				result.append("\n");
				indent = indent + spacing;
				tail.append(")");
			}
		}
		result.append(indent);
		result.append(indentNewLines(poValue, indent));
		result.append(tail);
		result.append("\n");
		return result.toString();
	}

	private String indentNewLines(String line, String indent)
	{
		StringBuilder sb = new StringBuilder();
		String[] parts = line.split("\n");
		String prefix = "";
		for (int i = 0; i < parts.length; i++)
		{
			sb.append(prefix);
			sb.append(parts[i]);
			prefix = "\n" + indent;
		}
		return sb.toString();
	}

	// TODO Need to determine how to format PO for Isabelle, will use getIsabelleContext() method
	public String getIsabelleString(String poValue)
	{
		return "";
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
	public IPOContext push(IPOContext context)
	{
		if (context instanceof CMLPOContext)
		{
			super.push((CMLPOContext) context);
		}
		return context;

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
