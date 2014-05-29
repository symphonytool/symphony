package eu.compassresearch.ide.refinementtool;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.expressions.PExp;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pexp;
	}
	
}
