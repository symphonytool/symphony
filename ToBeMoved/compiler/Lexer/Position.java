//package eu.compassresearch.cml.compiler;

public class Position {
    public int offset;
    public int column;
    public int line;
    
    public Position(){}
    
    public Position(int line, int column)
    {
	this.line = line;
	this.column = column;
    }
}