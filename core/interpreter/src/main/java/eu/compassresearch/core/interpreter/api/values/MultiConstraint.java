package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.values.Value;

public class MultiConstraint implements ValueConstraint
{

	List<ValueConstraint> constraints;

	public MultiConstraint(List<ValueConstraint> constraints)
	{
		this.constraints = constraints;
	}

	public MultiConstraint(ValueConstraint firstConstraint,
			ValueConstraint secondConstraint)
	{
		this.constraints = new LinkedList<ValueConstraint>();
		this.constraints.add(firstConstraint);
		this.constraints.add(secondConstraint);
	}

	@Override
	public boolean isValid(Value val) throws AnalysisException
	{

		for (ValueConstraint vc : this.constraints)
		{
			if (!vc.isValid(val))
			{
				return false;
			}
		}

		return true;
	}

	public static ValueConstraint combine(ValueConstraint c1,
			ValueConstraint c2)
	{
		if(c1 instanceof NoConstraint && c2 instanceof NoConstraint)
		{
			return c1;
		}
		return new MultiConstraint(c1,c2);
	}

}
