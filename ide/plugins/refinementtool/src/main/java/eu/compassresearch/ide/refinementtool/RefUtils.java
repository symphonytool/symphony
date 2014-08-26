package eu.compassresearch.ide.refinementtool;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.NodeList;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
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
			PExp exp = parsePExp(act);
			if (exp instanceof AVariableExp) {
				AReferenceAction ref = new AReferenceAction();
				ref.setLocation(exp.getLocation());
				ref.setName(((AVariableExp) exp).getName());
				pact = ref;
			}
		}
		return pact;
		
	}
	
	public static PVarsetExpression parsePVarset(String var) {
		ANTLRStringStream as = new ANTLRStringStream(var);
		CmlLexer cmlLexer = new CmlLexer(as);
		CommonTokenStream ct = new CommonTokenStream(cmlLexer);
		CmlParser parser = new CmlParser(ct);
		
		PVarsetExpression pvar = null;
		
		try {
			pvar = parser.varsetExpr();
		} catch (RecognitionException e) {
			
		}
		return pvar;
	}
	
	
	public static void main(String[] args) {
		ANTLRStringStream as = new ANTLRStringStream("A");
		CmlLexer cmlLexer = new CmlLexer(as);
		CommonTokenStream ct = new CommonTokenStream(cmlLexer);
		CmlParser parser = new CmlParser(ct);
		PAction act = null;
		PExp pexp = null;
		
			act = parsePAction("A");
			//PVarsetExpression var = parser.varsetExpr();
			//Object o = parser.expression();
			//pexp = parser.expression().exp;
		
		return;
	}
	
}
