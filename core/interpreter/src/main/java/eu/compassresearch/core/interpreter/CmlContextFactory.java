package eu.compassresearch.core.interpreter;

import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.ObjectValue;

public class CmlContextFactory {

	/**
	 * This creates a normal context which
	 * @param location The location the scope context covers
	 * @param title A description of the context
	 * @param outer The parent context of this context
	 * @return
	 */
	public static Context newContext(LexLocation location, String title, Context outer)
	{
		Context context = new Context(location, title, outer);
		context.setThreadState(null, CPUValue.vCPU);
		return context;
	}
	
	/**
	 * This creates a ObjectContext which is used for operation and function calls.
	 * It makes sure that actions and expression inside this scope can only access values from it own 
	 * scope and the global context. Meaning that it jumps to the the global top level context.
	 * @param location
	 * @param title
	 * @param outer
	 * @param self
	 * @return
	 */
	public static ObjectContext newObjectContext(LexLocation location,String title,Context outer,ObjectValue self)
	{
		ObjectContext objectContext = new ObjectContext(location, title, outer, self);
		objectContext.setThreadState(null, CPUValue.vCPU);
		return objectContext;
	}
	
	public static StateContext newStateContext(LexLocation location, String title)
	{
		StateContext stateContext = new StateContext(location, title);
		stateContext.setThreadState(null, CPUValue.vCPU);
		return stateContext;
	}
}
