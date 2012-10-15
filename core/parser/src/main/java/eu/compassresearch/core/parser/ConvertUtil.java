package eu.compassresearch.core.parser;

import eu.compassresearch.ast.expressions.*;
import org.overture.ast.expressions.*;
import eu.compassresearch.ast.patterns.*;
import org.overture.ast.patterns.*;
import eu.compassresearch.ast.types.*;
import org.overture.ast.types.*;
import eu.compassresearch.ast.actions.*;
import java.util.*;


public class ConvertUtil
{
    public static PParameter convertPatternToPParameter(PPattern pattern) throws PathConvertException
    {
	PParameter param = null;
	
	switch(pattern.kindPPattern()){
	    
	case IDENTIFIER:
	    {
		AIdentifierPattern cpattern = (AIdentifierPattern)pattern;
		param = new AIdentifierParameter(cpattern.getLocation(), 
						 cpattern.getName().getIdentifier());
	    }
	    break;
	case RECORD:
	    {
		ARecordPattern cpattern = (ARecordPattern)pattern;
		List<PParameter> parameters = new LinkedList<PParameter>();
		for(PPattern p : cpattern.getPlist()){
		    
		    PParameter arg = convertPatternToPParameter(p);
		    parameters.add(arg);
		}
		
		param = new ARecordParameter(cpattern.getLocation(), 
					     cpattern.getTypename(), 
					     parameters);
	    }
	    break;
	case TUPLE:
	    {
		ATuplePattern cpattern = (ATuplePattern)pattern;
		List<PParameter> parameters = new LinkedList<PParameter>();
		for(PPattern p : cpattern.getPlist()){
		    
		    PParameter arg = convertPatternToPParameter(p);
		    parameters.add(arg);
		}
		param =  new ATupleParameter(cpattern.getLocation(), 
					     parameters);
	    }
	 break;
	default:
	    throw new PathConvertException("Illigal Pattern kind for communication parameter : " + pattern.kindPPattern());
	    
	}

	return param;
    }

    public static PExp convertPatternToExp(PPattern pattern) throws PathConvertException
    {
	PExp exp = null;

	switch(pattern.kindPPattern()){
	case BOOLEAN:
	    {
		ABooleanPattern p = (ABooleanPattern)pattern;
		exp = new ABooleanLiteralExp(new ABooleanBasicType(p.getLocation(), 
								   true),
					     p.getLocation(),
					     p.getValue());
	    }
	    break;
	case CHARACTER:
	    {
		ACharacterPattern p = (ACharacterPattern)pattern;
		exp = new ACharLiteralExp(new ACharBasicType(p.getLocation(), 
							     true),
					  p.getLocation(),
					  p.getValue());
	    }
	    break;
      	case INTEGER:
	    {
		AIntegerPattern p = (AIntegerPattern)pattern;
		exp = new AIntLiteralExp(new AIntNumericBasicType(p.getLocation(), 
								  true),
					 p.getLocation(),
					 p.getValue());
		
	    }
	    break;
	case NIL:
	    {
		ANilPattern p = (ANilPattern)pattern;
		exp = new ANilExp(p.getLocation());
	    }
	    break;
	case QUOTE:
	    {
		AQuotePattern p = (AQuotePattern)pattern;
		exp = new AQuoteLiteralExp(new AQuoteType(p.getLocation(), 
							  true,
							  p.getValue()),
					   p.getLocation(),
					   p.getValue());
		
	    }
	    break;
	case REAL:
	    {
		ARealPattern p = (ARealPattern)pattern;
		exp = new ARealLiteralExp(new ARealNumericBasicType(p.getLocation(), 
								    true),
					  p.getLocation(),
					  p.getValue());
	    }
	    break;
	case STRING:
	    {
		AStringPattern p = (AStringPattern)pattern;
		exp = new AStringLiteralExp(null,//TODO set to sequence of char type
					    p.getLocation(),
					    p.getValue());
	    }
	    break;
	case EXPRESSION:
	    {
		AExpressionPattern p = (AExpressionPattern)pattern;
		exp = p.getExp();
	    }
	    break;
	default:
	    throw new PathConvertException("Illigal Pattern kind for channel name expression or communication " + pattern.kindPPattern());

	}

	return exp;
    }
}
