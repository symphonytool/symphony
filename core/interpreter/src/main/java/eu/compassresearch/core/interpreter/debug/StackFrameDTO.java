package eu.compassresearch.core.interpreter.debug;

import java.net.URI;

public class StackFrameDTO
{

	private final int lineNumber;
	private final int level;
	private final URI file;

	/**
	 * Dummy constructor for serialization
	 */
	@SuppressWarnings("unused")
	private StackFrameDTO()
	{
		this.lineNumber = -1;
		this.file = null;
		this.level = -1;
	}

	public StackFrameDTO(int lineNumber, URI file, int level)
	{
		this.lineNumber = lineNumber;
		this.file = file;
		this.level = level;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public int getLevel()
	{
		return level;
	}

	public URI getFile()
	{
		return file;
	}
}
