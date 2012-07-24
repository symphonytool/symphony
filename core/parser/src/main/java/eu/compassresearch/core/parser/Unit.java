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
}