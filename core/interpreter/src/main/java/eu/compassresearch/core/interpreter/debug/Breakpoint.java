package eu.compassresearch.core.interpreter.debug;

public class Breakpoint {

	private final int id;
	private final String file;
	private final int line;
	
	public Breakpoint(int id, String file, int line)
	{
		this.id = id;
		this.file = file;
		this.line = line;
	}

	public int getId() {
		return id;
	}

	public String getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}
	
	@Override
	public String toString() {
		return "Breakpoint" + System.lineSeparator() + 
				"id: " + this.id + System.lineSeparator() +
				"File: " + this.file + System.lineSeparator() +
				"Line: " + this.line + System.lineSeparator();
	}
}
