package eu.compassresearch.core.lexer;

import org.overturetool.vdmj.lex.*;

public class CmlLexeme {
  
    private Position startPos;
    private Position endPos;
    protected  String value;
    private int lex;
    private VDMToken vdmToken = null;

    public CmlLexeme(Position startPos, Position endPos, int lex, String value)
    {
	this.value = value;
	this.startPos = startPos;
	this.endPos = endPos;
	this.lex = lex;
    }

    public Position getStartPos()
    {
	return startPos;
    }

    public Position getEndPos()
    {
	return endPos;
    }

    public int getLexValue()
    {
	return lex;
    }

    public VDMToken getVDMToken()
    {
	return null;
    }
    
    public String getValue()
    {
	return this.value;
    }

    public String toString()
    {
	return value + " " + startPos;
    }
}

