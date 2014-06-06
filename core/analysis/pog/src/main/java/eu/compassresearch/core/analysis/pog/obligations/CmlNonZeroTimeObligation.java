package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.pog.obligation.NonZeroObligation;
import org.overture.pog.pub.IPOContextStack;

public class CmlNonZeroTimeObligation extends NonZeroObligation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmlNonZeroTimeObligation(PExp exp,
			IPOContextStack ctxt) {
		super(exp.getLocation(), exp, ctxt);

	}


}
