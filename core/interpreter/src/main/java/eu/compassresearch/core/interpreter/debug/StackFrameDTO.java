package eu.compassresearch.core.interpreter.debug;

public class StackFrameDTO {
	
	private final int lineNumber;
	private final int level;
	private final String file;
	
	/**
	 * Dummy constructor for serialization
	 */
	private StackFrameDTO()
	{
		this.lineNumber = -1;
		this.file = null;
		this.level = -1;
	}
	
	public StackFrameDTO(int lineNumber, String file, int level)
	{
		this.lineNumber = lineNumber;
		this.file = file;
		this.level = level;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getFile() {
		return file;
	}
}
