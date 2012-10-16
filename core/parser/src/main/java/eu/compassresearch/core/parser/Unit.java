package eu.compassresearch.core.parser;

//import eu.compassresearch.ast.lex.*; 
import org.overture.ast.lex.*;

public class Unit
{
    public enum UnitKind 
    {
	SELF,
	IDENTIFIER;
    }

    public final UnitKind kind;
    public final LexIdentifierToken value;
    public final LexLocation location;
       
    public Unit(UnitKind kind, LexIdentifierToken value)
    {
	this.kind = kind;
	this.value = value ;
	this.location = value.getLocation();
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
