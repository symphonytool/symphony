package eu.compassresearch.core.interpreter.debug;

import java.net.URI;

public class Breakpoint
{

	private final int id;
	private final URI file;
	private final int line;

	@SuppressWarnings("unused")
	private Breakpoint()
	{
		id = -1;
		file = null;
		line = -1;
	}

	public Breakpoint(int id, URI file, int line)
	{
		this.id = id;
		this.file = file;
		this.line = line;
	}

	public int getId()
	{
		return id;
	}

	public URI getFile()
	{
		return file;
	}

	public int getLine()
	{
		return line;
	}

	@Override
	public String toString()
	{
		return "Breakpoint" + System.lineSeparator() + "id: " + this.id
				+ System.lineSeparator() + "File: " + this.file
				+ System.lineSeparator() + "Line: " + this.line
				+ System.lineSeparator();
	}
}
