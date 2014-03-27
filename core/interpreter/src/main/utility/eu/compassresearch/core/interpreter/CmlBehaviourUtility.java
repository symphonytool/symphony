package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.runtime.ClassContext;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.runtime.RootContext;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.ReferenceValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;

class CmlBehaviourUtility
{

	public static boolean isAllChildrenFinished(CmlBehaviour process)
	{
		boolean isAllFinished = true;
		for (CmlBehaviour child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}

	public static boolean finishedChildExists(CmlBehaviour process)
	{
		for (CmlBehaviour child : process.children())
		{
			if (child.finished())
			{
				return true;
			}
		}

		return false;
	}

	/*
	 * This method makes a copy of the contexts up until the ObjectContext which represents the current executing
	 * process. So we should never go beyond an Objectcontext
	 */
	public static Context deepCopyProcessContext(Context from)
	{
		Context result = null;
		// We should never copy more than the ObjectContext so If from is a
		// ObjectContext then we stop and point to the old outer
		if (from instanceof ObjectContext)
		{
			// TODO is shallow copy good enough here??? this must be investigated
			result = CmlContextFactory.newObjectContext(from.location, from.title, from.outer, from.getSelf().shallowCopy());
			// If not a ObjectContext and then we continue to copy
			// NB this should never from.outer should never be null
		} else
		{
			result = new Context(from.assistantFactory, from.location, from.title, deepCopyProcessContext(from.outer));
		}

		result.threadState = from.threadState;

		for (ILexNameToken var : from.keySet())
		{
			Value v = from.get(var);
			result.put(var, v.deepCopy());
		}

		return result;
	}
	
	
	private static void replaceObjectMembers(ObjectValue srcObj, ObjectValue dstObj, Context dstContext) throws ValueException
	{
		for (Entry<ILexNameToken, Value> srcEntry : srcObj.getMemberValues().entrySet())
		{
			Value srcVal = srcEntry.getValue();
			if (srcVal instanceof UpdatableValue)
			{
				dstObj.getMemberValues().get(srcEntry.getKey()).set(dstContext.location, srcVal.deref(), dstContext);
			}
			else if(srcVal instanceof ObjectValue)
			{
				replaceObjectMembers((ObjectValue)srcVal,(ObjectValue)dstObj.getMemberValues().get(srcEntry.getKey()).deref(), dstContext);
			}
		}
	}
	
	private static void replaceMembers(ObjectValue srcObj, Context dstContext) throws ValueException
	{
		for (Entry<ILexNameToken, Value> entry : srcObj.getMemberValues().entrySet())
		{
			Value val = entry.getValue();
			if (val instanceof UpdatableValue)
			{
				dstContext.check(entry.getKey()).set(dstContext.location, entry.getValue().deref(), dstContext);
			}
			else if(val instanceof ObjectValue)
			{
				ObjectValue srcObjMember = (ObjectValue)val;
				ObjectValue dstObjMember = dstContext.check(entry.getKey()).objectValue(dstContext);
				replaceObjectMembers(srcObjMember,dstObjMember,dstContext);
				
			}
//			else if(val instanceof ReferenceValue)
//			{
//				System.out.print("ref !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			}
		}
	}

	public static Context mergeAndReplaceState(Context dstContext, Context srcContext)
			throws ValueException
	{
		// Find the root context, this is the current process object root context
		RootContext srcRootContext = srcContext.getRoot();
		ObjectValue srcRootObj = srcRootContext.getSelf();
		RootContext dstRootContext = dstContext.getRoot();

		// replace all the members values with the chosen choice node
		replaceMembers(srcRootObj, dstRootContext);
//		for (Entry<ILexNameToken, Value> entry : srcRootObj.getMemberValues().entrySet())
//		{
//			Value val = entry.getValue();
//			if (val instanceof UpdatableValue)
//			{
//				dstRootContext.check(entry.getKey()).set(dstRootContext.location, entry.getValue().deref(), dstRootContext);
//			}
////			else if(val instanceof ObjectValue)
////			{
////				ObjectValue objectInstant = (ObjectValue)val;
////				objectInstant.getMemberValues()
////				System.out.print("object !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////			}
////			else if(val instanceof ReferenceValue)
////			{
////				System.out.print("ref !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////			}
//		}

		// now we collect all the context below the RootContext for both the copy and the current
		// First we collect the copy contexts
		List<Context> copyContexts = new LinkedList<Context>();
		Context tmp = srcContext;
		while (!tmp.equals(srcRootContext))
		{
			copyContexts.add(0, tmp);
			tmp = tmp.outer;
		}
		// Next we collect the current contexts
		List<Context> contexts = new LinkedList<Context>();
		tmp = dstContext;
		while (!tmp.equals(dstRootContext))
		{
			contexts.add(0, tmp);
			tmp = tmp.outer;
		}

		// We know that the copy context must be at least as big as the current one so we iterate through those
		for (int i = 0; i < copyContexts.size(); i++)
		{
			Context iCopy = copyContexts.get(i);

			// Existing contexts that needs to be replaced
			if (contexts.size() > i)
			{
				Context iCurrent = contexts.get(i);
				for (Entry<ILexNameToken, Value> entry : iCopy.entrySet())
				{
					// Value val = entry.getValue();
					// if(val instanceof UpdatableValue)
					iCurrent.put(entry.getKey(), entry.getValue());
				}
			}
			// newly Added contexts
			else
			{
				// FIXME this should not be created like that a more general solution to this must
				// be made. Eg. a method call that can clone the context with a new outer pointer
				if (iCopy instanceof ClassContext)
				{
					throw new InterpreterRuntimeException("Not yet implemented!");
				} else if (iCopy instanceof ObjectContext)
				{
					dstContext = CmlContextFactory.newObjectContext(iCopy.location, iCopy.title, dstContext, iCopy.getSelf());
				} else if (iCopy instanceof StateContext)
				{
					throw new InterpreterRuntimeException("Trying to merge a StateContext, this should never happen!");
				} else
				{
					dstContext = CmlContextFactory.newContext(iCopy.location, iCopy.title, dstContext);
				}

				for (Entry<ILexNameToken, Value> entry : iCopy.entrySet())
				{
					dstContext.put(entry.getKey(), entry.getValue());
				}
			}
		}

		return dstContext;
	}
}
