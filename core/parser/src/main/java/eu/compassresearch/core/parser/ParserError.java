package eu.compassresearch.core.parser;

import java.io.File;

public class ParserError
{
	public int line;
	public int col;
	public String message;
	public File file;
    public int offset;
    // offending text
    public String otext;

    public ParserError(String message, int line, int col, int offset,String otext) {
		this.message = message;
		this.line = line;
		this.col = col;
		this.offset = offset;
		this.otext = otext;
	}
	
	@Override
	public String toString()
	{
		String fileName ="";
		if(file!=null)
			fileName = file.getName();
	return message + " in "+ fileName+ " line: "+ line+" col:"+ col;
	}
}

