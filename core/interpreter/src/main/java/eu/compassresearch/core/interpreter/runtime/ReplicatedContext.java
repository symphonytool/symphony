package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

/**
 * A context used by replicated nodes to hold the replication value
 * @author kel
 *
 */
public class ReplicatedContext extends Context
{

	public ReplicatedContext(IInterpreterAssistantFactory af,
			ILexLocation location, String title, Context outer)
	{
		super(af, location, title, outer);
	}

	/**
	 * serial
	 */
	private static final long serialVersionUID = -1888353385791341739L;

	
	@Override
	public Context deepCopy()
	{
		Context below = null;

		if (outer != null)
		{
			below = outer.deepCopy();
		}

		Context result = new ReplicatedContext(assistantFactory,location, title, below);
		result.threadState = threadState;
		
		for (ILexNameToken var: keySet())
		{
			Value v = get(var);
			result.put(var, v.deepCopy());
		}

		return result;
	}
}
