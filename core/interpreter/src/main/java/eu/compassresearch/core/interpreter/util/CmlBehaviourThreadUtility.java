package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.ClassContext;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.runtime.RootContext;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class CmlBehaviourThreadUtility {

	public static boolean isAllChildrenFinished(CmlBehaviour process)
	{
		boolean isAllFinished = true;
		for(CmlBehaviour child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}
	
	public static boolean finishedChildExists(CmlBehaviour process)
	{
		for(CmlBehaviour child : process.children())
		{
			if(child.finished())
				return true;
		}
		
		return false;
	}
	
	public static Context mergeState(Context dst, Context src)  throws ValueException
	{
		Context newCurrent = dst;
		//Find the root context, this is the current process object root context
		RootContext copyRoot = src.getRoot();
		RootContext currentRoot = dst.getRoot(); 

		//replace all the members values with the chosen choice node
		for(Entry<LexNameToken,Value> entry : copyRoot.getSelf().getMemberValues().entrySet())
		{
			Value val = entry.getValue();
			if(val instanceof UpdatableValue)
				currentRoot.check(entry.getKey()).set(dst.location, entry.getValue(), dst);
		}

		//now we collect all the context below the RootContext for both the copy and the current
		//First we collect the copy contexts
		List<Context> copyContexts = new LinkedList<Context>();
		Context tmp = src;
		while(!tmp.equals(copyRoot))
		{
			copyContexts.add(0,tmp);
			tmp = tmp.outer;
		}
		//Next we collect the current contexts
		List<Context> contexts = new LinkedList<Context>();
		tmp = dst;
		while(!tmp.equals(currentRoot))
		{
			contexts.add(0,tmp);
			tmp = tmp.outer;
		}

		//We know that the copy context must be at least as big as the current one so we iterate through those
		for(int i = 0 ; i < copyContexts.size();i++)
		{
			Context iCopy = copyContexts.get(i);

			//Existing contexts that needs to be replaced
			if(contexts.size() > i)
			{
				Context iCurrent = contexts.get(i);
				for(Entry<LexNameToken,Value> entry : iCopy.entrySet())
				{
					Value val = entry.getValue();
					if(val instanceof UpdatableValue)
						iCurrent.put(entry.getKey(), entry.getValue());
				}
			}
			//newly Added contexts
			else
			{
				//FIXME this should not be created like that a more general solution to this must
				//be made. Eg. a method call that can clone the context with a new outer pointer
				if(iCopy instanceof ClassContext)
					throw new InterpreterRuntimeException("Not yet implemented!");
				else if(iCopy instanceof ObjectContext)
					newCurrent = CmlContextFactory.newObjectContext(iCopy.location, iCopy.title, newCurrent, iCopy.getSelf());
				else if(iCopy instanceof StateContext)
					throw new InterpreterRuntimeException("Trying to merge a StateContext, this should never happen!");
				else
					newCurrent = CmlContextFactory.newContext(iCopy.location, iCopy.title, newCurrent);
				
				
				for(Entry<LexNameToken,Value> entry : iCopy.entrySet())
				{
					newCurrent.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		return newCurrent;
	}
}
