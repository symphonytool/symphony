package eu.compassresearch.core.lexer;

public class Position {
    public int offset;
    public int column;
    public int line;
    
    public Position(){}
    
    public Position(int line, int column, int offset)
    {
	this.line = line;
	this.column = column;
	this.offset = offset;
    }

    @Override
    public String toString()
    {
	return line + ":" + column; 
    }

    @Override
    public boolean equals(Object obj)
    {
	Position other = (obj instanceof Position) ? (Position)obj : null;
	if(other != null)
	    return this.offset == other.offset; 
	else
	    return false;
    }
}
