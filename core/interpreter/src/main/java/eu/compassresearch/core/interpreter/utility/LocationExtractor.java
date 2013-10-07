package eu.compassresearch.core.interpreter.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

public class LocationExtractor
{

	public static ILexLocation extractLocation(INode node)
	{
		try
		{
			Method method = node.getClass().getMethod("getLocation");
			return (ILexLocation) method.invoke(node);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
