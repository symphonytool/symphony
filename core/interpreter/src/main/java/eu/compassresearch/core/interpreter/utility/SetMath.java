package eu.compassresearch.core.interpreter.utility;

import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

public class SetMath
{

	public static SetValue getCrossProduct(List<SetValue> sets)
	{
		SetValue result = new SetValue();
		getCrossProduct(result, sets, 0, new LinkedList<Value>());
		return result;
	}

	private static void getCrossProduct(SetValue result, List<SetValue> sets,
			int depth, List<Value> current)
	{
		for (int i = 0; i < sets.get(depth).values.size(); i++)
		{
			List<Value> tmp = new LinkedList<Value>(current);
			tmp.add(depth, sets.get(depth).values.get(i));
			if (depth < sets.size() - 1)
			{
				getCrossProduct(result, sets, depth + 1, tmp);
			} else
			{
				ValueList vl = new ValueList();

				for (Value val : tmp)
				{
					vl.add(val);
				}

				result.values.add(new TupleValue(vl));
				// current = new LinkedList<Value>();
			}
		}
	}

}
