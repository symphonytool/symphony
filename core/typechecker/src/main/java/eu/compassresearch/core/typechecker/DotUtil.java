package eu.compassresearch.core.typechecker;

import java.io.File;
import java.util.List;

import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.preview.GraphViz.GraphVizException;
import org.overture.ast.preview.Main;

public class DotUtil
{
	/**
	 * Dots the node and saved the svg file in target/tmp.svg
	 * 
	 * @param n
	 */
	public static void dot(INode n)
	{
		dot(n, "tmp");
	}

	public static void dot(INode n, String name)
	{
		Main.dot = new File("dot.exe");
		Main.filterClassNames.add(Boolean.class.getSimpleName());
		Main.filterClassNames.add(Integer.class.getSimpleName());
		Main.filterClassNames.add("LexLocation");
		Main.filterClassNames.add("VDMToken");
		try
		{
			Main.makeImage(n, "svg", new File("target/" + name
					+ ".svg".replace('/', File.separatorChar)));
		} catch (GraphVizException e)
		{
		}
	}

	public static void dot(List<SClassDefinition> classes)
	{
		for (SClassDefinition c : classes)
		{
			DotUtil.dot(c, c.getName().getName());
		}
	}
}
