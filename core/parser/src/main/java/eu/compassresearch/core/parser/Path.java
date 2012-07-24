package eu.compassresearch.core.parser;

import eu.compassresearch.ast.expressions.*;
import eu.compassresearch.ast.lex.*; 
import java.util.*;

public class Path
{
    public class PathConvertException extends Exception
    {
	public PathConvertException(String message){
	    super(message);
	}
    }

    public enum PathKind {
	UNIT,
	TILDE,
	DOT,
	BACKTICK,
	DOTHASH,
	APPLY,
	SEQRANGE;
    }

    public final PathKind kind;
    public final Unit unit;
    public final Path subPath;
    public final List<PExp> expList;
    public final LexIntegerToken numeral;

    public Path(PathKind kind, Unit unit)
    {
	this.unit = unit;
	this.kind = kind;
	expList = null;
	subPath = null;
	numeral = null;
    }
    
    public Path(PathKind kind, Path subPath)
    {
	this.unit = null;
	this.kind = kind;
	expList = null;
	this.subPath = subPath;
	numeral = null;
    }
    
    public Path(PathKind kind, Path subPath, LexIntegerToken numeral)
    {
	this.unit = null;
	this.kind = kind;
	expList = null;
	this.subPath = subPath;
	this.numeral = numeral;
    }

    public Path(PathKind kind, Path subPath, Unit unit)
    {
	this.unit = unit;
	this.kind = kind;
	expList = null;
	this.subPath = subPath;
	numeral = null;
    }
    
    public Path(PathKind kind, Path subPath, List<PExp> expList)
    {
	this.unit = null;
	this.kind = kind;
	this.expList = expList;
	this.subPath = subPath;
	numeral = null;
    }

    public PExp convertToExpression() throws PathConvertException
    {
	PExp exp = null;
	switch(kind){
	case UNIT:
	    LexNameToken name = new LexNameToken("Default", unit.value);
	    switch(unit.kind){
	    case SELF:
		exp = new ASelfExp(name.getLocation(),name);
		break;
	    case IDENTIFIER:
		exp = new ANameExp(name.getLocation(),name);
		break;
		
	    }
	    break;
	    
	    
	default:
	    throw new PathConvertException("Illigal path for expression");
	}

	return exp;
    }
       

}