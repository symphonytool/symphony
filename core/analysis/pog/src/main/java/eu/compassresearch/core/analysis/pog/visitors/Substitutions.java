package eu.compassresearch.core.analysis.pog.visitors;

import java.util.HashMap;

import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;

/**
 * A class for holding variable substitutions to be performed
 * 
 * @author ldc
 * 
 */
public class Substitutions {

	HashMap<ILexNameToken, PExp> subs;

	public Substitutions() {
		this.subs = new HashMap<ILexNameToken, PExp>();
	}

	public boolean containsKey(AVariableExp key) {
		return subs.containsKey(key.getName());
	}

	public void put(AVariableExp key, PExp value) {
		subs.put(key.getName(), value);
	}

	public PExp get(AVariableExp key) {
		return subs.get(key.getName());

	}

}
