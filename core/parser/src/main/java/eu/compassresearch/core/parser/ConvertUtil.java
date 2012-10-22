package eu.compassresearch.core.parser;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.ACharLiteralExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ANilExp;
import org.overture.ast.expressions.AQuoteLiteralExp;
import org.overture.ast.expressions.ARealLiteralExp;
import org.overture.ast.expressions.AStringLiteralExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.patterns.ABooleanPattern;
import org.overture.ast.patterns.ACharacterPattern;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.AIntegerPattern;
import org.overture.ast.patterns.ANilPattern;
import org.overture.ast.patterns.AQuotePattern;
import org.overture.ast.patterns.ARealPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.AStringPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.ARealNumericBasicType;

import eu.compassresearch.ast.actions.AIdentifierParameter;
import eu.compassresearch.ast.actions.ARecordParameter;
import eu.compassresearch.ast.actions.ATupleParameter;
import eu.compassresearch.ast.actions.PParameter;
import eu.compassresearch.ast.expressions.PCMLExp;


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
		exp = new ABooleanConstExp(new ABooleanBasicType(p.getLocation(), 
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
