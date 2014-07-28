package eu.compassresearch.ide.refinementtool;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public abstract class RefUtils {

	public RefUtils() {
		// TODO Auto-generated constructor stub
	}

	public static PExp parsePExp(String exp) {
		ANTLRStringStream as = new ANTLRStringStream(exp);
		CmlLexer cmlLexer = new CmlLexer(as);
		CommonTokenStream ct = new CommonTokenStream(cmlLexer);
		CmlParser parser = new CmlParser(ct);
		
		PExp pexp = null;
		
		try {
			pexp = parser.expression().exp;
		} catch (RecognitionException e) {
			
		}
		return pexp;
	}

	public static PAction parsePAction(String act) {

		ANTLRStringStream as = new ANTLRStringStream(act);
		CmlLexer cmlLexer = new CmlLexer(as);
		CommonTokenStream ct = new CommonTokenStream(cmlLexer);
		CmlParser parser = new CmlParser(ct);
		
		PAction pact = null;
		
		try {
			pact = parser.action().action;
		} catch (RecognitionException e) {
			
		}
		return pact;
		
	}
	
}
