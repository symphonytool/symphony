package eu.compassresearch.core.typechecker.secondedition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class TestConverter
{
	public static void convert(String path, String name, int index,
			String content, boolean parseok, boolean tcok)
	{
		if (!parseok)
		{
			return;
		}

		convert(path, name, (tcok ? "-pos" : "-neg"), index, content);
	}

	public static void convert(String path, String name, String postFix,
			int index, String content)
	{
		File newTextFile = new File((path + name + "/" + name + "-"
				+ String.format("%04d", index) + postFix + ".cml").replace('/', File.separatorChar));
		newTextFile.getParentFile().mkdirs();
		try
		{
			FileWriter fileWriter = new FileWriter(newTextFile);
			fileWriter.write(codeFormat(content));
			fileWriter.close();
		} catch (IOException e)
		{
		}

	}

	static String codeFormat(String content)
	{
		List<String> keywords = new Vector<String>();
		keywords.addAll(Arrays.asList(new String[] { "class", "channels",
				"types", "functions", "values", "operations", "state",
				"actions", "process", "begin", "end" }));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(content, " ");
		while (st.hasMoreTokens())
		{

			String token = st.nextToken();

			if (keywords.contains(token.trim()))
			{
				sb.append("\n\n");
				sb.append(token);
				sb.append("\n\n");
			} else
			{
				sb.append(" " + token);
			}

		}

		return sb.toString().trim();
	}
}
