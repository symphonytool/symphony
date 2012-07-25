package eu.compassresearch.core.parser;

import eu.compassresearch.ast.lex.*; 

public class Unit
{
    public enum UnitKind 
    {
	SELF,
	IDENTIFIER;
    }

    public final UnitKind kind;
    public final LexIdentifierToken value;
       
    public Unit(UnitKind kind, LexIdentifierToken value)
    {
	this.kind = kind;
	this.value = value ;
    }

    public LexNameToken convertToName()
    {
	return new LexNameToken("Default", value); 
    }

    public LexNameToken convertToName(String prefix)
    {
	return new LexNameToken(prefix, value); 
    }
}